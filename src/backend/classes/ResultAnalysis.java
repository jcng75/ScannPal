package backend.classes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;

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
    
    private static boolean cmdCheck(String HTML){
        return true;
    }

    private static boolean checkSpecificAttack(String attackType, String htmlString, String payload, TestResult injectedTestResult){
        
        if (attackType == "SQL"){
            return sqliCheck(htmlString, injectedTestResult);
        } else if (attackType == "XSS"){
            return xssCheck(htmlString, payload);
        } else {
            return false;
        }
    
    }

    private static boolean imageSimilarityCheck(String basePhotoPath, String injectedPhotoPath, TestResult injectedResult) throws IOException{
        String path = "photos/";
        float similarity = ImageDifferenceBox.getPercentDifference(path + basePhotoPath, path + injectedPhotoPath);
        if (similarity > 0.9){
            // Fix later
            String comparisonPhoto = basePhotoPath + injectedPhotoPath;
            ImageDifferenceBox.compareImages(path + basePhotoPath, path + injectedPhotoPath, path + comparisonPhoto);
            injectedResult.setComparisonPhoto(comparisonPhoto);
            return true;
        }
        return false;
    }

    private static boolean htmlSimilarityCheck(String baseHTML, String injectedHTML){
        // If the percentage is lower than 50%, we can assume an injection was successful
        double similarity = StringSimilarity.similarity(baseHTML, injectedHTML);
        if (similarity < 50.00){
            return true;
        }
        return false;
    }

    public static void runAnalysis(List<TestResult> testResults) throws IOException{
        TestCase currentBaseCase = null;
        TestResult currentBaseResult = null;
        System.out.println("\n(***) Analyzing all results...\n");
        for (TestResult tr : testResults){
            if (!tr.getBaseCase().equals(currentBaseCase)){
                // Get the TestCaseNumber
                try{
                    String photoName = currentBaseResult.getPhotoName();
                    String startFile = photoName.split("--", 2)[0];
                    if (DeleteFile.checkFile("photos", startFile)){
                        System.out.println("(-) TestCase is not vulnerable!  Removing BaseCase file");
                        DeleteFile.deleteFile(photoName); 
                    }
                } catch (Exception e){

                }
                currentBaseCase = tr.getBaseCase();
                currentBaseResult = tr;
            }
            analyzeResult(currentBaseResult, tr);
        }
    }
    
    public static void analyzeResult(TestResult baseResult, TestResult injectedResult) throws IOException{

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
    TODO:
    - Check for alerts for xss attacks
    - Update attack heuristics
    - Help adam with splitting thing
    - EC2 communication script
    - Create demo
    - Ensure that if vulnerable update TestResult to be set as vulnerable 
    */
    
}
