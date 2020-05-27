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
    private final int STANDARD_QUOTA = 5;

    public Library(String name) {
        this.id = Library.nextId++;
        this.name = name;
        this.documents = new HashMap<>();
        this.registredUsers = new ArrayList<>();
    }
    
    /**
     * Add document in the library. If it already exists, update quantity.
     * @param doc
     * @param numberOfDoc 
     */
    public void addDoc(Document doc, int numberOfDoc) {        
        if (this.documents.containsKey(doc)) {
            this.documents.put(doc, numberOfDoc + this.documents.get(doc));
        } else {
            this.documents.put(doc, numberOfDoc);
        }
    }
    
    /**
     *
     * @param user
     */
    public void registerUser(User user) {
        if (!this.registredUsers.contains(user)) {
            this.registredUsers.add(user);
            user.subscribe(this, STANDARD_QUOTA);
        } else {
            System.err.println("Impossible d'inscrire l'utilisateur " + user.getName() + " " + user.getSurname() + " car il semble déjà abonné à cette bibliothèque.");
        }
    }
    
    /**
     *
     * @param user
     */
    public void removeUser(User user) {
        
        /* TODO : return documents before unsubscribe ! */
        
        if (this.registredUsers.remove(user)) {
            user.unsubscribe(this);
        } else {
            System.err.println("Impossible d'annuler l'abonnement car cet utilisateur ne semble pas être abonné à cette bibliothèque.");
        }
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
        return "Library "+ id + " : " + name;
    }

    public int getId() { return id; }

    public String getName() {
        return name;
    }
}