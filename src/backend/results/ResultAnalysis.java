package backend.results;

import java.io.IOException;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.WebDriver;

import backend.aws.MySQLConnection;
import backend.scan.MyWebDriver;
import backend.utility.DeleteFile;

public interface ResultAnalysis {

    private static boolean sqliCheck(String HTML, TestResult injectedTestResult){
        List<String> errorList = new ArrayList<String>(Arrays.asList(
        "You have an error in your SQL syntax", 
        "Unclosed quotation mark after the character string ''",
        "quoted string not properly terminated",
        "check the manual", 
        "Incorrect syntax near",
        "Unclosed quotation",
        "Invalid expression",
        "Rule does not contain a variable.",
        "Rule contains more than one variable.",
        "Invalid column name",
        "Invalid object name",
        "Ambiguous column name"
        )); 
        for (String error : errorList){
            if (HTML.contains(error)){
                injectedTestResult.setVulnerability(true);
                return true;
            }
        }
        return false;
    }

    private static boolean xssCheck(String htmlSource, String payload){
        return htmlSource.contains(payload);
    }
    
    /* private static boolean cmdCheck(String HTML){
        return true;
    } */

    private static boolean checkSpecificAttack(String attackType, String htmlString, String payload, TestResult injectedTestResult){
        
        if (attackType == "SQL"){
            return sqliCheck(htmlString, injectedTestResult);
        } else if (attackType == "XSS"){
            return xssCheck(htmlString, payload);
        } else {
            return false;
        }
    
    }

    private static boolean imageSimilarityCheck(String basePhotoName, String injectedPhotoName, TestResult injectedResult) throws IOException{
        String path = "photos/";
        float similarity = ImageDifferenceBox.getPercentDifference(path + basePhotoName, path + injectedPhotoName);
        if (similarity > 0.9){
            // Fix later
            String resultPhoto = resultFileName(injectedPhotoName);
            ImageDifferenceBox.compareImages(path + basePhotoName, path + injectedPhotoName, path + resultPhoto);
            injectedResult.setComparisonPhoto(resultPhoto);
            return true;
        }
        return false;
    }

    // helper method for imageSimilarityCheck to create a filename for a result image
    public static String resultFileName(String injectedPhotoName) {
        String separator = "--";
        String[] fileNameParts = injectedPhotoName.split(separator);
        String currentTimeStamp = new SimpleDateFormat("yyyy-MM-dd--HH-mm-ss").format(new Date());
        String newFileName = fileNameParts[0] + separator + fileNameParts[1] + separator + fileNameParts[2] + separator + "Result" + separator + currentTimeStamp + ".png";
        return newFileName;
    }

    private static boolean htmlSimilarityCheck(String baseHTML, String injectedHTML){
        // If the percentage is lower than 50%, we can assume an injection was successful
        double similarity = StringSimilarity.similarity(baseHTML, injectedHTML);
        if (similarity < 50.00){
            return true;
        }
        return false;
    }

    public static void runAnalysis(List<TestResult> testResults, int taskID) throws IOException{
        TestCase currentBaseCase = null;
        TestResult currentBaseResult = null;
        for (TestResult tr : testResults){
            if (!tr.getBaseCase().equals(currentBaseCase)){
                currentBaseCase = tr.getBaseCase();
                currentBaseResult = tr;
            }
            analyzeResult(currentBaseResult, tr, taskID);
            // Delete the BaseCase images that do not have corresponding InjectedCase images
            DeleteFile.deleteNonVulnerableImages("photos");
        }
    }
    
    public static void analyzeResult(TestResult baseResult, TestResult injectedResult, int taskID) throws IOException{

        // Ignore all basecases to be tested in our algorithm
        if (baseResult.equals(injectedResult)){
            return;
        }

        String basePhoto = baseResult.getPhotoName();
        String injectedPhoto = injectedResult.getPhotoName();
        String baseHTML = baseResult.getHtmlResult();
        String injectedHTML = injectedResult.getHtmlResult();
        
        System.out.println(String.format("(*) Comparing Case %s to %s", basePhoto, injectedPhoto));
        // Check for specific injected heuristic type
        String injectedResultType = injectedResult.getInjectTestCase().getAttackType();
        String payload = injectedResult.getInjectTestCase().getPayload();
        int counter = 0;

        // First check specific attack
        if (checkSpecificAttack(injectedResultType, injectedHTML, payload, injectedResult)){
            counter++;
        }
        // Check html similarity
        if (htmlSimilarityCheck(baseHTML, injectedHTML)){
            counter++;
        }
        // Next check image similarity
        if (imageSimilarityCheck(basePhoto, injectedPhoto, injectedResult)){
            counter++;
        }
        
        if (counter >= 2){
            System.out.println("(!!) TestResult is vulnerable to " + injectedResultType +  " attack!");
            System.out.println("(+) Payload used: " + payload);
        }
        else if (injectedResult.getVulnerable()){
            System.out.println("(!!!) TestResult is directly vulnerable to " + injectedResultType + "!");
            System.out.println("(+) Payload used: " + payload);
        }
        else{
            System.out.println("(✓) TestResult is not vulnerable!  Removing screenshot file");
            DeleteFile.deleteFile(injectedPhoto);
        }

        // store results into database
        // we have taskID from parameter
        boolean vulnerable = injectedResult.getVulnerable();
        
        if (vulnerable) {
            MySQLConnection connection = new MySQLConnection();
            // get the payload, attack type, and html string to be inserted into the database
            String attackPayload = injectedResult.getInjectTestCase().getPayload();
            String attackType = injectedResult.getInjectTestCase().getAttackType();
            String htmlString = injectedResult.getHtmlResult();
        
            // store the screenshot as a BLOB
            Blob screenshotBlob = null;
            String resultPhotoFileName = injectedResult.getComparisonPhoto();
            String photosDir = "photos/" + resultPhotoFileName;
            screenshotBlob = connection.convertScreenshotToBlob(photosDir);
            System.out.println("Printing screenshotBlob for debugging: " + screenshotBlob);
            
            // save the results to the Result table
            connection.addResult(taskID, vulnerable, attackPayload, attackType, htmlString, screenshotBlob);
        }

        System.out.println("\n");
    }

    public static void checkAlert(TestResult testResult){
        WebDriver driver = MyWebDriver.getDriver();
        String alertMessage = driver.switchTo().alert().getText(); // capture alert message
        if (alertMessage.equals("1") || alertMessage.contains("XSS")){
            testResult.setVulnerability(true);
        }
    }

    /* 
    To-do:
    - Check for alerts for xss attacks
    - Update attack heuristics
    - Help adam with splitting thing
    - EC2 communication script
    - Create demo
    - Ensure that if vulnerable update TestResult to be set as vulnerable 
    */
    
}
