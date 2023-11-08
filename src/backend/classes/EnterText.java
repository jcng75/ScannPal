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
   }

   /* Getter and Setter Methods */
   public String getText() {
      return this.text;
   }

   public void setText(String text) {
      this.text = text;
   }

   public String getID() {
      return this.id;
   }

   public void setID(String id) {
      this.id = id;
   }

   /* get the textbox by ID, set the WebElement class variable to the textbox */
   public void setTextbox() {
      String id = getID();
      this.textbox = driver.findElement(By.id(id));
   }

   public void execute() {
      String text = getText();
      setTextbox();
      textbox.sendKeys(text);
      // System.out.println(String.format("This enters text %s", getText()));
   }
}
