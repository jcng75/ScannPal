package backend.classes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// For Local Tests
public class Main {
    public static void main(String args[]) throws IOException, ClassNotFoundException {
        // System.setProperty("webdriver.chrome.driver", "chromedriver-mac-x64/chromedriver");

        // TestHttpRequest tRequest = new TestHttpRequest("http://localhost/login.php"); 
        // int responseCode = tRequest.getRequest();
        // System.out.println("Response Code: " + responseCode);
        
        // WebCrawler crawl = new WebCrawler("http://localhost/login.php", "admin", "password");
        // System.out.println(TakeScreenshot.getTimeStamp());
        // List<TestCase> testCases = crawl.crawl(3);

        // List<TestResult> testResults = TestResult.generateResults(testCases);

        // ResultAnalysis.runAnalysis(testResults);
        
        // EC2Client client = new EC2Client();
        // client.listInstances();

        // MySQLConnection conn = new MySQLConnection();
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

        // String selectQuery = "SELECT * FROM User";
        // conn.displaySelectAsList(selectQuery);
        // conn.displaySelectAsTable(selectQuery);
        
        // try {
		// 	System.out.println(PrivateIP.getPrivateIP());
		// } catch (Exception e) {
		// 	e.printStackTrace();
		// }

        TestCase tc1 = new TestCase();
        tc1.setAttackType("SQL tc1");
        tc1.setInjected(false);
        tc1.setPayload("this is for tc1 test");

        TestCase tc2 = new TestCase();
        tc2.setAttackType("XSS tc2");
        tc2.setInjected(true);
        tc2.setPayload("this is for tc2 test");

        List<TestCase> tcList = new ArrayList<TestCase>(Arrays.asList(tc1, tc2));
        System.out.println("tcList before serialization: " + tcList);

        byte[] serializedArray = Serialize.serializeList(tcList);
        System.out.println("Serialized tcList into byte array: " + serializedArray);

        List<TestCase> deserializedTC = Serialize.deserializeList(serializedArray);
		System.out.println("Deserialized byte array into List<TestCase>: " + deserializedTC + "\n");

        for (TestCase tc : deserializedTC) {
            System.out.println(tc.getAttackType());
            System.out.println(tc.getInjected());
            System.out.println(tc.getPayload()+"\n");
        }
 
    }
}
