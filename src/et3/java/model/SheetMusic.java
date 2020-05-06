package et3.java.model;

/**
 * SheetMusic class is a specification of the Document class that represents
 * documents corresponding to a tablature / a music score.
 */
public class SheetMusic extends Document {
    
    public SheetMusic(String title, String EAN, String date, String publisher) {
        super(title, EAN, date, publisher);
    }
    
}