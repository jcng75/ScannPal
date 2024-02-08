package backend.classes;

import java.util.List;

// For Local Tests
public class main {
    public static void main(String args[]) {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Justin Ng\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        TestHttpRequest tRequest = new TestHttpRequest("http://localhost/login.php"); 
        tRequest.getRequest();
        // String a = "testinggggggggggggggggggggg";
        // String b = "akjdflksdjlkasdjlkf";
        // System.out.println(editDistance.editDist(a, b, a.length(), a.length()));

        WebCrawler crawl = new WebCrawler("http://localhost/login.php", "admin", "password");
        List<TestCase> testCases = crawl.crawl(3);
        
        for (int i = testCases.size()-1; i >= 0; i--){
            testCases.get(i).runTestCase();
        }
        // List<TestCase> infectedCases = attackInjector.injectCase(injectedCaseExample);
        
        //injectedCaseExample.display();
        // for (TestCase tc : infectedCases){
            // tc.display();
        // }
        

        // Testing clicking button
        // String idName = "webwolf-button";
        // ClickButton clickButton2 = new ClickButton(driver, idName);
        // clickButton2.execute();

        // String fileName = "screenshot.jpg";

        // TakeScreenshot screenshot = new TakeScreenshot(fileName);
        // screenshot.execute();

        // List<WebElement> el = driver.findElements(By.tagName("a"));
        // el.get(9).click();
        
    }
}
