package backend.classes;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class EnterText extends TestAction {

   private String id;
   private String text;

   /* Constructor */
   public EnterText(String id, String text) {
      setID(id);
      setText(text);
   }

   /* Getter and Setter Methods */

   public void setID(String id) {
      this.id = id;
   }

   public String getID() {
      return this.id;
   }

   public void setText(String text) {
      this.text = text;
   }

   public String getText() {
      return this.text;
   }

   public WebElement getTextbox() {
      try {
         WebElement textbox = MyWebDriver.getDriver().findElement(By.id(id));
         return textbox;
      } 
      catch (Exception e) {
         System.out.println("Id didn't work trying to search by name...");
         WebElement textbox = MyWebDriver.getDriver().findElement(By.name(id));
         return textbox;
      }
   }

   public void execute() {
      String text = this.getText();
      WebElement textbox = this.getTextbox();
      textbox.sendKeys(text);
   }
}
