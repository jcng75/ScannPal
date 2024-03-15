package backend.classes;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;


public class WorkerNode {

    public WorkerNode(){

    }

    public void runNode() throws Exception{

        String privateIP = PrivateIP.getPrivateIP();
        String goldenQuery = """
                SELECT * FROM Task 
                WHERE node_ip = ;
                """ + privateIP + "AND completed = false;";

        while (true){
            Thread.sleep(5000);
            // Run the query to check for any new tasks from the database
            MySQLConnection conn = new MySQLConnection();
            ResultSet queryResults = conn.getSelectResults(goldenQuery);
 
            if (queryResults != null){
                while (queryResults.next()){
                    int taskID = queryResults.getInt("task_id");
                    InputStream getData = queryResults.getBinaryStream("test_case");
                    ObjectInputStream objectInputStream = new ObjectInputStream(getData);

                    @SuppressWarnings("unchecked")
                    List<TestCase> testCases = (List<TestCase>) objectInputStream.readObject();
                    // TestCase tc = deserialize(getData);

                    List<TestResult> testResults = TestResult.generateResults(testCases);
                    // If the testresult is vulnerable, save the results

                    ResultAnalysis.runAnalysis(testResults);
                    
                    String completeTaskQuery = """
                        UPDATE task
                        SET status = 1
                        WHERE task_id = 
                    """ + taskID + ";";
                    conn.runUpdate(completeTaskQuery);

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
