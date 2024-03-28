package backend.classes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
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
            Thread.sleep(5000);
            // Run the query to check for any new tasks from the database
            // ResultSet queryResults = mySQLConnection.getSelectResults(goldenQuery);
            ResultSet queryResults = goldenStatement.executeQuery();
 
            while (queryResults.next()){

                int taskID = queryResults.getInt("task_id");
                
                int jobID = queryResults.getInt("job_id");
                
                Blob blobData = queryResults.getBlob("test_cases");

                int blobLength = (int) blobData.length();
                byte[] byteData = blobData.getBytes(1, blobLength);
                blobData.free();

                List<TestCase> testCases = Serialize.deserializeList(byteData);

                List<TestResult> testResults = TestResult.generateResults(testCases);
                // If the testresult is vulnerable, save the results

                ResultAnalysis.runAnalysis(testResults);
                
                PreparedStatement completeTaskQuery = conn.prepareStatement("""
                    UPDATE Task
                    SET completed = true, date_completed = NOW()
                    WHERE task_id = ?;
                """);

                completeTaskQuery.setInt(1, taskID);
                completeTaskQuery.execute();

                PreparedStatement checkJob = conn.prepareStatement("""
                    SELECT * FROM Job
                    WHERE job_id = ?;
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

    public static void createTasks(HashMap<String, List<List<TestCase>>> splitCases, String currentEmail) throws SQLException{
        
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

        String createJobQuery = String.format("""
                INSERT INTO Job (user_id, completed, date_started )
                VALUES (%d, false, NOW())
                """, userID);
        
        conn.runUpdate(createJobQuery);

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
    }
    
}
