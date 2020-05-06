package et3.java.model;

/**
 * Book inherits from Document class and represents a book identified by
 * an ISBN.
 */
public class Book extends Document {
    
    private String ISBN;

    public Book(String title, String EAN, String date, String publisher, String isbn) {
        // TODO : manage books with ISBN but no EAN
        super(title, EAN, date, publisher);
        this.ISBN = isbn;
    }
    
}