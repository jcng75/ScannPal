package backend.classes;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

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
        EC2Client client = new EC2Client();
        // client.listInstances();
        // List<String> ips = client.getActiveInstances();
        // System.out.println(ips);
        TestCase testCase1 = new TestCase();
        TestCase testCase2 = new TestCase();
        TestCase testCase3 = new TestCase();
        TestCase testCase4 = new TestCase();
        TestCase testCase5 = new TestCase();
        TestCase testCase6 = new TestCase();
        List<TestCase> testCases = new ArrayList<TestCase>(Arrays.asList(testCase1, testCase2, testCase3, testCase4, testCase5, testCase6));
        HashMap<String, List<TestCase>> results = AttackInjector.splitTestCases(testCases);
        System.out.println(results.entrySet());

    }
}
