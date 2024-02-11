package backend.classes;
import java.util.ArrayList;
import java.util.List;

public class attackInjector {

    public static List<TestCase> injectCase(TestCase originalTestCase) {

        List<TestCase> injectionVariations = new ArrayList<>(); // make new arraylist to return with variations of injection

        injectionVariations.add(originalTestCase.clone()); // index 0 of each list of injected cases is the clean copy of the injected case
        HeuristicsCheck hc = new HeuristicsCheck(); // hc object
        Boolean needStop = hc.canExtend(originalTestCase); // to test if ends in URL

        if (needStop) { // if it ends in a URL, we don't want to inject this
            return injectionVariations; // returns empty list
        }
        
        // If we can continue peacefully:
        
        // ' --
        // " --
        // " OR 1=1--
        
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
                badBox.setText("INFECTED!"); // infect it
                // System.out.println("Updated case: " + i);
                // maliciousTestCase.display();
                // System.out.println("\n");

                // add test case to injected cases list
                maliciousTestCase.setInjected(true);
                injectionVariations.add(maliciousTestCase);
            }
        }

        return injectionVariations;
    }

    public static List<List<TestCase>> generateInjectedCases(List<TestCase> crawlResults) {
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
