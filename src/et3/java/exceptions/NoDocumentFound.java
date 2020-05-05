package et3.java.exceptions;

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