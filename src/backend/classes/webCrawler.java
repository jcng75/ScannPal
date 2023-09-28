package backend.classes;

public class WebCrawler {
    
    String url;
    String username;
    String password;

    public WebCrawler(String url, String username, String password){
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public void setUsername(String user){
        this.username = user;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public boolean crawl(String url, String username, String password){
        // fancy function what does it do?
        ValidateInput validInput = new ValidateInput();
        // If the validations fail, end the crawl instantly
        if(!validInput.isValid(url, username, password)){
            System.out.println("Inputted parameters are not valid, terminating crawl...");
            return false;
        }
        // Otherwise start crawl
        String s = String.format("Crawling for website: %s", url);
        System.out.println(s);
        // run crawler and then store information
        return true;
    }

}