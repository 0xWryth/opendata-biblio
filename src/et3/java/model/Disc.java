package et3.java.model;

/**
 * Disc is the abstract base class for documents subclasses related with
 * disc supports like DVD, CD, etc...
 */
public abstract class Disc extends Document {
    
    /**
     * {@inheritDoc}
     * @param title
     * @param EAN
     * @param date
     * @param publisher
     */
    public Disc(String title, String EAN, String date, String publisher) {
        super(title, EAN, date, publisher);
    }
    
}