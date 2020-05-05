package et3.java.exceptions;

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