package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
public class BorrowingManager {

    public static int maxBorrowCount = 10;
    public int itemsBorrowedCount;
    public ArrayList<RentedItem> physicalItemBorrowed;
    public Client client;
    public float amountOwned = 0;

    public BorrowingManager(ArrayList<RentedItem> physicalItemBorrowed){
        this.physicalItemBorrowed =physicalItemBorrowed;
        LocalDate now = LocalDate.now();


        for (RentedItem rentedItem : physicalItemBorrowed) {
            if(rentedItem.dueDateD.isBefore(now)){
                long daysPassed = ChronoUnit.DAYS.between(rentedItem.dueDateD, now);
                amountOwned += daysPassed*0.5;

            }
            
        }

    }

    public boolean canRent() {


        int dueItemCounts = 0;
        LocalDate currentDate = LocalDate.now();
        
        
        for (RentedItem rentedItem : physicalItemBorrowed) {
            if(rentedItem.dueDateD.isBefore(currentDate)){
                dueItemCounts++;
            }
            
        }

        if(dueItemCounts > 3){
            return false;
        }
        
        
        return true;
    }

    public void updateBorrowingPrivilages() {
    }

    public void rentAnItem(Item item, LocalDate date)  throws RentingNotAllowedException{
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yy");
        rentAnItem(item, date.toString());
    }

    
    public void rentAnItem(Item item, String date)  throws RentingNotAllowedException{
        

        for (RentedItem ri : physicalItemBorrowed) {
            if (ri.item.equals(item)){
                throw new RentingNotAllowedException("Item Has Already Been Rented.");
            }
        }

        if(!item.canBePurchased){
            throw new RentingNotAllowedException("Item Not Rentable.");
        }

        if(!canRent()){
            throw new RentingNotAllowedException("Renting Not Allowed. More than 3 items overdue.");
        }

        if(physicalItemBorrowed.size() == maxBorrowCount){
            throw new RentingNotAllowedException("Renting Not Allowed. Maximum items have been rented.");
        }

        
        RentedItem ri = new RentedItem(item, date);
        physicalItemBorrowed.add(ri);
        SearchBorrowedItem bi = SearchBorrowedItem.makeSearchBorrowedItem();
        bi.addPhyscialItem(item, date, client);
        maxBorrowCount--;

        
        


    }

    public void removeRentedItem(Item item) {
        RentedItem itemToRemove = null;
        for (RentedItem ri : physicalItemBorrowed) {
            if (ri.item.equals(item)){
                itemToRemove = ri;
                break;
            }
        }

        if(itemToRemove != null){
            physicalItemBorrowed.remove(itemToRemove);
            SearchBorrowedItem bi = SearchBorrowedItem.makeSearchBorrowedItem();
            bi.removeItem(item.title, client.email);
            maxBorrowCount++;
        }


    }

    public void removeRentedItem(RentedItem item) {
        this.removeRentedItem(item.item);
    }

    public double checkBalanceOwed() {
        return amountOwned;
    }

}
