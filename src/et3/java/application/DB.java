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

    public void addDocument(Document doc) {
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

    public void listLibraries() {
//        for (TypeKey name: example.keySet()){
//            String key = name.toString();
//            String value = example.get(name).toString();  
//            System.out.println(key + " " + value);  
//        }
        
        this.libs.entrySet().forEach(entry -> {
            System.out.println(entry.getValue());  
         });
    }

    public void listDocuments() {
        for (Document doc : this.docs) {
            System.out.println(doc);
        }
    }
    
    /*
        addUser()..
    */

    /**
     * Return the corresponding Series or a new Series if not found in db.
     * @param seriesTitle
     * @return 
     */
    public Series getSeries(String seriesTitle) {
        /* TODO : search in list + add in seriesList */
        
        Series existingSeries = null;  // = seriesList.search(seriesTitle);
        
        if (existingSeries == null) {
            existingSeries = new Series(seriesTitle);
            // add it to seriesList
        }
        
        return existingSeries;
    }

    /**
     * Return the corresponding Author or a new Author if not found in db.
     * @param authorName
     * @param authorSurname
     * @return 
     */
    public Author getAuthor(String authorName, String authorSurname) {
        /* TODO : One field can be empty at most */
        /* TODO : search in list */
        
        Author existingAuthor = null;  // = authList.search(authorName, authorSurname);
        
        if (existingAuthor == null) {
            existingAuthor = new Author(authorName, authorSurname);
            authList.put(existingAuthor.getId(), existingAuthor);
        }
        
        return existingAuthor;
    }
}