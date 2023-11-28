package backend.classes;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

// For Local Tests
public class main {
    public static void main(String args[]) {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Justin Ng\\Downloads\\Installers\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        TestHttpRequest tRequest = new TestHttpRequest("http://localhost:8080/WebGoat/login"); 
        tRequest.getRequest();

        // WebDriver driver = new ChromeDriver();
        WebDriver driver = MyWebDriver.getDriver();
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
        ClickButton clickButton = new ClickButton();
        clickButton.execute();

        // check the updated URL
        String expectedURL = driver.getCurrentUrl();
        System.out.println("URL after login attempt: " + expectedURL);

        // Testing clicking button
        // String idName = "webwolf-button";
        // ClickButton clickButton2 = new ClickButton(driver, idName);
        // clickButton2.execute();

        String fileName = "screenshot.jpg";

        TakeScreenshot screenshot = new TakeScreenshot(fileName);
        screenshot.execute();

        // List<WebElement> el = driver.findElements(By.tagName("a"));
        // el.get(9).click();
        

        driver.close();
    }
}
