package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Login {
    
    public Database db;
    private static Login login = null;

    public static Login makeLogin(Database db){
        if(login == null){
            login = new Login(db);
            return login;
        }
        return login;

    }
    public Login(Database database) {
        this.db = database;
    }

    public Client login(String email, String password) throws LoginFailedException{
        Client client = null;
        String pass = ""; 
        try (BufferedReader br = new BufferedReader(new FileReader(db.getClientFilePath()))) {
            String line = br.readLine();

            while ((line = br.readLine()) != null) {
                String[] metadata = line.split(","); 

                if(email.equalsIgnoreCase(metadata[1])){
                    client = (Client) ClientFactory.makeClient(metadata);
                    pass = metadata[2];
                    break;
                }
                
                
            }
        } catch (IOException e) {
        }


        if(client == null || !password.equals(pass)){
            throw new LoginFailedException("Login Failed.");
        }

        return client;

    }


}
