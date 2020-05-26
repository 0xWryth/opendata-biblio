package et3.java.application;

import et3.java.model.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Network class represents all the objects known in the system. The user
 * interacts in the main() with it each time he wants to operate (adding,
 * consulting, removing) on a document, library or that kind of objects.
 * A Network object encapsulates collections of libraries, documents, authors,
 * series and users.
 */
public class Network {
    private HashMap<Integer, Library> libs;
    private ArrayList<Document> docs;
    private HashMap<Integer, Author> authList;
    private HashMap<Integer, Series> seriesList;
    private HashMap<Integer, User> users;


    public Network() {
        this.libs = new HashMap<>();
        this.docs = new ArrayList<>();
        this.authList = new HashMap<>();
        this.seriesList = new HashMap<>();
        this.users = new HashMap<>();
    }

    public void addDocument(Document doc) {
        try {
            // We got different case
            // Case 1: doc is not a Book
            //      => If 2 EAN are the same => skip
            // Case 2: doc is Book
            //      Case 2.a: ISBN is empty
            //          => If 2 EAN are the same => skip
            //      Case 2.b: ISBN is not empty
            //          => If 2 ISBN are the same => skip

            Document skip = this.docs.stream()
                .filter(d -> (
                        (d instanceof Book && doc instanceof Book &&
                            ((Book) doc).getISBN().length() != 0 &&
                            ((Book) doc).getISBN().equals(((Book) d).getISBN())) || (
                                    d.getEAN().equals(doc.getEAN())
                                )
                ))
                .findFirst()
                .orElseThrow(Exception::new);
            System.err.println("Le document " + doc.toString() + " n'a pas été ajouté car il existe déjà.");
        } catch (Exception e) {
            // If an exception is thrown, then no duplicate doc has been found
            this.docs.add(doc);
        }
    }

    public void addAuthor(Author auth) {
        this.authList.putIfAbsent(auth.getId(), auth);
    }

    public void addSerie(Series series) {
        this.seriesList.putIfAbsent(series.getId(), series);
    }

    public HashMap<Integer, Library> getLibs() {
        return libs;
    }

    // Change parameter to String to do the new Library() inside the method ??
    
    public void addLibrary(Library lib) {
        // Two libs with same names but different id can be added..
        Library existingLib = this.libs.putIfAbsent(lib.getId(), lib);
        
        if (existingLib != null) {
            System.err.println("La " + lib.toString() + " n'a pas été ajoutée car elle existe déjà.");
        } else {
            System.out.println("La librairie " + lib.getName() + " a été ajoutée avec succès !");
        }
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
     * Return the corresponding Series or a new Series if not found in network.
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
     * Return the corresponding Author or a new Author if not found in network.
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

    /**
     *
     * @param user
     * @param libId
     */
    public void addUser(User user, int libId) {
        Library lib = this.libs.get(libId);
        
        if (lib != null) {
            User existingUser = this.users.putIfAbsent(user.getId(), user);
        
            if (existingUser != null) {
                System.err.println("L'utilisateur " + user.toString() + " n'a pas été ajoutée car il semble déjà exister dans le réseau.");
                return;
            }
            
            lib.registerUser(user);
            System.out.println("L'utilisateur " + user.toString() + " a bien été créé et inscrit à la librairie " + lib.getName());
        } else {
            // Add the unknown library on the fly ??
            System.err.println("La bibliothèque associée ne fait pas partie du réseau.");
        }
    }

    public ArrayList<Document> getDocs() {
        return docs;
    }

    public HashMap<Integer, Author> getAuthList() {
        return authList;
    }

    public HashMap<Integer, User> getUsers() {
        return users;
    }
}