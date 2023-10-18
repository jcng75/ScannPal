package backend.classes;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.ChromeDriver;

public class LambdaHandler {

    public String handleRequest(String input) {
        System.out.println("Welcome to my first " + input);

        // Set the path to the chromedriver binary
        System.setProperty("webdriver.chrome.driver", "/opt/chromedriver");

        // Set Chrome options for headless mode
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--disable-gpu");
        // chromeOptions.addArguments("--no-sandbox");

        // Create a WebDriver instance with ChromeOptions
        WebDriver driver = new ChromeDriver(chromeOptions);

        try {
            // Navigate to amazon.com
            driver.get("https://www.amazon.com");

            // Get the website's title
            String pageTitle = driver.getTitle();
            System.out.println("Page Title: " + pageTitle);

            return pageTitle;
        } finally {
            // Close the WebDriver session
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
