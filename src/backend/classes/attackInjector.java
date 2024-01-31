package backend.classes;
import java.util.ArrayList;
import java.util.Arrays;

import org.openqa.selenium.WebElement;

import net.bytebuddy.asm.Advice.Enter;


public class attackInjector {

    public static void main(String[] args) {
        // Example: Create a test case (replace this with your actual test case)

        String testString="this is a test";
        
        EnterText a = new EnterText("username" , testString);
        EnterText b = new EnterText("password", testString);
        ClickButton c = new ClickButton("button");
        EnterText d = new EnterText("expDate", testString);
        ClickButton e = new ClickButton("button2");

        // Click on login button
        ClickButton loginButton = new ClickButton("Login");
        loginButton.execute();


        TestAction[] originalTestCase = {a, b, c, d, e};

        


        // Generate malicious test cases
        ArrayList<TestAction[]> maliciousTestCases = malify(originalTestCase);

        // Print the original and malicious test cases
        System.out.println("Original Test Case: " + Arrays.toString(originalTestCase));
        System.out.println("Malicious Test Cases:");
        for (TestAction[] testCase : maliciousTestCases) {
            System.out.println(Arrays.toString(testCase));
        }
    }
    

    private EnterText inject(EnterText textBox) {
    // Modify the WebElement by setting its value to a dangerous string
        textBox.setText("username' OR 1=1 --"); 
        return textBox;
    }

    private TestAction inject(TestAction action) {
        System.out.println("TestAction Response");
        return action;
    }

    private ClickButton inject(ClickButton button) {
    // Modify the WebElement by setting its value to a dangerous string
        System.out.println("Cannot inject into button");
        return button;
    }

    // Attack injector
    public static ArrayList<TestAction[]> malify(TestAction[] originalTestCase) {
        System.out.println("This is the attack injector");
        
        ArrayList<TestAction[]> maliciousTestCases = new ArrayList<>();

        for (int i = 0; i < originalTestCase.length; i++) {
            for (int j = i; j < originalTestCase.length; j++) {
                // Create a copy of the original test case
                
                TestAction[] maliciousTestCase = Arrays.copyOf(originalTestCase, originalTestCase.length);

                // Inject at the current positions
                // THIS IS THE INJECT SEQUENCE
                for (int k = i; k <= j; k++) {
                    maliciousTestCase[k]= inject(maliciousTestCase[k]);
                }
                System.out.println();

                // Add the malicious test case to the list
                maliciousTestCases.add(maliciousTestCase);
            }
        }

        return maliciousTestCases;

            
    
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
