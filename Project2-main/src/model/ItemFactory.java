package model;

import java.util.Calendar;

public class ItemFactory {

    private ItemFactory(){}


    
    public static Item makeItem(String type, String[] metadata) {
        Item item = null;
        if(type.equalsIgnoreCase("CD")){
            int id = Integer.parseInt(metadata[0]);
            String til = metadata[1];
            String location = metadata[2];
            boolean canBePurchased = Boolean.parseBoolean(metadata[3]);
            int copiesAvailable = Integer.parseInt(metadata[4]);
            String publisher = metadata[5];

            CD Cd = new CD(til);
            Cd.ID = id;
            Cd.location = location;
            Cd.canBePurchased = canBePurchased;
            Cd.copiesAvailable = copiesAvailable;
            Cd.publisher = publisher;
            
            return Cd;

        }else if(type.equalsIgnoreCase("Book")){
            

            int id = Integer.parseInt(metadata[0]);
            String til = metadata[1];
            String location = metadata[2];
            boolean canBePurchased = Boolean.parseBoolean(metadata[3]);
            int copiesAvailable = Integer.parseInt(metadata[4]);
            String publisher = metadata[5];
            String author = metadata[6];
            int date = Integer.parseInt(metadata[7]);
            String isbn = metadata[8];
            boolean isBookLost = Boolean.parseBoolean(metadata[9]);
            int edition = Integer.parseInt(metadata[10]);

            Book book = new Book(til);
            book.ID = id;
            book.location = location;
            book.canBePurchased = canBePurchased;
            book.copiesAvailable = copiesAvailable;
            book.publisher = publisher;
            book.author = author;
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, date);
            book.date = calendar;
            
            book.ISBN = isbn;
            book.isBookLost = isBookLost;
            book.edition = edition;


            return book;


        }else if(type.equalsIgnoreCase("Magazine")){

            int id = Integer.parseInt(metadata[0]);
            String til = metadata[1];
            String location = metadata[2];
            boolean canBePurchased = Boolean.parseBoolean(metadata[3]);
            int copiesAvailable = Integer.parseInt(metadata[4]);
            String publisher = metadata[5];
            String author = metadata[6];
            int date = Integer.parseInt(metadata[7]);

            Magazine Magazine = new Magazine(til);
            Magazine.ID = id;
            Magazine.location = location;
            Magazine.canBePurchased = canBePurchased;
            Magazine.copiesAvailable = copiesAvailable;
            Magazine.publisher = publisher;
            Magazine.author = author;
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, date);
            Magazine.date = calendar;
            return Magazine;
        }

       return item;
    
    
    }



    
    
}
