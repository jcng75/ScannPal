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

    public void getRequest(){
        try {
            URL url = new URL(this.url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
        
   } 
}
