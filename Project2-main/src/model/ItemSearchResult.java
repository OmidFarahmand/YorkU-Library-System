package model;

import java.util.Collection;

public class ItemSearchResult {
    public Book book;
    public CD cd;
    public Magazine magazine;

    public Collection<Book> similarBooks;
    public Collection<CD> similarCD;
    public Collection<Magazine> similarMagazine;


    
    public ItemSearchResult(Book book, CD cd, Magazine magazine, Collection<Book> similarBooks,
            Collection<CD> similarCD, Collection<Magazine> similarMagazine) {
        this.book = book;
        this.cd = cd;
        this.magazine = magazine;
        this.similarBooks = similarBooks;
        this.similarCD = similarCD;
        this.similarMagazine = similarMagazine;
    }

    

}
