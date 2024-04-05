package backend.results;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import backend.scan.AttackInjector;
import backend.utility.DeleteFile;

public class TestResult {
   
    private String htmlResult;
    private String photoName;
    private TestCase baseCase;
    private TestCase injectedCase;
    private boolean isVulnerable;
    private String comparisonPhoto;

    public TestResult(String htmlResult, String photoName, TestCase baseCase, TestCase injectedCase){
        this.htmlResult = htmlResult;
        this.photoName = photoName;
        this.baseCase = baseCase;
        this.injectedCase = injectedCase;
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

    public TestCase getInjectTestCase(){
        return this.injectedCase;
    }

    public void setVulnerability(boolean isVulnerable){
        this.isVulnerable = isVulnerable;
    }

    public boolean getVulnerable() {
        return this.isVulnerable;
    }

    public void setComparisonPhoto(String comparisonPhoto){
        this.comparisonPhoto = comparisonPhoto;
    }

    public String getComparisonPhoto(){
        return this.comparisonPhoto;
    }

    public static List<TestResult> generateResults(List<TestCase> testCases) throws IOException{

        DeleteFile.clearDir("photos");
        
        List<List<TestCase>> injectedCases = AttackInjector.generateInjectedCases(testCases);
        List<TestResult> testResults = new ArrayList<TestResult>();

        // System.out.println(injectedCases.size());
        System.out.println("\n(+) Generating Test Results");
        for (int i = 0; i < injectedCases.size(); i++){
            List<TestCase> testCaseGroup = injectedCases.get(i);
            int counter = 1;
            @SuppressWarnings("unused")
			TestResult baseTestResult = new TestResult(null, null, null, null);
            String addString = "TestCase" + (i+1) + "--";
            TestCase originalTestCase = testCaseGroup.get(0);
            for (int j = 0; j < testCaseGroup.size(); j++){
                TestCase currentTestCase = testCaseGroup.get(j);
                if (j == 0){
                    try {
                        TestResult tr = testCaseGroup.get(j).runTestCase(originalTestCase, currentTestCase, addString + "BaseCase--");
                        baseTestResult = tr;
                        testResults.add(tr);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    TestResult tr = testCaseGroup.get(j).runTestCase(originalTestCase, currentTestCase, addString + "InjectedCase" + counter + "--" + testCaseGroup.get(j).getAttackType() + "--");
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