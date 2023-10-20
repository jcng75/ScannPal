package backend.classes;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class App {
    public static void main(String args[]) {
        System.setProperty("webdriver.chrome.driver", "chromedriver-linux64/chromedriver");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setBinary("chromedriver-linux64/headless-chromium");
        chromeOptions.addArguments("--headless");
        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.get("https://www.amazon.com/");
    }
}
