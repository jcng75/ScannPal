package backend.classes;

// For Local Tests
public class main {
    public static void main(String args[]) {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Justin Ng\\Downloads\\Installers\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        TestHttpRequest tRequest = new TestHttpRequest("http://localhost:8080/WebGoat/login"); 
        tRequest.getRequest();

        // WebDriver driver = MyWebDriver.getDriver();
        // driver.get("http://localhost:8080/WebGoat/login");
        // System.out.println("Page title is: " + driver.getTitle());
        // System.out.println("Page URL is: " + driver.getCurrentUrl());

        WebCrawler crawl = new WebCrawler("http://localhost:8080/WebGoat/login", "johnny", "abc123");
        crawl.crawl(1);

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
