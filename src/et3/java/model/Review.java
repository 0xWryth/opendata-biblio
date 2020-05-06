package et3.java.model;

/**
 * Review class is a specification of the Document class used to represent
 * documents corresponding to a report or a revision.
 */
public class Review extends Document {
    
    public Review(String title, String EAN, String date, String publisher) {
        super(title, EAN, date, publisher);
    }
    
}