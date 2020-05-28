package et3.java.model;

/**
 * Book inherits from Document class and represents a book identified by
 * an ISBN.
 */
public class Book extends Document {
    
    private String ISBN;

    public Book(String title, String EAN, String isbn, String date, String publisher) {
        // TODO : manage books with ISBN but no EAN
        super(title, EAN, date, publisher);
        this.ISBN = isbn;
    }

    public String getISBN() {
        return ISBN;
    }
    
    @Override
    public String toString() {
        return super.toString() + ", ISBN=" + ISBN;
    }
}