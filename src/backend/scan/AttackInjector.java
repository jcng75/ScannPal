
package backend.scan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

import backend.aws.EC2Client;
import backend.results.TestCase;
import backend.seleniumActions.EnterText;
import backend.seleniumActions.TestAction;
import backend.utility.HeuristicsCheck;

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
                    // System.out.println("Current Case: " + i);
                    // maliciousTestCase.display();
                    // System.out.println("\n");
                    
                    // SETUP BADBOX
                    EnterText badBox = (EnterText) maliciousTestCase.get(i); // grab the textbox from the malicious TestCase
                    badBox.setText(payload); // infect it
                    // System.out.println("Updated case: " + i);
                    // maliciousTestCase.display();
                    // System.out.println("\n");
                    maliciousTestCase.setInjected(true);
                    maliciousTestCase.setAttackType("SQL");
                    maliciousTestCase.setPayload(payload); 
                    
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
                    maliciousTestCase.setInjected(true);
                    maliciousTestCase.setAttackType("XSS");
                    maliciousTestCase.setPayload(payload); 
                    
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
                    maliciousTestCase.setInjected(true);
                    maliciousTestCase.setAttackType("CMD");
                    maliciousTestCase.setPayload(payload); 
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
            String sqliFile = "payloads" + File.separator + "sqli.txt";
            toReturn = getRandomLines(sqliFile, 3);

        }
        else if(payloadType.equals("CMD")){
            String cmdFile = "payloads" + File.separator + "cmd.txt";
            toReturn = getRandomLines(cmdFile, 3);

        }
        else if(payloadType.equals("XSS")){
            String xssFile = "payloads" + File.separator + "xss.txt";
            toReturn = getRandomLines(xssFile, 3);

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

    public static HashMap<String, List<List<TestCase>>> splitTestCases(List<List<TestCase>> testCases){
        
        HashMap<String, List<List<TestCase>>> splitCases = new HashMap<String, List<List<TestCase>>>();
        EC2Client client = new EC2Client();
        List<String> machines = client.getActiveInstances();
        int numOfMachines = machines.size();
        
        if (numOfMachines == 0){
            System.out.println("(!) There are no worker nodes running!");
            return null;
        }

        int maxPerCase = (int)(Math.ceil(testCases.size() / numOfMachines));
        int testCaseIndex = 0;

        for (int i = 0; i < numOfMachines; i++){

            List<List<TestCase>> toAdd = new ArrayList<List<TestCase>>();
            for (int j = 0; j < maxPerCase; j++){
                toAdd.add(testCases.get(testCaseIndex));
                testCaseIndex++;
            }

            splitCases.put(machines.get(i), toAdd);
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

