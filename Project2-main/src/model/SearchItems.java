package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SearchItems implements SearchEngine {

    private Database db;

    public SearchItems(Database db){
        this.db = db;

    }


    public ItemSearchResult searchItem(String input) {

        Book book = getBook(input);
        CD cd= getCD(input);
        Magazine magazine = getMagazine(input);
       

        Collection<Book> similarBooks = getSimilarBooks(input);
        Collection<CD> similarCD = getSimilarCD(input);
        Collection<Magazine> similarMagazine= getSimilarMagazine(input);
        
        return new ItemSearchResult(book, cd, magazine, similarBooks, similarCD, similarMagazine);
    }


    public Book getBook(String title) {
        
        Book book = null;
        try (BufferedReader br = new BufferedReader(new FileReader(db.getBookFilePath()))) {
            String line = br.readLine();

            while ((line = br.readLine()) != null) {
                String[] metadata = line.split(","); 

                if(title.equalsIgnoreCase(metadata[1])){
                    book = (Book) ItemFactory.makeItem("Book", metadata);
                    break;
                }
                
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return book;
        
        
        
    }


    public Magazine getMagazine(String title){

        Magazine Magazine = null;
        try (BufferedReader br = new BufferedReader(new FileReader(db.getMagazineFilePath()))) {
            String line = br.readLine();

            while ((line = br.readLine()) != null) {
                String[] metadata = line.split(","); 

                if(title.equalsIgnoreCase(metadata[1])){
                    Magazine = (Magazine) ItemFactory.makeItem("Magazine", metadata);
                    break;
                }
                
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Magazine;


    }
    
    public CD getCD(String title){

        CD Cd = null;
        try (BufferedReader br = new BufferedReader(new FileReader(db.getCDFilePath()))) {
            String line = br.readLine();

            while ((line = br.readLine()) != null) {
                String[] metadata = line.split(","); 

                if(title.equalsIgnoreCase(metadata[1])){
                    Cd = (CD) ItemFactory.makeItem("CD", metadata);
                    break;
                }
                
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Cd;

    }



    public Collection<Book> getSimilarBooks(String input) {



        Collection<Book> books = new ArrayList<>();
        Collection<String> bookTitles = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(db.getBookFilePath()))) {
            String line = br.readLine();

            while ((line = br.readLine()) != null) {
                String[] metadata = line.split(","); 
                String title = metadata[1];
                bookTitles.add(title);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> similarBookTitles = SearchStrategy.findMostSimilarStrings(bookTitles, input);
        for (String title : similarBookTitles) {
            books.add(getBook(title));
        }

        return books;


    }
    private Collection<CD> getSimilarCD(String input) {
        return null;
    }
    private Collection<Magazine> getSimilarMagazine(String input) {
        return null;
    }
}
