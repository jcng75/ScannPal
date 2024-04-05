package backend.scan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.openqa.selenium.WebDriver;

import backend.results.TestCase;
import backend.seleniumActions.ClickButton;
import backend.seleniumActions.EnterText;
import backend.seleniumActions.TestAction;
import backend.seleniumActions.VisitUrl;
import backend.utility.DeleteFile;


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
        
        String s = String.format("\n(+) Begin crawling for website: %s", url + "\n");
        System.out.println(s);

        System.out.println("(-) Clearing Photos Directory...");
        DeleteFile.clearDir("photos");
        
        WebDriver driver = MyWebDriver.getDriver();
        VisitUrl visitUrl = new VisitUrl(this.url);
        visitUrl.execute();
        
        loginUser();
        System.out.println(String.format("(-) Logged into website: %s", driver.getTitle()));
        
        String currentURL = driver.getCurrentUrl();
        
        // Add the starting link to the first queue
        Queue<TestCase> currentQueue = new LinkedList<TestCase>();
        Queue<TestCase> nextQueue = new LinkedList<TestCase>();

        // Hashset to make unique links only
        HashSet<String> hashSet = new HashSet<String>();
        
        // Initialize starting test case
        // Create the login action
        VisitUrl visitLogin = new VisitUrl(this.url);
        EnterText enterUser = new EnterText("username", this.username);
        EnterText enterPassword = new EnterText("password", this.password);
        ClickButton loginButton = new ClickButton("Login");
        VisitUrl visitHome = new VisitUrl(currentURL);
        List<TestAction> initialLogin = new ArrayList<TestAction>(Arrays.asList(visitLogin, enterUser, enterPassword, loginButton, visitHome));
        TestCase initialTestCase = new TestCase(initialLogin);
        
        nextQueue.add(initialTestCase);
        
        for (int i = 0; i < depth ; i++){
            System.out.println("(-) Current Depth: " + (i+1));
            currentQueue = new LinkedList<>(nextQueue);
            nextQueue.clear();
            for (TestCase tc : currentQueue){
                List<TestCase> updatedTC = tc.extend(tc, hashSet); 
                // for (TestCase testy : updatedTC){
                    // testy.display();
                    // System.out.println("\n");
                // }
                nextQueue.addAll(updatedTC);
            }
        }

        // Return information
        List<TestCase> resultList = new ArrayList<TestCase>(currentQueue);
        // System.out.println(resultList);
        return resultList;
    }

}