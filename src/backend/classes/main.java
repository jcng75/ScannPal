package backend.classes;
import java.util.ArrayList;
import java.util.List;




// For Local Tests
public class main {
    public static void main(String args[]) {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\adamr\\Documents\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        TestHttpRequest tRequest = new TestHttpRequest("http://localhost/login.php"); 
        tRequest.getRequest();

        WebCrawler crawl = new WebCrawler("http://localhost/login.php", "admin", "password");
        List<TestCase> cases = crawl.crawl(3);

        // TestCase someCase = goodCases.get(0);

        // someCase.display();
        
        // List<TestCase> infectedCases = new ArrayList<TestCase>();

        /*for (TestCase tc : cases){
            List<TestCase> injectedCase = attackInjector.injectCase(tc);
            infectedCases.addAll(injectedCase);
        }*/
        // List<TestCase> injectedCases = attackInjector.injectCase(someCase);

        TestCase injectedCaseExample = cases.get(3);
        List<TestCase> infectedCases = attackInjector.injectCase(injectedCaseExample);
        
        //injectedCaseExample.display();
        for (TestCase tc : infectedCases){
            tc.display();
        }
        
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
