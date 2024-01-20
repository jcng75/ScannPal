package backend.classes;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class TestCase implements Serializable {
    private List<TestAction> testCases;

    public TestCase() {
        this.testCases = new ArrayList<TestAction>();
    }

    public List<TestAction> getTestCase() {
        return this.testCases;
    }

    public void append(TestAction action) {
        this.testCases.add(action);
    }

    public void display() {
        System.out.println(testCases);
    }
}
