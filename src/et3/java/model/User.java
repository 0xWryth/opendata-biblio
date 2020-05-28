package et3.java.model;

import et3.java.exceptions.*;
import java.util.*;

/**
 * The User class represents an user of the libraries. He can subscribe /
 * unsubscribe himself to a library in order to borrow documents.
 */
public class User extends Person {
    private static int nextId = 1;
    private final int id;
    private HashMap<Integer, LibraryAccount> registredLib;     // first int : lib id

    public User(String name, String surname) {
        super(name, surname);
        this.id = User.nextId++;
        this.registredLib = new HashMap<>();
    }
    
    /**
     *
     * @param doc
     * @param lib
     * @throws et3.java.exceptions.UnregisteredUser
     * @throws et3.java.exceptions.DocumentQuotaReached
     */
    public void borrowDocument(Document doc, Library lib) throws DocumentBorrowingException {
        LibraryAccount account = this.registredLib.get(lib.getId());
        
        if (account == null){
            throw new UnregisteredUser();
        }

        account.addBorrowedDocument(doc);
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
     * @param quota
     */
    public void subscribe(Library lib, int quota) {
        this.registredLib.put(lib.getId(), new LibraryAccount(quota));
    }
    
    /**
     *
     * @param lib
     */
    public void unsubscribe(Library lib) {
        this.registredLib.remove(lib.getId());
    }

    public int getId() {
        return this.id;
    }
    
}