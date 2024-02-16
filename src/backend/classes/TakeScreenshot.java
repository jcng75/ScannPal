package backend.classes;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;

public class TakeScreenshot extends TestAction {

   String fileName;
   
   public TakeScreenshot(String fileName){
      String newFileName = fileName + getTimeStamp() + ".jpg";
      setFileName(newFileName);
      MyWebDriver.getDriver().manage().window().maximize();
   }

   public TakeScreenshot(){
      String time = getTimeStamp() + ".jpg";
      setFileName(time);
      MyWebDriver.getDriver().manage().window().maximize();
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
      File src = ((TakesScreenshot)MyWebDriver.getDriver()).getScreenshotAs(OutputType.FILE);
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
