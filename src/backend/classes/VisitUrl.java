package backend.classes;

public class VisitUrl extends TestAction {

   String url;

   VisitUrl(String url) {
      setUrl(url);
   }

   String getUrl() {
      return this.url;
   }

   void setUrl(String url) {
      this.url = url;
   }

   void execute(){
      System.out.println(String.format("This visits url %s", getUrl()));
      return;
   } 
}
