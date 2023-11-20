package backend.classes;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;

public class TakeScreenshot extends TestAction {

   String fileName;
   
   public TakeScreenshot(String fileName){
      setFileName(fileName);
      MyWebDriver.getDriver().manage().window().maximize();
   }

   public void setFileName(String fileName){
      this.fileName = fileName;
   }
   
   public String getFileName(){
      return this.fileName;
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
