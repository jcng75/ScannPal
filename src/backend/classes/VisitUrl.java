package backend.classes;

public class VisitUrl extends TestAction<String> {
   void execute(String url){
      System.out.println(String.format("This visits url %s", url));
      return;
   } 
}
