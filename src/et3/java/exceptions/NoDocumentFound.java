package et3.java.exceptions;

/**
 * Thrown to indicate that no document was found during a research, a borrow,
 * a return or a transfer.
 * 
 * @author Antonin
 */
public class NoDocumentFound extends Exception {

    /**
     * Creates a new instance of <code>NoDocumentFound</code> without detail
     * message.
     */
    public NoDocumentFound() {
    }

    /**
     * Constructs an instance of <code>NoDocumentFound</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public NoDocumentFound(String msg) {
        super(msg);
    }
}