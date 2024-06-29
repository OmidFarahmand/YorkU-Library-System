package model;

import java.util.Calendar;

public abstract class Printable extends Item {
	
    public String author;
	public Calendar date;
	public Printable(String title) {
        super(title);
    }
}
