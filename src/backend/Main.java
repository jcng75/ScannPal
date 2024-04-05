package backend;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import backend.aws.MySQLConnection;

import java.util.HashMap;

// For Local Tests
public class Main {
    public static void main(String args[]) throws IOException, SQLException, ClassNotFoundException {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Justin Ng\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        // WebCrawler crawl = new WebCrawler("http://3.217.118.130/login.php", "admin", "password");
        // List<TestCase> testCases = crawl.crawl(3);
        // List<List<TestCase>> injectedCases = AttackInjector.generateInjectedCases(testCases);
        // HashMap<String, List<List<TestCase>>> splitCases = AttackInjector.splitTestCases(injectedResults);
        // int counter = 162;
        // for (List<TestCase> injectedCase : injectedCases){
            // List<TestResult> result = TestResult.generateResults(injectedCase);
            // ResultAnalysis.runAnalysis(result, counter);
            // counter ++;
// 
        // }
        // List<TestResult> testResults = TestResult.generateResults(testCases);
        // WorkerNode.createTasks(splitCases, "mmollano1@pride.hofstra.edu");

        MySQLConnection mySQLConn = new MySQLConnection();
        Connection conn = mySQLConn.createConnection();

        // String selectQuery2 = "SELECT * FROM Job";
        // mySQLConn.displaySelectAsTable(selectQuery2);
        // String selectQuery1 = "SELECT * FROM Task";

        // mySQLConn.displaySelectAsList(selectQuery2);
        // mySQLConn.displaySelectAsTable(selectQuery1);
        

        String selectQuery3 = "SELECT task_id, payload FROM Result";
        // String selectQuery4 = "SELECT * FROM Result";
        mySQLConn.displaySelectAsList(selectQuery3);        

        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        // PreparedStatement goldenStatement = conn.prepareStatement("""
        //     SELECT * FROM Task 
        //     // WHERE node_ip = ? AND completed = false;
        // // """);



        // goldenStatement.setString(1, "10.0.0.5");
        // ResultSet rs = goldenStatement.executeQuery();
        
        // while (rs.next()){
        //     Blob blobData = rs.getBlob("test_cases");
        //     int blobLength = (int) blobData.length();
        //     byte[] byteData = blobData.getBytes(1, blobLength);
        //     blobData.free();

        //     List<TestCase> testCases = Serialize.deserializeList(byteData);
            
            
        //     List<TestResult> testResults = TestResult.generateResults(testCases);        
        // }
        // PreparedStatement checkJob = conn.prepareStatement("""
            // SELECT * FROM Job
            // WHERE job_id = ?;
        // """);
// 
        // int jobID = 7;
        // checkJob.setInt(1, jobID);
        // ResultSet rs = checkJob.executeQuery();
        // System.out.println(rs.next());
        // while (rs.next()){
        //     System.out.println("(+) NEW ROW (+)");
        //     System.out.println("job_id: " + rs.getInt(1));
        //     System.out.println("user_id: " + rs.getInt(2));
        //     System.out.println("completed: " + rs.getByte(3));
        // }
        // System.out.println("EXECUTING UPDATE ON JOB");
        // PreparedStatement completeJobQuery = conn.prepareStatement("""
            // UPDATE Job
            // SET completed = true, date_completed = NOW()
            // WHERE job_id = ?;
        // """);
        // completeJobQuery.setInt(1, jobID);
        // completeJobQuery.execute();
        // System.out.println("UPDATE COMPLETE");
        // rs = checkJob.executeQuery();
        // while (rs.next()){
        //     System.out.println("(+) NEW ROW (+)");
        //     System.out.println("job_id: " + rs.getInt(1));
        //     System.out.println("user_id: " + rs.getInt(2));
        //     System.out.println("completed: " + rs.getByte(3));
        //     System.out.println("date_completed: " + rs.getDate(5));
        // }

        // EC2Client client = new EC2Client();
        // client.listInstances();

        // TestCase tc1 = null;
        // TestCase tc2 = null;
        // TestCase tc3 = null;
        // TestCase tc4 = null;

        // List<TestCase> lTc = new ArrayList<TestCase>(Arrays.asList(tc1, tc2, tc3, tc4));
        // List<TestCase> lTc2 = new ArrayList<TestCase>(Arrays.asList(tc1, tc2, tc3, tc4));
        // List<TestCase> lTc3 = new ArrayList<TestCase>(Arrays.asList(tc1, tc2, tc3, tc4));
        // List<TestCase> lTc4 = new ArrayList<TestCase>(Arrays.asList(tc1, tc2, tc3, tc4));
        // List<List<TestCase>> ll = new ArrayList<List<TestCase>>(Arrays.asList(lTc, lTc2, lTc3, lTc4));
        // HashMap<String, List<List<TestCase>>> hasha = AttackInjector.splitTestCases(ll);
        // System.out.println(hasha.entrySet());
        /*
        [
            [BaseCase1, InjectedCase1, InjectedCase2]   
            [BaseCase1, InjectedCase1, InjectedCase2]   
            [BaseCase1, InjectedCase1, InjectedCase2]   
            [BaseCase1, InjectedCase1, InjectedCase2]   
            ]
            
        */

        // try {
		// 	System.out.println(PrivateIP.getPrivateIP());
		// } catch (Exception e) {
		// 	e.printStackTrace();
		// }
    }
}
