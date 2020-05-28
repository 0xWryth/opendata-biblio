package et3.java.model;

import et3.java.exceptions.DocumentQuotaReached;
import java.util.ArrayList;

/**
 * LibraryAccount is the class that represents the account of a User for a
 * given library. It contains its currently borrowed Documents and their
 * maximum number.
 */
public class LibraryAccount {
    
    public final int quota;
    private ArrayList<Document> docs;

    /**
     * Constructs an empty <tt>LibraryAccount</tt> with the specified quota.
     * 
     * @param quota
     */
    public LibraryAccount(int quota) {
        this.quota = quota;
        this.docs = new ArrayList<>(quota);
    }

    /**
     * Appends a document to the borrowed Document collection if quota isn't
     * reached.
     * 
     * @param doc the Document instance to add.
     * @throws DocumentQuotaReached
     */
    public void addBorrowedDocument(Document doc) throws DocumentQuotaReached {
        if (this.docs.size() == this.quota){
            throw new DocumentQuotaReached("Impossible d'emprunter le document " + doc.getTitle()
                    + ", l'utilisateur a déjà atteint son quota dans cette bibliothèque");
        }
        
        this.docs.add(doc);
    }

    /**
     * Deletes the specified document from the borrowed Document collection.
     * 
     * @param doc the Document instance to remove.
     * @return <tt>true</tt> if this the borrowed Document collection
     *         contained the specified element
     */
    public boolean removeBorrowedDocument(Document doc) {
        return this.docs.remove(doc);
    }
}