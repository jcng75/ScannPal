package backend.classes;

import org.openqa.selenium.WebDriver;

public class VisitUrl extends TestAction{
    
    private String url;

    public VisitUrl(String url) {
        setURL(url);
    }

    private void setURL(String url) {
        this.url = url;
    }

    private String getURL() {
        return this.url;
    }

    public void execute() {
        WebDriver driver = MyWebDriver.getDriver();
        driver.get(this.getURL());
        driver.manage().window().maximize();
    }
}
