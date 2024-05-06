package backend;

import java.io.IOException;
// import java.sql.Connection;
// import java.sql.PreparedStatement;
// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.List;

import backend.aws.MySQLConnection;

// For Local Tests
public class Main {
    public static void main(String args[]) throws IOException, ClassNotFoundException {
        System.setProperty("webdriver.chrome.driver", "chromedriver-mac-x64/chromedriver");

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

        MySQLConnection conn = new MySQLConnection();

        // conn.runUpdate("DELETE FROM Result");
        // conn.runUpdate("DELETE FROM Task");
        // conn.runUpdate("DELETE FROM Job");

        String selectQuery;

        // selectQuery = "SELECT * FROM User";
        // conn.displaySelectAsTable(selectQuery);

        selectQuery = "SELECT * FROM Job";
        conn.displaySelectAsTable(selectQuery);

        selectQuery = "SELECT * FROM Task WHERE job_id = 9;";
        conn.displaySelectAsTable(selectQuery);

        // selectQuery = "SELECT * FROM Result";
        // conn.displaySelectAsList(selectQuery);
        
        // selectQuery = "SELECT * FROM sessions";
        // conn.displaySelectAsTable(selectQuery);
        
        // conn.runUpdate("ALTER TABLE Job ADD COLUMN website_link VARCHAR(255) AFTER user_id;");
        // conn.runUpdate("UPDATE Job SET website_link = '3.95.191.246' WHERE job_id = 5;");
        // conn.runUpdate("DELETE FROM Job WHERE job_id = 2;");
        // conn.runUpdate("ALTER TABLE Result AUTO_INCREMENT = 1;");
        // conn.runUpdate("UPDATE Job SET completed = true, date_completed = NOW() WHERE job_id = 5;");
        // conn.runUpdate("DELETE FROM Task WHERE job_id = 7;");
        // conn.runUpdate("DELETE FROM Job WHERE job_id = 7;");
        // conn.runUpdate("DELETE FROM User WHERE user_id = 9;");
        // conn.runUpdate("UPDATE Task SET node_ip = '10.0.0.14' WHERE task_id BETWEEN 215 and 223;");
        // conn.runUpdate("UPDATE Task SET node_ip = '10.0.0.5' WHERE task_id = 215;");
        // conn.runUpdate("UPDATE Task SET completed = false WHERE task_id = 215;");
        // conn.runUpdate("ALTER TABLE Result MODIFY screenshot LONGBLOB;");
    }
}
