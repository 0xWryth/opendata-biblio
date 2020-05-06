package et3.java.model;

/**
 * BoardGame is a specifaction of Game class because it represents a particular
 * type of game (a role-playing game or party game).
 */
public class BoardGame extends Game {
    
    public BoardGame(String title, String EAN, String date, String publisher) {
        super(title, EAN, date, publisher);
    }
    
}