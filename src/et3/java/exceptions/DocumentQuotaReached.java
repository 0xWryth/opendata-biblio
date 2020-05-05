package et3.java.exceptions;

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