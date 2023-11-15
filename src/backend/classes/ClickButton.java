package backend.classes;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ClickButton extends TestAction {

   private WebDriver driver;
   private WebElement button;

   /* Constructor */
   public ClickButton(WebDriver driver, String tagName) {
      setDriver(driver);
      setButton(tagName);
   }

   public void setDriver(WebDriver driver) {
      this.driver = driver;
   }

   public WebDriver getDriver() {
      return this.driver;
   }

   public void setButton(String tagName) {
      WebDriver driver = this.getDriver();
      this.button = driver.findElement(By.tagName(tagName));
   }

   public WebElement getButton() {
      return this.button;
   }

   public void execute() {
      WebElement button = this.getButton();
      button.click();
   } 
}
