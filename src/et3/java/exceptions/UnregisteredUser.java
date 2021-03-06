package et3.java.exceptions;

/**
 * Thrown to indicate that a User isn't registred in a selected Library.
 * 
 * @author Antonin
 */
public class UnregisteredUser extends DocumentBorrowingException {

    /**
     * Creates a new instance of <code>UnregisteredUser</code> without detail
     * message.
     */
    public UnregisteredUser() {
    }

    /**
     * Constructs an instance of <code>UnregisteredUser</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public UnregisteredUser(String msg) {
        super(msg);
    }
}