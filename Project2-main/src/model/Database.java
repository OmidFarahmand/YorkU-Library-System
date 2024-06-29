package model;
import java.util.Calendar;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Database {
	public String bookFilePath = "src/Book.csv";
    public String newspaperFilePath= "src/Newsletter.csv";
    public String CDFilePath = "src/CD.csv";
    public String magazineFilePath = "src/Magazine.csv";
    public String virtualBookFilePath= "src/VirtualBook.csv";
    public String clientFilePath= "src/Client.csv";

    public String borrowedItemFilePath = "src/BorrowedItems.csv";


    public String itemPath;
    public String virtualItemFilePath;
    public static Database database = null;

    public static Database createDatabase(String clientFilePath, String bookFilePath, String virtualBookFilePath, String CDFilePat, String magazineFilePath, String newspaperFilePath, String borrowedItemFilePath) {
        if(database == null){
            database = new Database( clientFilePath,  bookFilePath,  virtualBookFilePath,  CDFilePat,  magazineFilePath,  newspaperFilePath, borrowedItemFilePath); 
        }
        
        return database;
        
    }
    public static Database createDatabase(String clientFilePath, String borrowedItemFilePath) {
    	if(database == null){
            database = new Database( clientFilePath,  "",  "",  "",  "",  "", borrowedItemFilePath); 
        }
    	database.clientFilePath = clientFilePath ;
    	database.borrowedItemFilePath= borrowedItemFilePath;
        return database;
        
    	
    }
    
    public static Database createDatabase() {
    	if(database == null){
            database = new Database(); 
        }
        return database;
        
    	
    }
    
    
    public Database() {
    	
    }

    private Database(String clientFilePath, String bookFilePath, String virtualBookFilePath, String CDFilePath, String magazineFilePath, String newspaperFilePath, String borrowedItemFilePath) {
//    	if(clientFilePath != null) {
//    		
//    		
//    	
//	        
//	        this.bookFilePath= bookFilePath;
//	        this.virtualBookFilePath= virtualBookFilePath;
//	        this.CDFilePath= CDFilePath;
//	        this.magazineFilePath = magazineFilePath;
//	        this.newspaperFilePath = newspaperFilePath;
//	        
//    	}
    }




    public  ArrayList<String> getAllItems(){
        ArrayList<String> output = new ArrayList<String>();

        try (BufferedReader br = new BufferedReader(new FileReader(bookFilePath))) {
            String line = br.readLine();

            while ((line = br.readLine()) != null) {
                String[] metadata = line.split(","); 

                String title = metadata[1];
                String author = metadata[3];
                output.add("Book: " + title + " -- Availability: " + author);
                
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(CDFilePath))) {
            String line = br.readLine();

            while ((line = br.readLine()) != null) {
                String[] metadata = line.split(","); 

                String title = metadata[1];
                String author = metadata[3];
                output.add("CD: " + title + " -- Availability: " + author);
                
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(magazineFilePath))) {
            String line = br.readLine();

            while ((line = br.readLine()) != null) {
                String[] metadata = line.split(","); 

                String title = metadata[1];
                String author = metadata[3];
                output.add("Magazine: " + title + " -- Availability: " + author);
                
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }




        return output;
    }



    





    public Client getClient(String username) {
        return new Student(); // TO BE CHANGED
    }

    public void addClient(Client client) {
    }

    


    public int getRandomID(){
        Random rand = new Random();
        int min = 1000000; 
        int max = 9900000;
        return rand.nextInt((max - min) + 1) + min;
    }
    public boolean addItem(String title){
        return true;
    }



    public String getClientFilePath() {
        return clientFilePath;
    }


    
    public String getBookFilePath() {
        return bookFilePath;
    }


    
    public String getVirtualBookFilePath() {
        return virtualBookFilePath;
    }


    
    public String getCDFilePath() {
        return CDFilePath;
    }


    
    public String getMagazineFilePath() {
        return magazineFilePath;
    }


    


    public String getNewspaperFilePath() {
        return newspaperFilePath;
    }



    public String getBorrowedItemFilePath() {
        return borrowedItemFilePath;
    }


}
