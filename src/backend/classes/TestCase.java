package backend.classes;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.netty.handler.codec.http.HttpContentEncoder.Result;

import java.io.Serializable;

public class TestCase implements Serializable {
    private List<TestAction> testActions;
    private boolean isInjected;
    private String attackType;
    private String payload;

    public TestCase() {
        this.testActions = new ArrayList<TestAction>();
        setInjected(false);
    }

    public TestCase(List<TestAction> actions) {
        this.testActions = actions;
    }

    public void setAttackType(String attackType){
        this.attackType = attackType;
    }

    public String getAttackType(){
        return this.attackType;
    }

    public void setPayload(String payload){
        this.payload = payload;
    }

    public String getPayload(){
        return this.payload;
    }

    public TestCase clone(){
        TestCase newTestCase = new TestCase();
        for (TestAction testAction : this.getTestCase()){
            if (testAction instanceof EnterText){
                EnterText newEnterText = (EnterText) testAction;
                newTestCase.append(newEnterText.clone());
            }
            else if (testAction instanceof VisitUrl){
                VisitUrl newVisitUrl = (VisitUrl) testAction;
                newTestCase.append(newVisitUrl.clone());
            }
            else if (testAction instanceof ClickButton){
                ClickButton newClickButton = (ClickButton) testAction;
                newTestCase.append(newClickButton);
            }
        }
        return newTestCase;
    }

    public List<TestAction> getTestCase() {
        return this.testActions;
    }

    public void setInjected(boolean isInjected){
        this.isInjected = isInjected;
    }

    public boolean getInjected() {
        return this.isInjected;
    }

    public TestAction getLast(){
        return this.testActions.get(this.testActions.size()-1);
    }

    // returns the size of a TestCase object (how many TestActions it has)
    public int size() {
        return this.testActions.size();
    }

    // returns a singular TestAction by index
    public TestAction get(int i) {
        return this.testActions.get(i);
    }

    public void append(TestAction action) {
        this.testActions.add(action);
    }

    // Needs working on, must return list of resulting test cases
    public List<TestCase> extend(TestCase testCase, HashSet<String> hashSet) {
        HeuristicsCheck hc = new HeuristicsCheck();
        List<TestCase> newTestCases = new ArrayList<TestCase>(); 
        // If the testCase cannot be extended any further, return empty list
        if (!hc.canExtend(testCase)){
            return newTestCases;
        } else {
            WebDriver driver = MyWebDriver.getDriver();
            // Add new link test cases
            VisitUrl lastTestAction = (VisitUrl) testCase.getLast();
            String currentPage = lastTestAction.getURL();
            // System.out.println(currentPage);
            driver.get(currentPage);
            while (hc.isAlertPresent()){
            // Switch to the alert and accept it
                Alert alert = MyWebDriver.getDriver().switchTo().alert();
                alert.accept();
            }         
            // 1. Get all links to make a new test case
            List<WebElement> pageLinks = driver.findElements(By.tagName("a"));
            for (WebElement linkElement : pageLinks){
                // If heuristics pass, clone the current testcase and add the new link
                if (!hc.heuristicsCheck(linkElement, currentPage, hashSet)) {
                    String newPage = linkElement.getAttribute("href");
                    hashSet.add(newPage);
                    VisitUrl newAction = new VisitUrl(newPage);
                    TestCase newTestCase = testCase.clone();
                    newTestCase.append(newAction);
                    newTestCases.add(newTestCase);
                }
            }
            // Add all textboxes to test case 
            List<WebElement> pageInputs = new ArrayList<>();
            pageInputs.addAll(driver.findElements(By.xpath("//input[@type='text']"))); 
            pageInputs.addAll(driver.findElements(By.xpath("//input[@type='password']"))); 
            pageInputs.addAll(driver.findElements(By.tagName("textarea")));
            /*
            [h, 1] [i1, i2, i3]
            [[h, 1, i1, i2, i3]]
            */
            TestCase newInputTestBaseCase = testCase.clone();
            for (WebElement textElement : pageInputs){
                String identifierString = textElement.getAttribute("id");
                if (identifierString.isEmpty()){
                    identifierString = textElement.getAttribute("name");
                }
                // Ask what to do if empty here?
                EnterText newEnterTextAction = new EnterText(identifierString, ":))");
                newInputTestBaseCase.append(newEnterTextAction);
            }
            // Get all possible buttons from the page
            List<WebElement> pageButtons = new ArrayList<WebElement>();
            pageButtons.addAll(driver.findElements(By.tagName("button")));
            pageButtons.addAll(driver.findElements(By.xpath("//input[@type='button']")));
            pageButtons.addAll(driver.findElements(By.xpath("//input[@type='submit']")));
            // Add one button per end of a test case
            for (WebElement buttonElement : pageButtons){
                if (hc.isBadButton(buttonElement)) continue;
                String identifierButtonString = buttonElement.getAttribute("id");
                if (identifierButtonString.isEmpty()){
                    identifierButtonString = buttonElement.getAttribute("name");
                }
                // Ask what to do if empty here?
                ClickButton newButtonAction = new ClickButton(identifierButtonString);
                TestCase newInputTestCase = newInputTestBaseCase.clone();
                // newInputTestCase.display();
                newInputTestCase.append(newButtonAction);
                newTestCases.add(newInputTestCase);
            }

        }
        return newTestCases;
    }
    // [[baseCase1, injectedCase, injectedCase], [baseCase2, injectedCase, injectedCase]]

    public TestResult runTestCase(TestCase baseTestCase, TestCase injectedTestCase, String fileName){ 
        System.out.println("\n(+) Running Test Case: " + fileName + "\n");  
        int clickButtonCounter = 0;
        String fullFileName = "";
        String htmlResult = "";
        boolean xssVulnerable = false;
        List<TestAction> testActions = this.getTestCase();
        for (TestAction testAction : testActions){
            testAction.execute();
            if (testAction instanceof ClickButton){
                if (++clickButtonCounter == 2){
                    TakeScreenshot takeScreenshot = new TakeScreenshot(fileName);
                    takeScreenshot.execute();
                    fullFileName = takeScreenshot.getFileName();
                    // save screenshot string
                    htmlResult = MyWebDriver.getDriver().getPageSource();
                    ClickButton currentClickButton = (ClickButton) testAction;
                    try {
                        boolean correctAlertMessage = currentClickButton.getAlertMessage().equals("1") || 
                        currentClickButton.getAlertMessage().contains("XSS");
                        if (currentClickButton.hasAlert() && correctAlertMessage){
                            System.out.println("(!!) Alert detected");
                            xssVulnerable = true;
                        }
                    } catch (NullPointerException e){

                    }
                }
            }
        }
        
        TestResult newResult = new TestResult(htmlResult, fullFileName, baseTestCase, injectedTestCase);
        newResult.setVulnerability(xssVulnerable);
        return newResult;
    }

    public void display() {
        for (TestAction action : testActions){
            System.out.println(action);
        }
    }
}
