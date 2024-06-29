package model;

import java.util.ArrayList;

public class NewsletterSubscriptionManager {

    public ArrayList<Newsletter> newsletterSubscribed;
    public Client client;
    public Payment paymentAPI;

    public NewsletterSubscriptionManager(ArrayList<Newsletter> newsletterSubscribed) {
        this.newsletterSubscribed = newsletterSubscribed;
    }

    public void subscribeToUniProvidedNewsletter(UniProvidedNewsletter newsletter, String date) {
    	if(client != null) {
    		
    		
    	
        newsletterSubscribed.add(newsletter);
        SearchBorrowedItem bi = SearchBorrowedItem.makeSearchBorrowedItem();
        bi.addVirtualItem(newsletter, date, client);

    }
    }

    public void subscribeToPaidNewsletter(PaidNewsletter newsletter, String date) {
    	if(client != null) {
        newsletterSubscribed.add(newsletter);
        SearchBorrowedItem bi = SearchBorrowedItem.makeSearchBorrowedItem();
        bi.addVirtualItem(newsletter, date, client);
    	}
    }

    public void cancelSubscription(Newsletter newsletter) {
    	if(client != null) {
        SearchBorrowedItem bi = SearchBorrowedItem.makeSearchBorrowedItem();
        bi.removeItem(newsletter.title, client.email);
        
        newsletterSubscribed.remove(newsletter);
    	}
    }

}
