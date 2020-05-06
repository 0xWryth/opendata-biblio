package et3.java.model;

/**
 * VinylDisc is a specifaction of Disc class because it represents a particular
 * type of disc (a vinyl record).
 */
public class VinylDisc extends Disc {
    
    public VinylDisc(String title, String EAN, String date, String publisher) {
        super(title, EAN, date, publisher);
    }
    
}