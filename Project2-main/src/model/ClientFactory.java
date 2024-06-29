package model;
import java.util.ArrayList;

public abstract class  ClientFactory {

    public static Client makeClient(String[] metadata) {
      
     

        Client client = null;
        String type = metadata[3];
        SearchBorrowedItem searchBorrowedItem = SearchBorrowedItem.makeSearchBorrowedItem();


        ArrayList<VirtualItem> virtualItemsBorrowed =  searchBorrowedItem.searchBorrowedVirtualItem("VBook", metadata[1]);
        ArrayList<VirtualItem> uNewsBorrowed =  searchBorrowedItem.searchBorrowedVirtualItem("Unews", metadata[1]);
        ArrayList<VirtualItem> pNewsBorrowed =  searchBorrowedItem.searchBorrowedVirtualItem("Pnews", metadata[1]);

        ArrayList<Newsletter> news = new ArrayList<>();
        for (VirtualItem newsletter : uNewsBorrowed) {
            news.add((Newsletter) newsletter);
        }
        for (VirtualItem newsletter : pNewsBorrowed) {
            news.add((Newsletter) newsletter);
        }

        NewsletterSubscriptionManager newsletterManager = new NewsletterSubscriptionManager(news);


        // Physcial Items
        
        ArrayList<RentedItem> booksBorrowed =  searchBorrowedItem.searchBorrowedItem("Book", metadata[1]);
        ArrayList<RentedItem> CDBorrowed =  searchBorrowedItem.searchBorrowedItem("CD", metadata[1]);
        ArrayList<RentedItem> magazineBorrowed =  searchBorrowedItem.searchBorrowedItem("Magazine", metadata[1]);


        ArrayList<RentedItem> rentedItems = new ArrayList<>();
        for (RentedItem item : booksBorrowed) {
            rentedItems.add(item);
        }
        for (RentedItem item : CDBorrowed) {
            rentedItems.add(item);
        }
        for (RentedItem item : magazineBorrowed) {
            rentedItems.add(item);
        }

        BorrowingManager borrowingManager = new BorrowingManager(rentedItems);


        if(type.equalsIgnoreCase("Student")){
            client = new Student();

            client.email = metadata[1];
            client.newsletterManager = newsletterManager;
            client.borrowManager = borrowingManager;
            
            
            



        }else if(type.equalsIgnoreCase("Faculty") || type.equalsIgnoreCase("Faculty Member")){
            client = new Faculty();

            client.email = metadata[1];
            client.newsletterManager = newsletterManager;
            client.borrowManager = borrowingManager;
            


        }else if(type.equalsIgnoreCase("NonFaculty") || type.equalsIgnoreCase("Non-Faculty Staff")){
            client = new NonFaculty();

            client.email = metadata[1];
            client.newsletterManager = newsletterManager;
            client.borrowManager = borrowingManager;


        }else if(type.equalsIgnoreCase("Visitor")){

            client = new Visitor();

            client.email = metadata[1];
            client.newsletterManager = newsletterManager;
            client.borrowManager = borrowingManager;


        }
        ArrayList<VirtualBook> temp = new ArrayList<>();
        for (VirtualItem vIB : virtualItemsBorrowed) {
            temp.add((VirtualBook)vIB);
        }
        
    client.virtualBooksAvailable = temp;
        

        client.borrowManager.client = client;
        client.newsletterManager.client = client;



        return client;
    }




}
