package backend.classes;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

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
