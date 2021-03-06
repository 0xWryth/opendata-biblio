package et3.java.exceptions;

/**
 * Its subclasses are thrown to indicate that the application's user
 * has attempted an adding action but something went wrong.
 * 
 * @author Antonin
 */
public abstract class DocumentAddingException extends Exception {

    /**
     * Creates a new instance of <code>DocumentAddingException</code> without detail
     * message.
     */
    public DocumentAddingException() {
    }

    /**
     * Constructs an instance of <code>DocumentAddingException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public DocumentAddingException(String msg) {
        super(msg);
    }
}