package model;
import java.util.Date;
public class Book extends Printable {

	public String ISBN;
	public boolean isBookLost;
	public int edition;

	public Book(String title){
		super(title);

	}
	
}
