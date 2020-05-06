package et3.java.model;

/**
 * Other class is a specification of the Document class used to represent
 * documents that doesn't matches with an existing subclass.
 */
public class Other extends Document {
    
    public Other(String title, String EAN, String date, String publisher) {
        super(title, EAN, date, publisher);
    }
    
}