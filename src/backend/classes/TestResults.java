package backend.classes;

public class TestResults {
   
    String htmlResult;
    String photoName;
    TestCase baseCase;

    public TestResults(String htmlResult, String photoName, TestCase baseCase){
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
