package model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register {

    public Database database;
    private static Register reg = null;
    SearchClient sc;

    public static Register makeRegister(Database database){
        if(reg == null){
            reg = new Register(database);
            return reg;
        }
        return reg;


    }

    private Register(Database database) {
        this.database = database;
        sc = SearchClient.makeSearchClient(database);
    }

    public boolean register(String email, String password, String type) throws RegistrationFailedException{

        if(sc.getClient(email) != null){
            throw new RegistrationFailedException("Email Address is Already Registered.");
        }

        if(!verifyEmail(email) ){
            throw new RegistrationFailedException("Email Address Invalid.");
        }

        if(!verifyPassword(password) ){
            throw new RegistrationFailedException("Password Too Week.");
        }

        if(type.equalsIgnoreCase("Staff")){
            // TODO: IDK WHAT TO DO HERE...
        }
        
        sc.addClient(email, password, type);
        return true;
        
    }

    public boolean registerAsStaff(Staff client, String email, String password) {
        return true;
    }

    private boolean verifyEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                            "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        // Compile the regex into a pattern
        Pattern pattern = Pattern.compile(emailRegex);

        // If the email address is empty, return false
        if (email == null) {
            return false;
        }

        // Create a matcher for the input email
        Matcher matcher = pattern.matcher(email);

        // Return whether the email matches the regex
        return matcher.matches();
    }

    private boolean verifyPassword(String password) {
        // Regex to check the password criteria
        String regex = "^(?=.*[0-9])" +           // at least one digit
                       "(?=.*[a-z])" +            // at least one lower case letter
                       "(?=.*[A-Z])" +            // at least one upper case letter
                       "(?=\\S+$).{5,}$";         // no whitespace, at least 5 characters

        // Compile the regex
        Pattern p = Pattern.compile(regex);

        // If the password is empty, return false
        if (password == null) {
            return false;
        }

        // Matcher for the regex
        return p.matcher(password).matches();
    }

}
