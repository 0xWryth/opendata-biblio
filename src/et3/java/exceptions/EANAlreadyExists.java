package et3.java.exceptions;

public class EANAlreadyExists extends DocumentAddingException {

    /**
     * Creates a new instance of <code>EANAlreadyExists</code> without detail
     * message.
     */
    public EANAlreadyExists() {
    }

    /**
     * Constructs an instance of <code>EANAlreadyExists</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public EANAlreadyExists(String msg) {
        super(msg);
    }
}