package backend.classes;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
// import java.util.List;
import java.util.List;
import java.util.HashMap;

// For Local Tests
public class Main {
    private static final String HashMap = null;

    public static void main(String args[]) throws IOException, SQLException {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Justin Ng\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        // TestHttpRequest tRequest = new TestHttpRequest("http://localhost/login.php"); 
        // int responseCode = tRequest.getRequest();
        // System.out.println("Response Code: " + responseCode);
        
        WebCrawler crawl = new WebCrawler("http://localhost/login.php", "admin", "password");
        // System.out.println(TakeScreenshot.getTimeStamp());
        List<TestCase> testCases = crawl.crawl(3);
        List<List<TestCase>> injectedResults = AttackInjector.generateInjectedCases(testCases);
        HashMap<String, List<List<TestCase>>> splitCases = AttackInjector.splitTestCases(injectedResults);
        WorkerNode.createTasks(splitCases, "mmollano1@pride.hofstra.edu");

        // List<TestResult> testResults = TestResult.generateResults(testCases);

        // ResultAnalysis.runAnalysis(testResults);
        
        // EC2Client client = new EC2Client();
        // client.listInstances();

        MySQLConnection conn = new MySQLConnection();
        // String createQuery = """
        //     CREATE TABLE Result (
        //         result_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
        //         task_id INT NOT NULL,
        //         is_vulnerable BOOLEAN NOT NULL,
        //         payload varchar(250),
        //         attack_type char(3),
        //         html_string TEXT,
        //         screenshot BLOB,
        //         FOREIGN KEY (task_id) REFERENCES Task(task_id)
        //     );
        // """;
        // client.runUpdate(createQuery);

        String selectQuery = "SELECT * FROM Task";
        String selectQuery2 = "SELECT * FROM Job";
        // conn.displaySelectAsList(selectQuery);
        conn.displaySelectAsTable(selectQuery);
        conn.displaySelectAsTable(selectQuery2);

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
