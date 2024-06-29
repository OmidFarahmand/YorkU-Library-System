package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SearchVirtualItem implements SearchEngine {
    private Database db;


    
    public SearchVirtualItem(Database db) {
        this.db = db;
    }



    public VirtualItemSearchResult searchVirtualItem(String input) {

        return new VirtualItemSearchResult(getVirtualBook(input), getUniNewsletter(input), getPaidNewsletter(input));
    }



    public VirtualBook getVirtualBook(String title){

        VirtualBook book = null;
        try (BufferedReader br = new BufferedReader(new FileReader(db.getVirtualBookFilePath()))) {
            String line = br.readLine();

            while ((line = br.readLine()) != null) {
                String[] metadata = line.split(","); 

                if(title.equalsIgnoreCase(metadata[1])){
                    book = (VirtualBook) VirtualItemFactory.makeVirtualItem(metadata, true);
                    break;
                }
                
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return book;

    }


    public UniProvidedNewsletter getUniNewsletter(String title){


        UniProvidedNewsletter newsletter = null;
        try (BufferedReader br = new BufferedReader(new FileReader(db.getNewspaperFilePath()))) {
            String line = br.readLine();

            while ((line = br.readLine()) != null) {
                String[] metadata = line.split(","); 

                if(metadata[2].equalsIgnoreCase("Paid")){
                    continue;
                }

                if(title.equalsIgnoreCase(metadata[1])){
                    newsletter = (UniProvidedNewsletter) VirtualItemFactory.makeVirtualItem(metadata, false);
                    break;
                }
                
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newsletter;


    }

    public PaidNewsletter getPaidNewsletter(String title){

        PaidNewsletter newsletter = null;
        try (BufferedReader br = new BufferedReader(new FileReader(db.getNewspaperFilePath()))) {
            String line = br.readLine();

            while ((line = br.readLine()) != null) {
                String[] metadata = line.split(","); 
                if(metadata[2].equalsIgnoreCase("UniProvided")){
                    continue;
                }
                if(title.equalsIgnoreCase(metadata[1])){
                    newsletter = (PaidNewsletter) VirtualItemFactory.makeVirtualItem(metadata, false);
                    break;
                }
                
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newsletter;

        
    }


}
