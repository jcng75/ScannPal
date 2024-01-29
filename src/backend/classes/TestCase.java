package backend.classes;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import java.io.Serializable;

public class TestCase implements Serializable {
    private List<TestAction> testCases;

    public TestCase() {
        this.testCases = new ArrayList<TestAction>();
    }

    public TestCase(List<TestAction> actions) {
        this.testCases = actions;
    }

    public List<TestAction> getTestCase() {
        return this.testCases;
    }

    // Needs working on, must return list of resulting test cases
    public List<TestCase> append(TestAction action, HashSet<String> hashSet) {
        HeuristicsCheck hc = new HeuristicsCheck();
        // Modify this later on
        if (!hc.heuristicsCheck(null, null, hashSet)){
            this.testCases.add(action);
            return new ArrayList<TestCase>();
        }
        return new ArrayList<TestCase>();
    }

    public void display() {
        System.out.println(testCases);
    }

}
