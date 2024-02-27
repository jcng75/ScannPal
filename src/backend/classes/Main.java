package backend.classes;

import java.io.IOException;
import java.util.List;

// For Local Tests
public class Main {
    public static void main(String args[]) throws IOException {
        System.setProperty("webdriver.chrome.driver", "chromedriver-mac-x64/chromedriver");

        TestHttpRequest tRequest = new TestHttpRequest("http://localhost/login.php"); 
        int responseCode = tRequest.getRequest();
        System.out.println("Response Code: " + responseCode);
        
        WebCrawler crawl = new WebCrawler("http://localhost/login.php", "admin", "password");
        System.out.println(TakeScreenshot.getTimeStamp());
        List<TestCase> testCases = crawl.crawl(3);

        List<TestResult> testResults = TestResult.generateResults(testCases);

        ResultAnalysis.runAnalysis(testResults);
    }
}
