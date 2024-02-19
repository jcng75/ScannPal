package backend.classes;

import java.util.List;

public class ResultAnalysis {
    
    public static void analyzeResults(List<TestResult> testResults){

    }

    private static boolean xssCheck(String HTML, String payload){
        System.out.println("Comparing HTML page to payload: "); 
        StringSimilarity.printSimilarity(HTML, payload);
        return HTML.contains(payload);
    }

    public static void analyzeResult(TestResult baseResult, TestResult injectedResult){
        String baseHTML = baseResult.getHtmlResult();
        String injectedHTML = injectedResult.getHtmlResult();
        double similarity = StringSimilarity.similarity(baseHTML, injectedHTML);
        System.out.println(String.format("Comparing Case %s to %s", baseResult.getPhotoName(), injectedResult.getPhotoName()));
        xssCheck(injectedResult.getHtmlResult(), injectedResult.getInjectTestCase().getPayload());
        System.out.println(similarity);
        System.out.println("\n");


    }
}
