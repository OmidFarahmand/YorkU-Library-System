package model;

import java.util.ArrayList;
import java.util.Collection;

public class LibraryManagementTeam {

    public int ID;
    public int accessCode;
    public ItemManagement itemManagement;
    public Collection<Request> bookRequests;
    public ArrayList<Item> items = new ArrayList<>();
    private static boolean objectCreated = false;

    public static LibraryManagementTeam createLibraryManagementTeam() {
        return new LibraryManagementTeam();
    }

    public void disableAnItem(Item item) {
    	item.canBePurchased = false;
    }

    public void enableAnItem(Item item) {
    	item.canBePurchased = true;
    }

    public void addAnItem(Item item) {
    	items.add(item);
    }

    public Item selectAnItemFromDatabase(int ID) {
    	if(ID < items.size()) {
    		return items.get(ID);
    	}
        return null;
    }

}
