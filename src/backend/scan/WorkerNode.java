package backend.scan;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import backend.aws.MySQLConnection;
import backend.results.ResultAnalysis;
import backend.results.TestCase;
import backend.results.TestResult;
import backend.utility.PrivateIP;
import backend.utility.Serialize;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;


public class WorkerNode {

    public WorkerNode(){

    }

    public void runNode() throws Exception{

        MySQLConnection mySQLConnection = new MySQLConnection();
        Connection conn = mySQLConnection.createConnection();
        String privateIP = PrivateIP.getPrivateIP();

        PreparedStatement goldenStatement = conn.prepareStatement("""
                SELECT * FROM Task 
                WHERE node_ip = ? AND completed = false;
                """);

        goldenStatement.setString(1, privateIP);

        while (true){
            System.out.println("(::) Checking for new tasks to complete... \n\n");
            Thread.sleep(5000);
            // Run the query to check for any new tasks from the database
            // ResultSet queryResults = mySQLConnection.getSelectResults(goldenQuery);
            ResultSet queryResults = goldenStatement.executeQuery();
 
            while (queryResults.next()){

                
                int taskID = queryResults.getInt("task_id");

                System.out.println("(!) New task found! Task ID: " + taskID + "\n\n");
                
                int jobID = queryResults.getInt("job_id");
                
                Blob blobData = queryResults.getBlob("test_cases");

                int blobLength = (int) blobData.length();
                byte[] byteData = blobData.getBytes(1, blobLength);
                blobData.free();

                List<TestCase> testCases = Serialize.deserializeList(byteData);

                List<TestResult> testResults = TestResult.generateResults(testCases);
                // If the testresult is vulnerable, save the results

                PreparedStatement startTaskQuery = conn.prepareStatement("""
                    UPDATE Task
                    SET date_started = NOW()
                    WHERE task_id = ?;
                """);

                startTaskQuery.setInt(1, taskID);
                startTaskQuery.execute();

                // pass the taskID when running analysis, so that it can be used when inserting into Result table
                ResultAnalysis.runAnalysis(testResults, taskID);
                
                PreparedStatement completeTaskQuery = conn.prepareStatement("""
                    UPDATE Task
                    SET completed = true, date_completed = NOW()
                    WHERE task_id = ?;
                """);

                completeTaskQuery.setInt(1, taskID);
                completeTaskQuery.execute();

                PreparedStatement checkJob = conn.prepareStatement("""
                    SELECT * FROM Task
                    WHERE job_id = ? and completed = false;
                """);

                checkJob.setInt(1, jobID);
                
                ResultSet rs = checkJob.executeQuery();
                // if no tasks left, run update query
                if (!rs.next()){
                    PreparedStatement completeJobQuery = conn.prepareStatement("""
                        UPDATE Job
                        SET completed = true, date_completed = NOW()
                        WHERE job_id = ?;
                    """);
                    completeJobQuery.setInt(1, jobID);
                    completeJobQuery.execute();
                }

            }
        }
    }

    public static void createTasks(HashMap<String, List<List<TestCase>>> splitCases, String currentEmail, String url) throws SQLException{
        
        // Create the job associated with all these tasks
        MySQLConnection conn = new MySQLConnection();
        Connection connection = conn.createConnection();

        PreparedStatement getCurrentUser = connection.prepareStatement("SELECT user_id FROM User WHERE email = ?;");
        getCurrentUser.setString(1, currentEmail);

        int userID = 0;

        ResultSet rs = getCurrentUser.executeQuery();
        while (rs.next()){
            userID = rs.getInt(1);
        }

        PreparedStatement createJobQuery = connection.prepareStatement("INSERT INTO Job (user_id, completed, date_started, website_link) VALUES (?, false, NOW(), ?);");
        
        createJobQuery.setInt(1, userID);
        createJobQuery.setString(2, url);
        createJobQuery.executeUpdate();

        System.out.println("(+) New Job Has been Created!");

        // Get that job_id to associate with the test_cases

        String getJobID = "SELECT MAX(job_id) FROM Job";
        
        rs = conn.getSelectResults(getJobID);
        int jobID = 0;

        while (rs.next()){
            jobID = rs.getInt(1);
        }

        for (String key : splitCases.keySet()){
            List<List<TestCase>> outerTestCaseList = splitCases.get(key);
            for (List<TestCase> testCaseList : outerTestCaseList){
                byte[] serializedList = Serialize.serializeList(testCaseList);
                conn.createTask(jobID, key, serializedList);
            }
        }

        System.out.println("(+) New tasks created!");
    }
    
}
