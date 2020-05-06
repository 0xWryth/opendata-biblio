package et3.java.model;

/**
 * Comic is a specifaction of Book class because it represents a particular
 * type of book (a strip cartoon).
 */
public class Comic extends Book {
    
    public Comic(String title, String EAN, String date, String publisher, String isbn) {
        super(title, EAN, date, publisher, isbn);
    }
    
}