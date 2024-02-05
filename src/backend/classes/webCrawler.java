package backend.classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.openqa.selenium.WebDriver;


public class WebCrawler {
    
    String url;
    String username;
    String password;

    public WebCrawler(String url, String username, String password) {
        setUrl(url);
        setUsername(username);
        setPassword(password);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUsername(String user) {
        this.username = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private void loginUser() {
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

    /*
    public boolean crawl(int depth) {
        
        System.out.println(parseURLPath(url));
        String s = String.format("Crawling for website: %s", url);
        System.out.println(s);
        
        WebDriver driver = MyWebDriver.getDriver();
        VisitUrl visitUrl = new VisitUrl(this.url);
        visitUrl.execute();
        
        loginUser();
        System.out.println(String.format("Logged into website: %s", driver.getTitle()));
        System.out.println(String.format("Current URL: %s", driver.getCurrentUrl()));
        
        String currentURL = driver.getCurrentUrl();
        
        // Initialize web element list that will contain all possible action paths
        LinkedList<LinkedList<String>> elementList = new LinkedList<LinkedList<String>>();
        // Initalize link elements queue that will help us navigate through webpage
        // Add the starting link to the queue
        Queue<LinkedList<String>> linkQueue = new LinkedList<LinkedList<String>>();
        LinkedList<String> initialLink = new LinkedList<String>(Arrays.asList(currentURL));
        // add a hashset
        linkQueue.add(initialLink);
        elementList.add(initialLink);
        
        // Need a depth counter to make sure we go the specified depth of the user
        int depthCounter = 0;
        // The currLinkCounter is used to track the total links from each "layer"
        // The newLinkCounter tracks it for the next layer
        int currLinkCounter = 1;
        int newLinkCounter = 0;
        while (depthCounter != depth && !linkQueue.isEmpty()) {
            System.out.println("Current depth: " + depthCounter);
            // System.out.println("Current element queue: " + linkQueue);
            // Extract the first element of queue and the link associated with the element
            LinkedList<String> currentList = linkQueue.poll();
            String currentLink = currentList.getLast();
            // System.out.println(currentLink);
            // Go to new link
            List<WebElement> elements = MyWebDriver.getDriver().findElements(By.tagName("a"));
            // System.out.println("Current queue: " + linkQueue);
            for (WebElement element : elements) {
                String newLink = element.getAttribute("href");
                if (heuristicsCheck(element, currentLink)) continue;
                LinkedList<String> newList = new LinkedList<String>(currentList);
                newList.add(newLink);
                linkQueue.add(newList);
                elementList.add(newList);
                newLinkCounter++;
            }
            currLinkCounter--;
            // If the current link counter reaches 0 we must go to the next depth of scanning
            if (currLinkCounter == 0) {
                depthCounter++;
                currLinkCounter = newLinkCounter;
                newLinkCounter = 0;
            }
            
        }
        
        System.out.println(elementList);
        
        // run crawler and then store information
        return true;
    }
    */
    
    // Fu version
    public List<TestCase> crawl2(int depth) {
        
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
        
        for (int i = 0; i < depth ; i++) {
            System.out.println("Current Depth: " + (i+1));
            currentQueue = new LinkedList<>(nextQueue);
            nextQueue.clear();
            for (TestCase tc : currentQueue) {
                List<TestCase> updatedTC = tc.extend(tc, hashSet); 
                for (TestCase testy : updatedTC) {
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