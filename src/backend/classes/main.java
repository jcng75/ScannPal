package backend.classes;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

// For Local Tests
public class Main {
    public static void main(String args[]) {
        System.setProperty("webdriver.chrome.driver", "chromedriver-mac-x64/chromedriver");

        WebDriver driver = new ChromeDriver();
        driver.get("http://35.175.186.100:8080/WebGoat/login");
        System.out.println("Page title is: " + driver.getTitle());
        System.out.println("Page URL is: " + driver.getCurrentUrl());

        // enter text into username field
        String usernameID = "exampleInputEmail1";
        String usernameText = "johnny";
        EnterText enterText = new EnterText(driver, usernameID, usernameText);
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
        ClickButton clickButton = new ClickButton(driver, tagName);
        clickButton.execute();

        // check the updated URL
        String expectedURL = driver.getCurrentUrl();
        System.out.println("URL after login attempt: " + expectedURL);

        driver.close();
    }
}
