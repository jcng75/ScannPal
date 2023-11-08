package backend.classes;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ClickButton extends TestAction {

   private WebElement button;

   /* Constructor */
   public ClickButton(String tagName) {
      setButton(tagName);
   }

   public void setButton(String tagName) {
      //this.button = driver.findElement(By.tagName(tagName));
      this.button = null;
   }

   public void execute() {
      button.click();
   } 
}
