package backend.classes;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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
         WebElement theChosenOne = null;
         try {
            List<WebElement> buttons = driver.findElements(By.xpath("//input[@type='submit']"));
            for (WebElement element : buttons){
               if (element.getAttribute("id").isEmpty() && element.getAttribute("name").isEmpty()){
                  theChosenOne = element;
                  break;
               }
            }
            return theChosenOne;
         } 
         catch (Exception e) {
            List<WebElement> buttons = driver.findElements(By.tagName("button")); 
            try {            
               for (WebElement element : buttons){
                  if (element.getAttribute("id").isEmpty() && element.getAttribute("name").isEmpty()){
                     theChosenOne = element;
                     break;
                  }
               }
               return theChosenOne;
            } 
            catch (Exception e1){           
               List<WebElement> inputButtons = driver.findElements(By.xpath("//input[@type='button']"));
               for (WebElement element : inputButtons){
                  if (element.getAttribute("id").isEmpty() && element.getAttribute("name").isEmpty()){
                     theChosenOne = element;
                     break;
                  }
               }
               return theChosenOne;
            }
         }
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

   public ClickButton clone(){
      return new ClickButton(this.idName);
   }

   public void execute() {
      WebElement button = this.getButton();
      button.click();
      HeuristicsCheck hc = new HeuristicsCheck();
      
      // If you get an alert after clicking a button accept alert
      if (hc.isAlertPresent()){
         // Switch to the alert and accept it
         Alert alert = MyWebDriver.getDriver().switchTo().alert();
         alert.accept();
      }
      
   } 

   @Override
    public String toString(){
      String returnString = String.format("ClickButton, idName = %s", idName);
      return returnString;
    }
}
