package model;

import java.util.Iterator;

public abstract class  OnlineResources {
	private SearchVirtualItem searchVirtualItem = new SearchVirtualItem(Database.database);;
    public Client client;

    public OnlineResources(Client client) {
        this.client = client;
    }

    public Newsletter searchSubscribedNewsletter(String input) {
    	
        return searchVirtualItem.getUniNewsletter(input);
    }

    public String readVirtualItem(VirtualItem item) {
        return null;
    }

    public Iterator<VirtualItem> iterator() {
        return null;
    }

}
