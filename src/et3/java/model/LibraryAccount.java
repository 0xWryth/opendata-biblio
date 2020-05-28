package et3.java.model;

import et3.java.exceptions.DocumentQuotaReached;
import java.util.ArrayList;

/**
 * LibraryAccount is the class that ...
 */
public class LibraryAccount {
    
    public final int quota;
    private ArrayList<Document> docs;

    public LibraryAccount(int quota) {
        this.quota = quota;
        this.docs = new ArrayList<>();
    }

    /**
     *
     * @param doc
     * @throws DocumentQuotaReached
     */
    public void addBorrowedDocument(Document doc) throws DocumentQuotaReached {
        if (this.docs.size() == this.quota){
            throw new DocumentQuotaReached();
        }
        
        this.docs.add(doc);
    }
}