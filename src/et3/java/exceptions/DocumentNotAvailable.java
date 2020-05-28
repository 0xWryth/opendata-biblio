package et3.java.exceptions;

/**
 * Thrown to indicate that a Document isn't available from the selected Library.
 * 
 * @author Antonin
 */
public class DocumentNotAvailable extends DocumentBorrowingException {

    /**
     * Creates a new instance of <code>DocumentNotAvailable</code> without detail
     * message.
     */
    public DocumentNotAvailable() {
    }

    /**
     * Constructs an instance of <code>DocumentNotAvailable</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public DocumentNotAvailable(String msg) {
        super(msg);
    }
}