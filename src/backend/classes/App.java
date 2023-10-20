package backend.classes;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class App {
    public static void main(String args[]) {
        System.setProperty("webdriver.chrome.driver", "/home/ec2-user/ScannPal/chromedriver-linux64/chromedriver");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setBinary("/home/ec2-user/ScannPal/chrome-linux64/chrome");
        chromeOptions.addArguments("--headless");
        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.get("https://www.amazon.com/");
        System.out.println("Page title is : " + driver.getTitle());
        driver.close();
    }
}
