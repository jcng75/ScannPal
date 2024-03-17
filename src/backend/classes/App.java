package backend.classes;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

// For Cloud Tests
public class App {
    public static void main(String args[]) throws Exception {
        System.setProperty("webdriver.chrome.driver", "/ScannPal/chromedriver-linux64/chromedriver");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setBinary("/ScannPal/chrome-linux64/chrome");
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--no-sandbox");
        System.out.println("You made it here");
        // WebDriver driver = MyWebDriver.getDriver();
        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.get("http://44.212.26.250/login.php");
        System.out.println(driver.getTitle());
        // WorkerNode workerNode = new WorkerNode();
        // workerNode.runNode();
    }
}
