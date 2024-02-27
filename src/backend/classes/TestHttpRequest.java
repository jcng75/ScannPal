package backend.classes;

import java.net.HttpURLConnection;
import java.net.URL;

public class TestHttpRequest {
    
    String url;

    public TestHttpRequest(String url){
        setURL(url);
    }

    private void setURL(String url){
        this.url = url;
    }

    public int getRequest(){
        int responseCode = 0;
        try {
            URL url = new URL(this.url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
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
