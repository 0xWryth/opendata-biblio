package et3.java.model;

import java.util.*;

/**
 * The User class represents an user of the libraries. He can subscribe /
 * unsubscribe himself to a library in order to borrow documents.
 */
public class User extends Person {
    private static int nextId = 1;
    private final int id;
    private HashMap<Integer, Integer> registredLib;     // first int : lib id, second : quota allowed

    public User(String name, String surname) {
        super(name, surname);
        this.id = User.nextId++;
    }
    
    /**
     *
     * @param d
     * @param l
     */
    public void borrowDocument(Document d, Library l) {
        // TODO - implement User.borrowDocument
        throw new UnsupportedOperationException();
    }
    
    /**
     *
     * @param d
     * @param l
     */
    public void returnDocument(et3.java.model.Document d, et3.java.model.Library l) {
        // TODO - implement User.returnDocument
        throw new UnsupportedOperationException();
    }
    
    /**
     *
     * @param lib
     */
    public void subscribe(Library lib) {
        // TODO - implement User.register
        throw new UnsupportedOperationException();
    }
    
    /**
     *
     * @param lib
     */
    public void unsubscribe(Library lib) {
        // TODO - implement User.unsubscribe
        throw new UnsupportedOperationException();
    }
    
}