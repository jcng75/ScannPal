package backend.classes;

public class TestResult {
   
    String htmlResult;
    String photoName;
    TestCase baseCase;

    public TestResult(String htmlResult, String photoName, TestCase baseCase) {
        this.htmlResult = htmlResult;
        this.photoName = photoName;
        this.baseCase = baseCase;
    }

    public String getHtmlResult() {
        return this.htmlResult;
    }

    public String getPhotoName() {
        return this.photoName;
    }

    public TestCase getBaseCase() {
        return this.baseCase;
    }

}
