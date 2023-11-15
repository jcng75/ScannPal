package backend.classes;

// import org.openqa.selenium.By; 
import org.openqa.selenium.WebDriver; 
import org.openqa.selenium.safari.SafariDriver;

public class Safari {

    public static void main(String[] args) {
        // Instantiate a SafariDriver class. 
        WebDriver driver = new SafariDriver(); 

        // Launch Website 
        driver.navigate().to("http://www.amazon.com/");
    } 
}
