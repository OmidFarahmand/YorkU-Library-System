package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class RentedItem {

    public Item item;
    public String borrowDate;
    public String dueDate;
    public LocalDate dueDateD;
    


    public RentedItem(Item item, String borrowDate) {
        this.item = item;
        this.borrowDate = borrowDate;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withLocale(Locale.ENGLISH);
        LocalDate date = LocalDate.parse(borrowDate, formatter).plus(1, ChronoUnit.MONTHS);
        this.dueDate = date.format(formatter);
        this.dueDateD = date;

    }

    public RentedItem(Item item, LocalDate borrowDate) {
        this.item = item;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withLocale(Locale.ENGLISH);;
        this.borrowDate = borrowDate.format(formatter);

        
        LocalDate date = borrowDate.plus(1, ChronoUnit.MONTHS);
        this.dueDate = date.format(formatter);
        this.dueDateD = date;

    }

    
}
