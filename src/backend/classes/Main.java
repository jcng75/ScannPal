package backend.classes;

import java.io.IOException;
// import java.util.List;

// For Local Tests
public class Main {
    public static void main(String args[]) throws IOException {
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

        MySQLConnection client = new MySQLConnection();
        // String insertQuery = """
        //     INSERT INTO User (fname, lname, email, password_hash, creation_date) VALUES (
        //         'Justin', 
        //         'Ng', 
        //         'jng14@pride.hofstra.edu', 
        //         '$2y$10$aZegIS2LjnykNb3QPIsnM.OeQ0vIEgRjfSqAlCVlzQdyHrLvBQLVK', 
        //         NOW()
        //     );
        // """;
        // client.runUpdate(insertQuery);

        String selectQuery = "SELECT * FROM User";
        client.runSelect(selectQuery);
    }
}
