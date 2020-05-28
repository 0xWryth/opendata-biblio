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

    /**
     * {@inheritDoc}
     * @param name      string corresponding to the name given to the User.
     * @param surname   string corresponding to the surname given to the User.
     */
    public User(String name, String surname) {
        super(name, surname);
        this.id = User.nextId++;
        this.registredLib = new HashMap<>();
    }
    
    /**
     * Add a Document to the User's associated library account.
     *
     * @param doc the Document instance to add.
     * @param lib the Library where the Document come from.
     * @throws et3.java.exceptions.UnregisteredUser
     * @throws et3.java.exceptions.DocumentQuotaReached
     */
    public void borrowDocument(Document doc, Library lib) throws DocumentBorrowingException {
        LibraryAccount account = this.registredLib.get(lib.getId());
        
        if (account == null){
            throw new UnregisteredUser("Emprunt impossible : l'utilisateur"
                    + " n'est pas inscrit dans la bibliothèque " + lib.getName() + ".");
        }

        account.addBorrowedDocument(doc);
    }
    
    /**
     * Removes a Document from the user's associated library account.
     *
     * @param doc the Document instance to remove.
     * @throws et3.java.exceptions.NoDocumentFound
     */
    public void returnDocument(Document doc) throws NoDocumentFound {
        boolean isRemoved = false;
        
        for(Map.Entry<Integer, LibraryAccount> entry : this.registredLib.entrySet()) {
            if (isRemoved = entry.getValue().removeBorrowedDocument(doc)) {
                isRemoved = true;
                break;
            }
        }
        
        if (!isRemoved) {
            throw new NoDocumentFound("Impossible de rendre ce document. Il n'a pas été emprunté par l'utilisateur.");
        }
    }
    
    /**
     * Registers the current User to the specified Library.
     *
     * @param lib the library which the User should be registered.
     * @param quota an integer corresponding to the number of Documents the
     *              User is allowed to borrow.
     */
    public void subscribe(Library lib, int quota) {
        this.registredLib.put(lib.getId(), new LibraryAccount(quota));
    }
    
    /**
     * Unsubscribes the current User from the specified Library.
     *
     * @param lib the library which the User should be unregistered.
     */
    public void unsubscribe(Library lib) {
        this.registredLib.remove(lib.getId());
    }

    public int getId() {
        return this.id;
    }
    
}