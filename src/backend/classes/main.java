package backend.classes;
import java.util.List;




// For Local Tests
public class main {
    public static void main(String args[]) {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\adamr\\Documents\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        TestHttpRequest tRequest = new TestHttpRequest("http://localhost/login.php"); 
        tRequest.getRequest();

        WebCrawler crawl = new WebCrawler("http://localhost/login.php", "admin", "password");
        List<TestCase> goodCases = crawl.crawl(3);

        TestCase someCase = goodCases.get(0);

        someCase.display();
        
        List<TestCase> injectedCases = injectCase(someCase);

        TestCase injectedCaseExample = injectedCases.get(0);

        injectedCaseExample.display();
        
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
