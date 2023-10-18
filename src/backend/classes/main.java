package backend.classes;

import java.util.ArrayList;

public class Main {
    public static void main(String args[]) {
        ArrayList<TestAction> l = new ArrayList<TestAction>();
        l.add(new ClickButton());
        l.add(new EnterText("testing"));
        l.add(new SelectInputBox());
        l.add(new TakeScreenshot());
        l.add(new VisitUrl("www.hobs.com"));
        for (TestAction testAction : l) {
            testAction.execute();
        }
    }
}
