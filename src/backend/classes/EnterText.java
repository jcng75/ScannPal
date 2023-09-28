package backend.classes;

public class EnterText extends TestAction<String>{
   public void execute(String text){
      System.out.println(String.format("This enters text %s", text));
      return;
   } 
}
