package backend.classes;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;

// For Cloud Tests
public class App {
    public static void main(String args[]) {
        System.setProperty("webdriver.chrome.driver", "/home/ec2-user/ScannPal/chromedriver-linux64/chromedriver");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setBinary("/home/ec2-user/ScannPal/chrome-linux64/chrome");
        chromeOptions.addArguments("--headless");
      
        WebDriver driver = MyWebDriver.getDriver();
        // driver.get("https://www.amazon.com/");
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
        enterText.setTextbox(passwordID);
        enterText.execute();

        // click the button
        String tagName = "button";
        ClickButton clickButton = new ClickButton(tagName);
        clickButton.execute();

        // WebElement username = driver.findElement(By.id("exampleInputEmail1"));
        // WebElement password = driver.findElement(By.id("exampleInputPassword1"));
        // WebElement login = driver.findElement(By.tagName("button"));

        // username.sendKeys("johnny");
        // password.sendKeys("abc123");
        // password.submit();
        // login.click();

        // check the updated URL
        String expectedURL = driver.getCurrentUrl();
        System.out.println("URL after login attempt: " + expectedURL);

        driver.close();
    }
}
