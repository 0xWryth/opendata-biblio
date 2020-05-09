package et3.java.model;

/**
 * Comic is a specifaction of Book class because it represents a particular
 * type of book (a strip cartoon).
 */
public class Comic extends Book {
    
    public Comic(String title, String EAN, String isbn, String date, String publisher) {
        super(title, EAN, isbn, date, publisher);
    }
    
}