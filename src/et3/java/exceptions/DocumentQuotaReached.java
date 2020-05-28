package et3.java.exceptions;

/**
 * Thrown to indicate that a User can't borrow another Document from a Library
 * because it already reached it allocated quota.
 * 
 * @author Antonin
 */
public class DocumentQuotaReached extends DocumentBorrowingException {

    /**
     * Creates a new instance of <code>DocumentQuotaReached</code> without detail
     * message.
     */
    public DocumentQuotaReached() {
    }

    /**
     * Constructs an instance of <code>DocumentQuotaReached</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public DocumentQuotaReached(String msg) {
        super(msg);
    }
}