package backend;

import org.openqa.selenium.WebDriver;
// import org.openqa.selenium.chrome.ChromeDriver;
// import org.openqa.selenium.chrome.ChromeOptions;

import backend.scan.WorkerNode;

// For Cloud Tests
public class App {
    public static void main(String args[]) throws Exception {
        System.setProperty("webdriver.chrome.driver", "/ScannPal/chromedriver-linux64/chromedriver");
        
        // WebDriver driver = MyWebDriver.getDriver();
        // driver.get("http://3.217.118.130//login.php");
        // System.out.println(driver.getTitle());
        WorkerNode workerNode = new WorkerNode();
        workerNode.runNode();
    }
}
