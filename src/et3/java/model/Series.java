package et3.java.model;

import java.util.ArrayList;

/**
 * Series class represent a series of documents and is identified by an id.
 */
public class Series {
    private static int nextId = 1;
    private final int id;
    private String title;
    private ArrayList<Document> documents;
    
    public Series(String title) {
        this.id = Series.nextId++;
        this.title = title;
        this.documents = new ArrayList<>();
    }
    
    public int getId() { return id; }
}