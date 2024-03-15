package backend.classes;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;

// For Cloud Tests
public class App {
    public static void main(String args[]) throws Exception {
        System.setProperty("webdriver.chrome.driver", "/home/ec2-user/ScannPal/chromedriver-linux64/chromedriver");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setBinary("/home/ec2-user/ScannPal/chrome-linux64/chrome");
        chromeOptions.addArguments("--headless");
        WorkerNode workerNode = new WorkerNode();
        workerNode.runNode();
    }
}
