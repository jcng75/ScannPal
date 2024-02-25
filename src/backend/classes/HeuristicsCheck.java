package backend.classes;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.net.MalformedURLException;
import java.net.URL;

public class HeuristicsCheck {

    public HeuristicsCheck(){
    }

    private boolean isStale(WebElement element){
        return ExpectedConditions.stalenessOf(element).apply(MyWebDriver.getDriver());
    }

    private boolean isDifferentWebsite(String currentWebsite, WebElement element) {
        String link = element.getAttribute("href");
        String parsedUrl = parseURLHost(currentWebsite);
        return !parsedUrl.equals(parseURLHost(link));
    }

    private boolean isNull(WebElement element){
        String currentLink = element.getAttribute("href");
        return currentLink == null || "".equals(currentLink);
    }

    private boolean isLogout(WebElement element){

        LinkedList<String> bannedList = new LinkedList<String>(Arrays.asList("logout", "signout", "log out", "sign out"));
        String elementText = element.getText().toLowerCase();
        return bannedList.contains(elementText);
        
    }

    private boolean isMarked(String link, HashSet<String> hashSet){
        return hashSet.contains(link);
    }

    // hardcoded for testing website
    public boolean isBadButton(WebElement element){
        String elementName = element.getAttribute("name");
        if (elementName.equals("create_db")){
            return true;
        }
        if (elementName.equals("Change")){
            return true;
        }
        return false;
    }

    private boolean isBadLink(WebElement element){
        String webLink = element.getAttribute("href");
        String parsedPath = parseURLPath(webLink);
        // System.out.println(parsedPath);
        return parsedPath.equals("/vulnerabilities/captcha/");
    }

    private boolean isBadRequest(WebElement element){
        String webLink = element.getAttribute("href");
        TestHttpRequest testHttpReq = new TestHttpRequest(webLink);
        int responseCode = testHttpReq.getRequest();
        return responseCode >= 400;
    }

    public boolean heuristicsCheck(WebElement element, String currentLink, HashSet<String> hashSet){
        
        // if any of the heuristics hold, we can skip the web element within the crawl function
        if (isStale(element)) return true;
        if (isNull(element)) return true;
        // if (isBadRequest(element)) return true;
        if (isBadLink(element)) return true;
        if (isDifferentWebsite(currentLink, element)) return true;
        if (isLogout(element)) return true;
        if (isMarked(currentLink, hashSet)) return true;

        return false;
    }

    public boolean canExtend(TestCase tc){
        return tc.getLast().getClass() == VisitUrl.class;
    }
    
    public boolean isAlertPresent() {
        WebDriver driver = MyWebDriver.getDriver();
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    public String parseURLHost(String url){
        try {
            URL fullUrl = new URL(url);
            String startUrl = fullUrl.getHost();
            return startUrl;
        } catch (MalformedURLException e) {
            System.out.println(url);
            e.printStackTrace();
        }
        return url;
        
    }
    
    public String parseURLPath(String url){
        try {
            URL fullUrl = new URL(url);
            String path = fullUrl.getPath();
            return path;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

}
