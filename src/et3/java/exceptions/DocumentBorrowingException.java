package et3.java.exceptions;

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