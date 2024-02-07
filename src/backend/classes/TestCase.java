package backend.classes;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.Serializable;

public class TestCase implements Serializable {
    private List<TestAction> testActions;
    private boolean isVulnerable;
    private boolean isInjected;

    public TestCase() {
        this.testActions = new ArrayList<TestAction>();
    }

    public TestCase(List<TestAction> actions) {
        this.testActions = actions;
    }

    public TestCase clone(){
        TestCase newTestCase = new TestCase();
        for (TestAction testAction : this.getTestCase()){
            newTestCase.append(testAction);
        }
        return newTestCase;
    }

    public List<TestAction> getTestCase() {
        return this.testActions;
    }

    public void setInjected(boolean isInjected){
        this.isInjected = isInjected;
    }

    public void setVulnerability(boolean isVulnerable){
        this.isVulnerable = isVulnerable;
    }

    public TestAction getLast(){
        return this.testActions.get(this.testActions.size()-1);
    }

    public void append(TestAction action) {
        this.testActions.add(action);
    }

    // Needs working on, must return list of resulting test cases
    public List<TestCase> extend(TestCase testCase, HashSet<String> hashSet) {
        HeuristicsCheck hc = new HeuristicsCheck();
        List<TestCase> newTestCases = new ArrayList<TestCase>(); 
        // System.out.println(hashSet);
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
            // 1. Get all links to make a new test case
            List<WebElement> pageLinks = driver.findElements(By.tagName("a"));
            for (WebElement linkElement : pageLinks){
                // If heuristics pass, clone the current testcase and add the new link
                if (!hc.heuristicsCheck(linkElement, currentPage, hashSet)) {
                    String newPage = linkElement.getAttribute("href");
                    hashSet.add(newPage);
                    VisitUrl newAction = new VisitUrl(newPage);
                    // TestCase newTestCase = new TestCase(testCase.getTestCase());
                    TestCase newTestCase = testCase.clone();
                    // System.out.println("\n");
                    newTestCase.append(newAction);
                    // newTestCase.display();
                    newTestCases.add(newTestCase);
                }
            }
            // Add all textboxes to test case 
            List<WebElement> pageInputs = new ArrayList<>();
            pageInputs.addAll(driver.findElements(By.xpath("//input[@type='text']"))); 
            pageInputs.addAll(driver.findElements(By.xpath("//input[@type='password']"))); 
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

    public TestResult runTestCase(TestCase tc){   
        int counter = 1;
        List<TestAction> testActions = tc.getTestCase();
        for (TestAction testAction : testActions){
            testAction.execute();
            if (testAction instanceof EnterText){
                if (++counter == 2){
                    // screenshot
                    // save screenshot string
                    // save html structure
                }
            }
        }
        return new TestResult("htmlResult", "fileName", tc);
    }

    public void display() {
        for (TestAction action : testActions){
            System.out.println(action);
        }
    }
}
