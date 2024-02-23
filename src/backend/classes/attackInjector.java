package backend.classes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.lang.Math;
import java.util.Scanner;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;




public class AttackInjector {

    public static List<TestCase> injectCase(TestCase originalTestCase) throws IOException {

        List<TestCase> injectionVariations = new ArrayList<>(); // make new arraylist to return with variations of injection

        injectionVariations.add(originalTestCase.clone()); // index 0 of each list of injected cases is the clean copy of the injected case
        HeuristicsCheck hc = new HeuristicsCheck(); // hc object
        Boolean needStop = hc.canExtend(originalTestCase); // to test if ends in URL

        if (needStop) { // if it ends in a URL, we don't want to inject this
            return injectionVariations; // returns empty list
        }
        
        // If we can continue peacefully:
        List<String> SQLPayloads = AttackInjector.getPayloads("SQL");
        for (String payload : SQLPayloads){

            for (int i = 3; i < originalTestCase.size(); i++) {
                TestAction action = originalTestCase.get(i);
                
                if (action instanceof EnterText) { // if we can inject this action, needs review
                    
                    // CLONE THE ORIGINAL CASE
                    TestCase maliciousTestCase = originalTestCase.clone();
                    System.out.println("Current Case: " + i);
                    maliciousTestCase.display();
                    System.out.println("\n");
                    
                    // SETUP BADBOX
                    EnterText badBox = (EnterText) maliciousTestCase.get(i); // grab the textbox from the malicious TestCase
                    badBox.setText(payload); // infect it
                    System.out.println("Updated case: " + i);
                    maliciousTestCase.display();
                    System.out.println("\n");
                    
                    // add test case to injected cases list
                    injectionVariations.add(maliciousTestCase);
                }
            }
        }
        List<String> XSSPayloads = AttackInjector.getPayloads("XSS");
        for (String payload : XSSPayloads){

            for (int i = 3; i < originalTestCase.size(); i++) {
                TestAction action = originalTestCase.get(i);
                
                if (action instanceof EnterText) { // if we can inject this action, needs review
                    
                    // CLONE THE ORIGINAL CASE
                    TestCase maliciousTestCase = originalTestCase.clone();
                    System.out.println("Current Case: " + i);
                    maliciousTestCase.display();
                    System.out.println("\n");
                    
                    // SETUP BADBOX
                    EnterText badBox = (EnterText) maliciousTestCase.get(i); // grab the textbox from the malicious TestCase
                    badBox.setText(payload); // infect it
                    System.out.println("Updated case: " + i);
                    maliciousTestCase.display();
                    System.out.println("\n");
                    
                    // add test case to injected cases list
                    injectionVariations.add(maliciousTestCase);
                }
            }
            
        }
        List<String> CMDPayloads = AttackInjector.getPayloads("CMD");
        for (String payload : CMDPayloads){

            for (int i = 3; i < originalTestCase.size(); i++) {
                TestAction action = originalTestCase.get(i);
                
                if (action instanceof EnterText) { // if we can inject this action, needs review
                    
                    // CLONE THE ORIGINAL CASE
                    TestCase maliciousTestCase = originalTestCase.clone();
                    System.out.println("Current Case: " + i);
                    maliciousTestCase.display();
                    System.out.println("\n");
                    
                    // SETUP BADBOX
                    EnterText badBox = (EnterText) maliciousTestCase.get(i); // grab the textbox from the malicious TestCase
                    badBox.setText(payload); // infect it
                    System.out.println("Updated case: " + i);
                    maliciousTestCase.display();
                    System.out.println("\n");
                    
                    // add test case to injected cases list
                    injectionVariations.add(maliciousTestCase);
                }
            }
        }
            return injectionVariations;
        }
        
    public static List<List<TestCase>> generateInjectedCases(List<TestCase> crawlResults) throws IOException {
        List<List<TestCase>> listOfLists = new ArrayList<List<TestCase>>(); // make a new list of lists to assimilate all lists of injected cases

        for (TestCase tc : crawlResults){
            List<TestCase> tcBAD = injectCase(tc);
            listOfLists.add(tcBAD);
        }

        return listOfLists;
    }

    public static void displayAll(List<List<TestCase>> allCases){
        
        for (List<TestCase> tc : allCases){
            for(TestCase isolatedtc : tc){
                isolatedtc.display();
                System.out.println("");
            }
        }

    }

    public static List<String> getPayloads(String payloadType) throws IOException{
        List<String> toReturn = new ArrayList<String>();
        if (payloadType.equals("SQL")){

            toReturn = getRandomLines("payloads\\sqli.txt",20);

        }
        else if(payloadType.equals("CMD")){
            toReturn = getRandomLines("payloads\\cmd.txt",40);

        }
        else if(payloadType.equals("XSS")){
            toReturn = getRandomLines("payloads\\xss.txt",50);

        }
        return toReturn;
    }

    
    public static List<String> getRandomLines(String filePath, int numLines) throws IOException {
        List<String> allLines = Files.readAllLines(Paths.get(filePath));
        int totalLines = allLines.size();

        List<String> selectedLines = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < numLines; i++) {
            int randomLineIndex = random.nextInt(totalLines);
            selectedLines.add(allLines.get(randomLineIndex));
        }

        return selectedLines;
    }



    public static List<List<TestCase>> splitTestCases(List<TestCase> testCaseList, int numOfMachines){
        List<List<TestCase>> splitCases = new ArrayList<List<TestCase>>();

        int indexToFill = 0;
        int maxPerCase = (int)(Math.ceil(testCaseList.size()/numOfMachines));
        int casesInBucket = 0;

        while(splitCases.size()<numOfMachines){
            List<TestCase> toAdd = new ArrayList<TestCase>();
            for(TestCase tc : testCaseList){
                if(casesInBucket>=maxPerCase){
                    indexToFill++;
                    casesInBucket=0;
                    splitCases.add(testCaseList);
                }
                toAdd.add(tc);
                
            }
        }



        return splitCases;


    }



}



// PER TEST CASE (On^2)
// a --> previous element
// b --> injected/malicious element
// General idea: we want to check every case of an element being a or b
//  a a a a a
//  b a a a a
//  a b a a a
//  a a b a a
//  a a a b a
//  a a a a b
//  b b a a a
//  b a b a a
//  b a a b a
//  .......
//  b b b b b 

