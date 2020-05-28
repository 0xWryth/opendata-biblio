package et3.java.model;

/**
 * Game is the abstract base class for document subclasses related with
 * playing universe like video games, board games, etc...
 */
public abstract class Game extends Document {
    
    /**
     * {@inheritDoc}
     * @param title
     * @param EAN
     * @param date
     * @param publisher
     */
    public Game(String title, String EAN, String date, String publisher) {
        super(title, EAN, date, publisher);
    }
    
}