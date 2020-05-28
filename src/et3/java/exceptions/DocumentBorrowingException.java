package et3.java.exceptions;

/**
 * Its subclasses are thrown to indicate that the application's user
 * has attempted a borrowing action but something went wrong.
 * 
 * @author Antonin
 */
public abstract class DocumentBorrowingException extends Exception {

    /**
     * Creates a new instance of <code>DocumentBorrowingException</code> without detail
     * message.
     */
    public DocumentBorrowingException() {
    }

    /**
     * Constructs an instance of <code>DocumentBorrowingException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public DocumentBorrowingException(String msg) {
        super(msg);
    }
}