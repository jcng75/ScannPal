package backend.classes;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;


public class WebCrawler {
    
    String url;
    String username;
    String password;

    public WebCrawler(String url, String username, String password){
        setUrl(url);
        setUsername(username);
        setPassword(password);
    }

    public void setUrl(String url){
        this.url = url;
    }

    public void setUsername(String user){
        this.username = user;
    }

    public void setPassword(String password){
        this.password = password;
    }

    private void loginUser(){
        // Enter username field
        EnterText enterUser = new EnterText("exampleInputEmail1" , username);
        enterUser.execute();
        // Enter password field
        EnterText enterPass = new EnterText("exampleInputPassword1", password);
        enterPass.execute();
        // Click on login button
        ClickButton loginButton = new ClickButton();
        loginButton.execute();
    }

    private boolean isStale(WebElement element){
        return ExpectedConditions.stalenessOf(element).apply(MyWebDriver.getDriver());
    }

    public boolean crawl(int depth){

        String s = String.format("Crawling for website: %s", url);
        System.out.println(s);

        WebDriver driver = MyWebDriver.getDriver();

        driver.get(this.url);

        loginUser();
        System.out.println(String.format("Logged into website: %s", driver.getTitle()));

         List<WebElement> elements = MyWebDriver.getDriver().findElements(By.tagName("a"));

        int nonStaticLink = 0;
        for (WebElement element : elements){

            if (isStale(element)){
                continue;
            }
            nonStaticLink += 1;
        }
        if (nonStaticLink > 0){
            System.out.println("Could not find any links. Ending crawl...");
            return false;
        }

        // run crawler and then store information
        return true;
    }

}