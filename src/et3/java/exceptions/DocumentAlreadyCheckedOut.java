package et3.java.exceptions;

public class DocumentAlreadyCheckedOut extends DocumentBorrowingException {

    /**
     * Creates a new instance of <code>DocumentAlreadyCheckedOut</code> without detail
     * message.
     */
    public DocumentAlreadyCheckedOut() {
    }

    /**
     * Constructs an instance of <code>DocumentAlreadyCheckedOut</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public DocumentAlreadyCheckedOut(String msg) {
        super(msg);
    }
}