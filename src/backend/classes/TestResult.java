package backend.classes;

import java.util.ArrayList;
import java.util.List;

public class TestResult {
   
    String htmlResult;
    String photoName;
    TestCase baseCase;

    public TestResult(String htmlResult, String photoName, TestCase baseCase){
        this.htmlResult = htmlResult;
        this.photoName = photoName;
        this.baseCase = baseCase;
    }

    public String getHtmlResult() {
        return this.htmlResult;
    }

    public String getPhotoName() {
        return this.photoName;
    }

    public TestCase getBaseCase() {
        return this.baseCase;
    }

    public static List<TestResult> generateResults(List<TestCase> testCases){
        
        List<List<TestCase>> injectedCases = AttackInjector.generateInjectedCases(testCases);
        AttackInjector.displayAll(injectedCases);
        List<TestResult> testResults = new ArrayList<TestResult>();
        
        for (int i = 0; i < injectedCases.size(); i++){
            List<TestCase> testCaseGroup = injectedCases.get(i);
            int counter = 1;
            String addString = "--ForTestCase" + (i+1) + "--";
            TestCase originalTestCase = testCaseGroup.get(0);
            for (int j = 0; j < testCaseGroup.size(); j++){
                if (j == 0){
                    TestResult tr = testCaseGroup.get(j).runTestCase(originalTestCase, "baseCase" + addString);
                    testResults.add(tr);
                }
                else {
                    TestResult tr = testCaseGroup.get(j).runTestCase(originalTestCase, "injectedCase" + counter + addString);
                    testResults.add(tr);
                    counter++;
                }
            }
        }
        return testResults;
    }
    
    public void display(){
        System.out.println("Photo filename: " + getPhotoName());
        System.out.println("HTML Result:");
        System.out.println(getHtmlResult());
        System.out.println("\n");
    }

}
