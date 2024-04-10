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

        selectQuery = "SELECT * FROM User";
        conn.displaySelectAsTable(selectQuery);

        // selectQuery = "SELECT * FROM Job";
        // conn.displaySelectAsList(selectQuery);

        // selectQuery = "SELECT * FROM Task";
        // conn.displaySelectAsList(selectQuery);

        // selectQuery = "SELECT * FROM Result";
        // conn.displaySelectAsList(selectQuery);

    }
}
