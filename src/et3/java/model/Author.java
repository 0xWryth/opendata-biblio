package et3.java.model;

import java.util.ArrayList;

/**
 * The Author class represents a document's author.
 */
public class Author extends Person {
    private static int nextId = 1;
    private final int id;
    private ArrayList<Document> writtenDocs;

    public Author(String name, String surname) {
        super(name, surname);
        this.id = Author.nextId++;
    }
    
    public int getId() { return id; }
}