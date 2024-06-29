package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchBorrowedItem implements SearchEngine{

    Database db;
    SearchItems SI;
    SearchVirtualItem SVI;
    public static SearchBorrowedItem  object = null;

    public static SearchBorrowedItem makeSearchBorrowedItem(){
        if(object == null){
            object =  new SearchBorrowedItem(Database.database);
        }
        return object;
    }

    private SearchBorrowedItem(Database db) {
        this.db = db;
        SI = new SearchItems(db);
        SVI = new SearchVirtualItem(db);
    }


    public ArrayList<RentedItem> searchBorrowedItem(String type, String email){
        
        ArrayList<RentedItem> rentedItem = new ArrayList<>();
        RentedItem item = null;

        try (BufferedReader br = new BufferedReader(new FileReader(db.getBorrowedItemFilePath()))) {
            String line = br.readLine();

            while ((line = br.readLine()) != null) {
                String[] metadata = line.split(","); 
                if(metadata.length ==0){
                    break;
                }
                boolean a = type.equalsIgnoreCase(metadata[0]);
                boolean b = email.equalsIgnoreCase(metadata[2]);

                if(a && b){
                    

                    if(type.equalsIgnoreCase("Book")){
                        item = new RentedItem((Book) SI.getBook(metadata[1]), metadata[3]);

                    }else if(type.equalsIgnoreCase("Magazine")){
                        item = new RentedItem((Magazine) SI.getMagazine(metadata[1]), metadata[3]);
                        
                    }else if(type.equalsIgnoreCase("CD")){
                        item = new RentedItem((CD) SI.getCD(metadata[1]), metadata[3]);
                    }

                    rentedItem.add(item);
                }
                
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rentedItem;

    }

    public ArrayList<VirtualItem> searchBorrowedVirtualItem(String type, String email){
        
        ArrayList<VirtualItem> items = new ArrayList<>();
        VirtualItem item;

        try (BufferedReader br = new BufferedReader(new FileReader(db.getBorrowedItemFilePath()))) {
            String line = br.readLine();

            while ((line = br.readLine()) != null) {
                String[] metadata = line.split(","); 
                if(metadata.length ==0){
                    break;
                }
                
                //System.out.println(metadata);
                boolean a = type.equalsIgnoreCase(metadata[0]);
                boolean b = email.equalsIgnoreCase(metadata[2]);

                if( a && b){
                    

                    if(type.equalsIgnoreCase("VBook")){
                        item = (VirtualBook) SVI.getVirtualBook(metadata[1]);
                        
                        

                    }else if(type.equalsIgnoreCase("Pnews")){
                        item = (PaidNewsletter) SVI.getPaidNewsletter(metadata[1]);
                      
                        
                    }else if(type.equalsIgnoreCase("UNews")){

                        item = (UniProvidedNewsletter) SVI.getUniNewsletter(metadata[1]);
                        
                    }else{
                        throw new Exception("One of the Readings Failed.");
                    }

                    items.add(item);
                    
                }
                
                
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return items;

    }




    public void addPhyscialItem(Item item, String date, Client client){

        String type = "";
        if(item instanceof Book){
            type = "Book";
        }else if(item instanceof CD){
            type = "CD";
        }else if(item instanceof Magazine){
            type = "Magazine";
        }

        String newRow = String.format("%s,%s,%s,%s\n", type, item.title, client.email, date); 

        try (FileWriter fw = new FileWriter(db.getBorrowedItemFilePath(), true)) {
            fw.append(newRow); 
        } catch (Exception e) {
            e.printStackTrace();
        }



    }


    public void addVirtualItem(VirtualItem item, String date, Client client){

        

        String type = "";
        String title = "";
        if(item instanceof VirtualBook){
            type = "Vbook";
            title = ((VirtualBook)item).title;
        }else if(item instanceof UniProvidedNewsletter){
            type = "Unews";
            title = ((UniProvidedNewsletter)item).title;
        }else{
            type = "Pnews";
            title = ((PaidNewsletter)item).title;
        }
        
        
        //ArrayList<VirtualItem> vi = searchBorrowedVirtualItem(type,client.email);
        
        
        
        
        String newRow = String.format("%s,%s,%s,%s\n", type, title, client.email, date); 

        try (FileWriter fw = new FileWriter(db.getBorrowedItemFilePath(), true)) {
            fw.append(newRow); 
        } catch (Exception e) {
            e.printStackTrace();
        }



    }


    public void removeItem(String title, String email){
        String filePath = db.getBorrowedItemFilePath();
        try {
            // Read all lines from the file
            List<String> lines = Files.readAllLines(Paths.get(filePath));

            // Filter out the row with the given title in column 2 and email in column 3
            List<String> updatedLines = lines.stream()
                    .filter(line -> {
                        String[] columns = line.split(","); // Assumes CSV uses commas as separator
                        // Check if the row matches the criteria to keep it
                        return !(columns.length > 2 && columns[1].trim().equals(title) && columns[2].trim().equals(email));
                    })
                    .collect(Collectors.toList());

            // Write the filtered content back to the same file
            FileWriter writer = new FileWriter(filePath, false); // false to overwrite
            for (String line : updatedLines) {
                writer.write(line + System.lineSeparator());
            }
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }



    }

}
