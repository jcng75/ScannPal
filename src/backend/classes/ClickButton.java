package backend.classes;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ClickButton extends TestAction {

   private WebElement button;

   /* Constructor */
   public ClickButton(WebDriver driver, String tagName) {
      setDriver(driver);
      setButton(tagName);
   }

   public ClickButton(WebDriver driver) {
      setDriver(driver);
      setButton();
   }

   // If we know the id
   public void setButton(String idName) {
      WebDriver driver = this.getDriver();
      this.button = driver.findElement(By.id(idName));
   }

   // If we are looking for a button just find a button
   public void setButton() {
      WebDriver driver = this.getDriver();
      this.button = driver.findElement(By.tagName("button"));
   }

   public WebElement getButton() {
      return this.button;
   }

   public void execute() {
      WebElement button = this.getButton();
      button.click();
   } 
}
