package et3.java.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Library is a class that represents a real library that contains documents
 * and subscribed users.
 */
public class Library {
    private static int nextId = 1;
    private final int id;
    private String name;
    private HashMap<Document, Integer> documents;
    private ArrayList<User> registredUsers;

    public Library(String name) {
        this.id = Library.nextId++;
        this.name = name;
        this.documents = new HashMap<>();
        this.registredUsers = new ArrayList<>();
    }
    
    public void addDoc(Document doc, int numberOfDoc) {
        // TODO : check if it already exists before adding (just add +1 in "quantity")
        this.documents.put(doc, numberOfDoc);
    }
    
    /**
     *
     * @param user
     */
    public void registerUser(User user) {
        // TODO : check if it already exists before adding
        this.registredUsers.add(user);
    }
    
    /**
     * 
     * @param lib
     */
    public void exchangeDocument(Library lib) {
        // TODO - implement Library.exchangeDocument
        throw new UnsupportedOperationException();
    }
    
    /**
     * Displays a view of the corresponding document(s).
     */
    public void listDocuments() {
        // TODO - implement Library.listDocuments
        throw new UnsupportedOperationException();
    }
    
    /**
     * Displays a view of the corresponding document(s).
     * @param date
     */
    public void listByDate(String date) {
        // TODO - implement Library.listByDate
        throw new UnsupportedOperationException();
    }
    
    /**
     * Displays a view of the corresponding document(s).
     * @param author
     */
    public void listByAuthor(Author author) {
        // TODO - implement Library.listByAuthor
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return "Library "+ id + " : " + name + '}';
    }

    public int getId() { return id; }
}