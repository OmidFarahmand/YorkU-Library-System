package model;

public class RentAndReturnSystem {

    public Payment paymentAPI;
    private boolean objectCreated;

    public static RentAndReturnSystem createRARSystem() {
        return null;
    }

    public boolean rentAnItem(Item item, Client client) {
        return true;
    }

    public boolean returnAnItem(Item item, Client client) {
        return true;
    }
}
