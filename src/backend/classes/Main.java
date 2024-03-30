package backend.classes;

import java.io.IOException;
// import java.sql.Connection;
// import java.sql.PreparedStatement;
// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.List;

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

        MySQLConnection conn = new MySQLConnection();

        //conn.runUpdate("DELETE FROM Job");
        //conn.runUpdate("DELETE FROM Task");

        String selectQuery = "SELECT * FROM Job";
        conn.displaySelectAsTable(selectQuery);

        selectQuery = "SELECT * FROM Task";
        conn.displaySelectAsTable(selectQuery);

    }
}
