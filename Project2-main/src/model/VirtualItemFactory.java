package model;

import java.util.Calendar;

public class VirtualItemFactory {

    public static VirtualItem makeVirtualItem(String[] metadata, boolean isBook) {

        

        if(isBook){

            int id = Integer.parseInt(metadata[0]);
            String title = metadata[1];
            String location = metadata[2];
            boolean canBePurchased = Boolean.parseBoolean(metadata[3]);
            int copiesAvailable = Integer.parseInt(metadata[4]);
            String publisher = metadata[5];
            String author = metadata[6];
            int date = Integer.parseInt(metadata[7]);
            String isbn = metadata[8];
            boolean isBookLost = Boolean.parseBoolean(metadata[9]);
            int edition = Integer.parseInt(metadata[10]);
            String content = metadata[11];

            VirtualBook book = new VirtualBook(title);
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
            book.content = new VirtualItemContent(content);
            return book;

        }else{


            int id = Integer.parseInt(metadata[0]);
            String title = metadata[1];
            String type = metadata[2];
            String content = metadata[3];
            float cost = Float.parseFloat(metadata[4]);

            if(type.equalsIgnoreCase("Uniprovided")){
                UniProvidedNewsletter newsletter = new UniProvidedNewsletter();
                newsletter.title = title;
                newsletter.content = new VirtualItemContent(content);
                newsletter.ID = id;
                newsletter.cost = cost;
                
                return newsletter;
        
            }else if (type.equalsIgnoreCase("Paid")){
                PaidNewsletter newsletter = new PaidNewsletter();
                newsletter.title = title;
                newsletter.content = new VirtualItemContent(content);
                newsletter.ID = id;
                newsletter.cost = cost;
                
                return newsletter;
            

            }






        }

        return null;


    }

}
