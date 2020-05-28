package et3.java.model;

import java.util.ArrayList;

/**
 * The Author class represents a document's author.
 */
public class Author extends Person {
    private static int nextId = 1;
    private final int id;
    private ArrayList<Document> writtenDocs;

    /**
     * {@inheritDoc}
     * @param name      string corresponding to the name given to the Author.
     * @param surname   string corresponding to the surname given to the Author.
     */
    public Author(String name, String surname) {
        super(name, surname);
        this.id = Author.nextId++;
        this.writtenDocs = new ArrayList<>();
    }
    
    public int getId() { return id; }

    public void addWrittenDoc(Document newDoc) {
        if (!writtenDocs.contains(newDoc)) {
            writtenDocs.add(newDoc);
        }
    }
}