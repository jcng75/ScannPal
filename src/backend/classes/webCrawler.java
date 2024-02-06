package backend.classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.net.MalformedURLException;
import java.net.URL;

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
        EnterText enterUser = new EnterText("username" , username);
        enterUser.execute();
        // Enter password field
        EnterText enterPass = new EnterText("password", password);
        enterPass.execute();
        // Click on login button
        ClickButton loginButton = new ClickButton("Login");
        loginButton.execute();
    }
    
    // Fu version
    public List<TestCase> crawl(int depth){
        
        String s = String.format("Crawling for website: %s", url);
        System.out.println(s);
        
        WebDriver driver = MyWebDriver.getDriver();
        VisitUrl visitUrl = new VisitUrl(this.url);
        visitUrl.execute();
        
        loginUser();
        System.out.println(String.format("Logged into website: %s", driver.getTitle()));
        System.out.println(String.format("Current URL: %s", driver.getCurrentUrl()));
        
        String currentURL = driver.getCurrentUrl();
        
        // Add the starting link to the first queue
        Queue<TestCase> currentQueue = new LinkedList<TestCase>();
        Queue<TestCase> nextQueue = new LinkedList<TestCase>();

        // Hashset to make unique links only
        HashSet<String> hashSet = new HashSet<String>();
        
        // Initialize starting test case
        // Create the login action
        EnterText enterUser = new EnterText("username", this.username);
        EnterText enterPassword = new EnterText("password", this.password);
        ClickButton loginButton = new ClickButton("Login");
        VisitUrl visitHome = new VisitUrl(currentURL);
        List<TestAction> initialLogin = new ArrayList<TestAction>(Arrays.asList(enterUser, enterPassword, loginButton, visitHome));
        TestCase initialTestCase = new TestCase(initialLogin);
        
        nextQueue.add(initialTestCase);
        
        for (int i = 0; i < depth ; i++){
            System.out.println("Current Depth: " + (i+1));
            currentQueue = new LinkedList<>(nextQueue);
            nextQueue.clear();
            for (TestCase tc : currentQueue){
                List<TestCase> updatedTC = tc.extend(tc, hashSet); 
                for (TestCase testy : updatedTC){
                    testy.display();
                    System.out.println("\n");
                }
                nextQueue.addAll(updatedTC);
            }
        }

        // Return information
        List<TestCase> resultList = new ArrayList<TestCase>(currentQueue);
        // System.out.println(resultList);
        return resultList;
    }

}