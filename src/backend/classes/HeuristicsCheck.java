package backend.classes;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
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

    private boolean isDifferentWebsite(String parsedUrl, WebElement element) {
        String link = element.getAttribute("href");
        return parsedUrl.equals(parseURLHost(link));
    }

    private boolean isLogout(WebElement element){

        LinkedList<String> bannedList = new LinkedList<String>(Arrays.asList("logout", "signout", "log out", "sign out"));
        String elementText = element.getText().toLowerCase();
        return bannedList.contains(elementText);
        
    }

    private boolean isMarked(String link, HashSet<String> hashSet){
        return hashSet.contains(link);
    }

    public boolean heuristicsCheck(WebElement element, String currentLink, HashSet<String> hashSet){
        
        // if any of the heuristics hold, we can skip the web element within the crawl function
        if (isStale(element)) return true;
        if (isDifferentWebsite(currentLink, element)) return true;
        if (isLogout(element)) return true;
        if (isMarked(currentLink, hashSet)) return true;

        return false;
    }

    public String parseURLHost(String url){
        try {
            URL fullUrl = new URL(url);
            String startUrl = fullUrl.getHost();
            return startUrl;
        } catch (MalformedURLException e) {
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
