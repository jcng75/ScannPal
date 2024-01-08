package backend.classes;

// For Local Tests
public class main {
    public static void main(String args[]) {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Justin Ng\\Downloads\\Installers\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        TestHttpRequest tRequest = new TestHttpRequest("http://localhost/login.php"); 
        tRequest.getRequest();

        WebCrawler crawl = new WebCrawler("http://localhost/login.php", "admin", "password");
        crawl.crawl(3);

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
