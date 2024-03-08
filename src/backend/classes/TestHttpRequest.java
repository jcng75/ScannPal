package backend.classes;

import java.net.HttpURLConnection;
import java.net.URL;

public class TestHttpRequest {
    

    public static int getRequest(String url){
        int responseCode = -1;
        try {
            URL urlTest = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlTest.openConnection();
            connection.setRequestMethod("GET");
            responseCode = connection.getResponseCode();
            return responseCode;

        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
        return responseCode;
   }
}
