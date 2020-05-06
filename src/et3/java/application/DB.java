package et3.java.application;

import et3.java.model.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * DB class represents all the objects known in the system. The user
 * interacts in the main() with it each time he wants to operate (adding,
 * consulting, removing) on a document, library or that kind of objects.
 * A DB object encapsulates collections of libraries, documents, authors,
 * series and users.
 */
public class DB {
    private HashMap<Integer, Library> libs;
    private ArrayList<Document> docs;
    private HashMap<Integer, Author> authList;
    private HashMap<Integer, Series> seriesList;
    private HashMap<Integer, User> users;


    public DB() {
        this.libs = new HashMap<>();
        this.docs = new ArrayList<>();
        this.authList = new HashMap<>();
        this.seriesList = new HashMap<>();
        this.users = new HashMap<>();
    }

    public void addDocuments(Document doc) {
        this.docs.add(doc);
    }

    public void addAuthor(Author auth) {
        this.authList.putIfAbsent(auth.getId(), auth);
    }

    public void addSerie(Series series) {
        this.seriesList.putIfAbsent(series.getId(), series);
    }

    public void addLibrary(Library lib) {
        this.libs.putIfAbsent(lib.getId(), lib);
    }
    
    /*
        addUser()..
    */
}