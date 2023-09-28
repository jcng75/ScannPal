package backend.classes;

public class EnterText extends TestAction{

   String text;

   EnterText(String text){
      setText(text);
   }

   String getText(){
      return this.text;
   }

   void setText(String text){
      this.text = text;
   }

   public void execute(){
      System.out.println(String.format("This enters text %s", getText()));
      return;
   } 
}
