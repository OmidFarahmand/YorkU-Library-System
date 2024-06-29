package model;
import java.util.Random;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SearchClient implements SearchEngine{

    Database db;
    static SearchClient  searchClient = null;

    public static SearchClient makeSearchClient(Database db){
        if(searchClient == null){
            searchClient = new SearchClient(db);
            return searchClient;
        }
        return searchClient;

    }

    private SearchClient(Database db) {
        this.db = db;
    }

    public Client getClient(String email){
        Client client = null;
        try (BufferedReader br = new BufferedReader(new FileReader(db.getClientFilePath()))) {
            String line = br.readLine();

            while ((line = br.readLine()) != null) {
                String[] metadata = line.split(","); 

                if(email.equalsIgnoreCase(metadata[1])){
                    client = (Client) ClientFactory.makeClient(metadata);
                    break;
                }
                
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return client;


    }

    public boolean addClient(String email, String password, String type ){
        

        String newRow = String.format("%d,%s,%s,%s\n", db.getRandomID(), email, password, type); 

        try (FileWriter fw = new FileWriter(db.getClientFilePath(), true)) {
            fw.append(newRow); 
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


        return true;
    }

    
}
