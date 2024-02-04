package backend.classes;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ClickButton extends TestAction {

   String idName;

   /* Constructor */
   public ClickButton(String idName) {
      setIdName(idName);
   }

   public ClickButton(){
   }

   public void setIdName(String idName){
      this.idName = idName;
   }

   // If we don't know the id, search for it using findElement by tagname
   // Otherwise, use the id to get the element using findElement by id/name
   public WebElement getButton() {
      WebDriver driver = MyWebDriver.getDriver();
      if (this.idName.isEmpty()){
         return driver.findElement(By.tagName("button")); 
      } else {
         try {
            return driver.findElement(By.id(idName));
         }
         catch (Exception e){
            System.out.println("Could not locate using id, trying with name instead...");
            return driver.findElement(By.name(idName));
         }   
      }
   }

   public void execute() {
      WebElement button = this.getButton();
      button.click();
   } 

   @Override
    public String toString(){
      return idName;
    }
}
