package et3.java.exceptions;

/**
 * Thrown to indicate that a Book can't have the same ISBN than an existing
 * Book.
 * 
 * @author Antonin
 */
public class ISBNAlreadyExists extends DocumentAddingException {

    /**
     * Creates a new instance of <code>ISBNAlreadyExists</code> without detail
     * message.
     */
    public ISBNAlreadyExists() {
    }

    /**
     * Constructs an instance of <code>ISBNAlreadyExists</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public ISBNAlreadyExists(String msg) {
        super(msg);
    }
}