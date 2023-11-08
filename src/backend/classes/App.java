package backend.classes;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class App {

    public static ChromeOptions chromeOptions = new ChromeOptions();
    public static WebDriver driver = new ChromeDriver(chromeOptions);
    public static void main(String args[]) {

        System.setProperty("webdriver.chrome.driver", "/home/ec2-user/ScannPal/chromedriver-linux64/chromedriver");
        chromeOptions.setBinary("/home/ec2-user/ScannPal/chrome-linux64/chrome");
        chromeOptions.addArguments("--headless");
      
        //driver.get("https://www.amazon.com/");
        driver.get("http://localhost:8080/WebGoat/login");
        System.out.println("Page title is: " + driver.getTitle());
        System.out.println("Page URL is: " + driver.getCurrentUrl());

        // enter text into username field
        String usernameID = "exampleInputEmail1";
        String usernameText = "johnny";
        EnterText enterText = new EnterText(usernameID, usernameText);
        enterText.execute();

        // enter text into password field
        String passwordID = "exampleInputPassword1";
        String passwordText = "abc123";
        enterText.setID(passwordID);
        enterText.setText(passwordText);

        // click the button
        String tagName = "button";
        ClickButton clickButton = new ClickButton(tagName);
        clickButton.execute();

        // WebElement username = driver.findElement(By.id("exampleInputEmail1"));
        // WebElement password = driver.findElement(By.id("exampleInputPassword1"));
        // WebElement login = driver.findElement(By.tagName("button"));

        //username.sendKeys("johnny");
        //password.sendKeys("abc123");
        //password.submit();
        //login.click();

        String expectedURL = driver.getCurrentUrl();
        System.out.println("URL after login attempt: " + expectedURL);

        driver.close();
    }
}
