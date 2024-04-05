package backend;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import backend.results.TestCase;
import backend.seleniumActions.EnterText;

public class Test {
    @SuppressWarnings("unchecked")
	public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "chromedriver-mac-x64/chromedriver");

        // WebDriver driver = MyWebDriver.getDriver();
        // driver.get("http://localhost:8080/WebGoat/login");
        // System.out.println("Page title is: " + driver.getTitle());
        // System.out.println("Page URL is: " + driver.getCurrentUrl());

        // Create first TestAction
        EnterText username = new EnterText("exampleInputEmail1", "matteo");

        // Create second TestAction
        EnterText password = new EnterText("exampleInputPassword1", "abc123");

        // Serialization
        TestCase object = new TestCase();
        object.append(username);
        object.append(password);
        object.display();
        List<TestCase> listOfTestCases = new ArrayList<TestCase>();
        listOfTestCases.add(object);
        String filename = "testCase.ser";

        try {
            // Saving of TestCase object into a file
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(file);

            // Method for serialization of object
            out.writeObject(listOfTestCases);

            // close file handlers
            out.close();
            file.close();

            System.out.println("TestCase object has been serialized");
        }
        catch(IOException ex) {
            System.out.printf("IOException '%s' is caught\n", ex);
        }

        // Deserialization
        List<TestCase> object2 = null;

        try {
            // Reading the serialized object from a file
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of the serialized TestCase object
            object2 = (ArrayList<TestCase>) in.readObject();
            for (TestCase tc : object2){
                tc.display();
            }

            // close file handlers
            in.close();
            file.close();

            // System.out.println("TestCase object has been deserialied");
        }
        catch(IOException ex) {
            System.out.printf("IOException '%s' is caught\n", ex);
        }
        catch(ClassNotFoundException ex) {
            System.out.printf("ClassNotFoundException '%s' is caught\n", ex);
        }
    }
}
