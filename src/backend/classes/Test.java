package backend.classes;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Test {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "chromedriver-mac-x64/chromedriver");

        WebDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/WebGoat/login");
        System.out.println("Page title is: " + driver.getTitle());
        System.out.println("Page URL is: " + driver.getCurrentUrl());

        // Create first TestAction
        EnterText username = new EnterText(driver, "exampleInputEmail1", "matteo");

        // Create second TestAction
        EnterText password = new EnterText(driver, "exampleInputPassword1", "abc123");

        // Serialization
        TestCase object = new TestCase();
        object.append(username);
        object.append(password);
        object.display();
        String filename = "testCase.ser";

        try {
            // Saving of TestCase object into a file
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(file);

            // Method for serialization of object
            out.writeObject(object);

            // close file handlers
            out.close();
            file.close();

            System.out.println("TestCase object has been serialized");
        }
        catch(IOException ex) {
            System.out.println("IOException is caught");
        }

        // Deserialization
        TestCase object2 = null;

        try {
            // Reading the serialized object from a file
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of the serialized TestCase object
            object2 = (TestCase)in.readObject();

            // close file handlers
            in.close();
            file.close();

            System.out.println("TestCase object has been deserialied");
            object2.display();
        }
        catch(IOException ex) {
            System.out.println("IOException is caught");
        }
        catch(ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException is caught");
        }
    }
}
