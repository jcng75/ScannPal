package backend.scan;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class MyWebDriver {
   private static WebDriver driver = null;
   public static WebDriver getDriver() {
        if (driver == null){

            // Detect the OS
            String OS_Type = System.getProperty("os.name").toLowerCase();

            if (OS_Type.contains("linux")) {
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setBinary("/ScannPal/chrome-linux64/chrome");
                chromeOptions.addArguments("--headless");
                chromeOptions.addArguments("--no-sandbox");
                System.out.println("Creating ChromeDriver for Linux...");
                driver = new ChromeDriver(chromeOptions);
            }
            else { // Windows or Mac
                System.out.println("Creating ChromeDriver for Windows/Mac...");
                driver = new ChromeDriver();
            }
        }
        return driver;
   } 
}
