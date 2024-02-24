package backend.classes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// For Local Tests
public class main {
    public static void main(String args[]) throws IOException {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Justin Ng\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        TestHttpRequest tRequest = new TestHttpRequest("http://localhost/login.php"); 
        tRequest.getRequest();
        // String a = "testinggggggggggggggggggggg";
        // String b = "akjdflksdjlkasdjlkf";
        // System.out.println(editDistance.editDist(a, b, a.length(), a.length()));

        WebCrawler crawl = new WebCrawler("http://localhost/login.php", "admin", "password");
        System.out.println(TakeScreenshot.getTimeStamp());
        List<TestCase> testCases = crawl.crawl(3);

        List<TestResult> testResults = TestResult.generateResults(testCases);

        ResultAnalysis.runAnalysis(testResults);

        // for (TestResult tr : testResults){
        //     tr.display();
        // }
    }
}
