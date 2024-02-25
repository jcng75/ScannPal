package backend.classes;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;

public class VisitUrl extends TestAction{
    
    String url;

    public VisitUrl(String url){
        setURL(url);
    }

    private void setURL(String url){
        this.url = url;
    }

    public String getURL(){
        return this.url;
    }

    public VisitUrl clone(){
        return new VisitUrl(this.url);
    }

    public void execute(){
        WebDriver driver = MyWebDriver.getDriver();
        driver.get(this.url);
        HeuristicsCheck hc = new HeuristicsCheck();
        while (hc.isAlertPresent()){
        // Switch to the alert and accept it
            Alert alert = MyWebDriver.getDriver().switchTo().alert();
            alert.accept();
        }         
        driver.manage().window().maximize();
    }

    @Override
    public String toString(){
        String returnString = String.format("VisitUrl, url = %s", url);
        return returnString;
    }

}
