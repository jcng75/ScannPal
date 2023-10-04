package backend.classes;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class App {
    public static void main(String args[]){
        System.setProperty("webdriver.chrome.driver", "C:/Users/Justin Ng/Downloads/Installers/chrome-win32/chromedriver-win32/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.google.com/");
    }
}
