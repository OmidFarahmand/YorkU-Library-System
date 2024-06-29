package model;

import java.util.ArrayList;

public abstract class Client {


    public String username;
    private String password;
    public String email;
    public NewsletterSubscriptionManager newsletterManager;
    public BorrowingManager borrowManager;
    public ArrayList<VirtualBook> virtualBooksAvailable;

    public Client(){



    }

    public void viewMyOnlineResource() {
    }

    public boolean allowedToBorrow() {
        return borrowManager.canRent();
    }

}
