package backend.classes;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class EnterText extends TestAction {

   private String id;
   private String text;
   private WebElement textbox;

   /* Constructor */
   public EnterText(String id, String text) {
      setID(id);
      setText(text);
      setTextbox(id);
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

   /* get the textbox by ID, set the WebElement class variable to the textbox */
   public void setTextbox(String id) {
      this.textbox = MyWebDriver.getDriver().findElement(By.id(id));
   }

   public WebElement getTextbox() {
      return this.textbox;
   }

   public void execute() {
      String text = this.getText();
      WebElement textbox = this.getTextbox();
      textbox.sendKeys(text);
   }
}
