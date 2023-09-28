package backend.classes;

import java.util.ArrayList;

public class main {
    public static void main(String args[]){
        ClickButton cb = new ClickButton();
        cb.execute(null);
        ArrayList<TestAction> l = new ArrayList<TestAction>();
        l.add(cb);
        for (TestAction testAction : l) {
            testAction.execute(null);
        }
    }
}
