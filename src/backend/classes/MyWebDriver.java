package backend.classes;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class MyWebDriver {
   private static WebDriver driver = null;
   public static WebDriver getDriver() {
        if (driver == null){
            driver = new ChromeDriver();
        }
        return driver;
   } 
}
