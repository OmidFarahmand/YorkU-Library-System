package model;

import java.util.ArrayList;

public class BuySpecialItems {

    public Payment paymentAPI;
    public ArrayList<Item> purchasedItems = new ArrayList<>();

    public boolean buyAnItem(Item item, double price) {
    	if(item.canBePurchased) {
    		paymentAPI.pay(price);
    		purchasedItems.add(item);
    		return true;
    	}else {
    		return false;
    	}
    }

}
