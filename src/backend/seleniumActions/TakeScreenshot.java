package backend.seleniumActions;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

import backend.scan.MyWebDriver;

public class TakeScreenshot extends TestAction {

   private static final boolean UnhandledAlertException = false;
   String fileName;
   
   public TakeScreenshot(String fileName){
      String newFileName = fileName + getTimeStamp() + ".png";
      setFileName(newFileName);
      // MyWebDriver.getDriver().manage().window().maximize();
   }

   public TakeScreenshot(){
      String time = getTimeStamp() + ".png";
      setFileName(time);
      // MyWebDriver.getDriver().manage().window().maximize();
   }

   public void setFileName(String fileName){
      this.fileName = fileName;
   }
   
   public String getFileName(){
      return this.fileName;
   }

   public static String getTimeStamp(){
      String fileName = new SimpleDateFormat("yyyy-MM-dd--HH-mm-ss").format(new Date());
      return fileName;
   }

   public void execute(){

      File src = null;
      

      WebDriver driver = MyWebDriver.getDriver();

         
      try {
         src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

      
      } catch (UnhandledAlertException e){
      
         Alert alert = driver.switchTo().alert();
         alert.dismiss();
      
      } catch (NoAlertPresentException noE){
         src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
      }
      


      String filePath = "photos/" + fileName;
      File newFile = new File(filePath);

      try {
         FileHandler.copy(src, newFile);
      } catch (IOException e) {
         System.out.println(e);
      }
      return;
   } 
}
