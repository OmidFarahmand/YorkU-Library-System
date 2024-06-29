package model;

public class ItemManagement {

    public Database database;

    public void disableAnItem(Item item) {
    	item.canBePurchased = false;
    }

    public void enableAnItem(Item item) {
    	item.canBePurchased = true;
    }

    public void addAnItem(Item item) {
    }

    public Item selectAnItemFromDatabase(int ID) {
        return null;
    }

}
