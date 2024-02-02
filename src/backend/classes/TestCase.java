package backend.classes;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.Serializable;

public class TestCase implements Serializable {
    private List<TestAction> testCases;
    private boolean vulnerable;

    public TestCase() {
        this.testCases = new ArrayList<TestAction>();
    }

    public TestCase(List<TestAction> actions) {
        this.testCases = actions;
    }

    public List<TestAction> getTestCase() {
        return this.testCases;
    }

    public TestAction getLast(){
        return this.testCases.get(this.testCases.size()-1);
    }

    public void append(TestAction action) {
        this.testCases.add(action);
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
            driver.get(currentPage);
            // 1. Get all links to make a new test case
            List<WebElement> pageLinks = driver.findElements(By.tagName("a"));
            for (WebElement linkElement : pageLinks){
                // If heuristics pass, clone the current testcase and add the new link
                if (!hc.heuristicsCheck(linkElement, currentPage, hashSet)) {
                    String newPage = linkElement.getAttribute("href");
                    hashSet.add(newPage);
                    VisitUrl newAction = new VisitUrl(newPage);
                    TestCase newTestCase = new TestCase(testCase.getTestCase());
                    newTestCase.append(newAction);
                    newTestCases.add(newTestCase);
            }
        }
            // Add all textboxes to test case 
            List<WebElement> pageInputs = driver.findElements(By.xpath("//input[@type='text']"));
            /*
            [h, 1] [i1, i2, i3]
            [[h, 1, i1, i2, i3]]
             */
            TestCase newInputTestBaseCase = new TestCase(testCase.getTestCase());
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
                TestCase newInputTestCase = new TestCase(newInputTestBaseCase.getTestCase());
                newInputTestCase.append(newButtonAction);
                newTestCases.add(newInputTestCase);
            }

        }
        return newTestCases;
    }

    public void display() {
        System.out.println(testCases);
    }
}
