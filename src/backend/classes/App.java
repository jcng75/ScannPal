package backend.classes;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class App {
    public static void main(String args[]) {
        System.setProperty("webdriver.chrome.driver", "/home/ec2-user/ScannPal/chromedriver-linux64/chromedriver");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setBinary("/home/ec2-user/ScannPal/chrome-linux64/chrome");
        chromeOptions.addArguments("--headless");
        WebDriver driver = new ChromeDriver(chromeOptions);
        //driver.get("https://www.amazon.com/");
        driver.get("http://localhost:8080/WebGoat/login");
        System.out.println("Page title is: " + driver.getTitle());
        System.out.println("Page URL is: " + driver.getCurrentUrl());

        WebElement username = driver.findElement(By.id("exampleInputEmail1"));
        WebElement password = driver.findElement(By.id("exampleInputPassword1"));
        WebElement login = driver.findElement(By.tagName("button"));

        username.sendKeys("abc@gmail.com");
        password.sendKeys("abc123");
        login.click();

        driver.close();
    }
}
