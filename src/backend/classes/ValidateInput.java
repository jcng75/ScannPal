package backend.classes;

public class ValidateInput {
   
    // This parameter is the status of the inputted stuff
    boolean isValid;
    
    public ValidateInput(){
        this.isValid = true;
    }

    private boolean validateUrl(String url){
        // Checks url for 200 response
        // if url  fails, set valid to false
        System.out.println(String.format("A 200 response cannot be generated from url %s", url));
        return false;
        
    }

    private boolean validateLogin(String url){
        // Checks if login page has fourm
        // if page has login fourm -> return true
        // else set valid to false and return false
        System.out.println(String.format("A login fourm cannot be found from url %s", url));
        return false;
    }

    private boolean validateCredentials(String username, String password){
        // Checks if login credentials work
        // if work -> return true
        // else set valid to false and return false
        System.out.println("Provided credentials are not valid.");
        return false;
    }

    public boolean isValid(String url, String username, String password){
        // Execute all methods from class to check if sufficient input is valid
        // If any of the inputs fail the tests, return false
        // Otherwise return true when all pass
        if (!validateUrl(url)){
            return false;
        } 
        if (!validateLogin(url)) {
            return false;
        }
        if (!validateCredentials(username, password)) 
        {
            return false;
        }
        return isValid;
    }






}
