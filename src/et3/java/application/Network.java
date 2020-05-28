package et3.java.application;

import et3.java.exceptions.*;
import et3.java.model.*;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * Network class represents all the objects known in the system. The user
 * interacts in the main() with it each time he wants to operate (adding,
 * consulting, removing) on a document, library or that kind of objects.
 * A Network object encapsulates collections of libraries, documents, authors,
 * series and users.
 */
public class Network {
    private HashMap<Integer, Library> libs;
    private HashMap<String, Document> docs;
    private HashMap<Integer, Author> authList;
    private HashMap<Integer, Series> seriesList;
    private HashMap<Integer, User> users;


    public Network() {
        this.libs = new HashMap<>();
        this.docs = new HashMap<>();
        this.authList = new HashMap<>();
        this.seriesList = new HashMap<>();
        this.users = new HashMap<>();
    }

    /**
     * Add document in the network if it doesnt already exists.
     * @param doc 
     * @throws et3.java.exceptions.EANAlreadyExists 
     * @throws et3.java.exceptions.ISBNAlreadyExists 
     */
    public void addDocument(Document doc) throws EANAlreadyExists, ISBNAlreadyExists {
        String key = doc.getEAN() + (doc instanceof Book ? ((Book) doc).getISBN() : "");
        
        Document existingDoc = this.docs.putIfAbsent(key, doc);
        
        /* TODO : handle empty EAN or ISBN empty ?? */
        
        // We got different case
        // Case 1: doc is not a Book
        //      => If 2 EAN are the same => skip
        // Case 2: doc is Book
        //      Case 2.a: ISBN is empty
        //          => If 2 EAN are the same => skip
        //      Case 2.b: ISBN is not empty
        //          => If 2 ISBN are the same => skip
        
        
        if (existingDoc != null) {
            if (existingDoc.getEAN().equals(doc.getEAN())) {
                throw new EANAlreadyExists("Le document " + doc.toString() + " n'a pas été ajouté au réseau car son EAN existe déjà.");
            }
            if (doc instanceof Book && doc instanceof Book &&
                ((Book) existingDoc).getISBN().equals(((Book) doc).getISBN())
               ) {
                throw new ISBNAlreadyExists("Le document " + doc.toString() + " n'a pas été ajouté au réseau car son ISBN existe déjà.");
            }
            System.err.println("Le document " + doc.toString() + " n'a pas été ajouté au réseau pour une raison inconnue...");
        } else {
            System.out.println("Le document " + doc.toString() + " a bien été ajouté !");
        }
    }

    public void addAuthor(Author auth) {
        this.authList.putIfAbsent(auth.getId(), auth);
    }

    public void addSerie(Series series) {
        this.seriesList.putIfAbsent(series.getId(), series);
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
    
    /**
     *
     * @param userId
     * @param docKey
     * @param libId
     * @throws et3.java.exceptions.DocumentBorrowingException
     */
    public void registerBorrowing(int userId, String docKey, int libId) throws DocumentBorrowingException {
        User user;
        if ((user = this.users.get(userId)) == null) {
            // TODO : throw new Exception();    // ???
            System.err.println("Impossible d'effectuer l'emprunt : l'utilisateur n'existe pas..");
            return;
        }
        
        Document doc;
        if ((doc = this.docs.get(docKey)) == null) {
            // TODO : throw new Exception();    // ???
            System.err.println("Impossible d'effectuer l'emprunt : le document n'existe pas..");
            return;
        }
        
        Library lib;
        if ((lib = this.libs.get(libId)) == null) {
            // TODO : throw new Exception();    // ???
            System.err.println("Impossible d'effectuer l'emprunt : la bibliothèque n'existe pas..");
            return;
        }
        
        if (!lib.hasDocument(doc)){
            throw new DocumentNotAvailable("Impossible de retirer ce document de la bibliothèque car il n'y est pas.");
        }
        
        user.borrowDocument(doc, lib);
        lib.removeDocument(doc);
        System.out.println("Le document a bien été emprunté à la bibliothèque " + lib.getName() + " par " + user.toString() + ".");
    }

    /**
     *
     * @param userId
     * @param docKey
     * @param libId
     * @throws et3.java.exceptions.UnregisteredUser
     * @throws et3.java.exceptions.NoDocumentFound
     */
    public void recordDocumentReturn(int userId, String docKey, int libId) throws UnregisteredUser, NoDocumentFound {
        User user;
        if ((user = this.users.get(userId)) == null) {
            // TODO : throw new Exception();    // ???
            System.err.println("Impossible de rendre le document : l'utilisateur n'existe pas..");
            return;
        }
        
        Document doc;
        if ((doc = this.docs.get(docKey)) == null) {
            // TODO : throw new Exception();    // ???
            System.err.println("Impossible de rendre le document : ses \"indentifiants\" ne correspondent pas..");
            return;
        }
        
        Library lib;
        if ((lib = this.libs.get(libId)) == null) {
            // TODO : throw new Exception();    // ???
            System.err.println("Impossible de rendre le document : la bibliothèque n'existe pas..");
            return;
        }
        
        if (!lib.hasUser(user)) {
            throw new UnregisteredUser("Impossible de rendre le document : l'utilisateur doit être abonné à la bibliothèque " + lib.getName());
        }
        
        user.returnDocument(doc);
        lib.addDoc(doc, 1);
        System.out.println("Le document a bien été retourné à la bibliothèque " + lib.getName() + " par " + user.toString() + ".");
    }
    
    /**
     *
     * @param libId1
     * @param libId2
     * @param docKey
     * @throws et3.java.exceptions.DocumentNotAvailable
     */
    public void transferDocument(int libId1, int libId2, String docKey) throws DocumentNotAvailable {
        Library lib1;
        if ((lib1 = this.libs.get(libId1)) == null) {
            // TODO : throw new Exception();    // ???
            System.err.println("Impossible de rendre le document : la bibliothèque n'existe pas..");
            return;
        }
        
        Library lib2;
        if ((lib2 = this.libs.get(libId2)) == null) {
            // TODO : throw new Exception();    // ???
            System.err.println("Impossible de rendre le document : la bibliothèque n'existe pas..");
            return;
        }
        
        Document doc;
        if ((doc = this.docs.get(docKey)) == null) {
            // TODO : throw new Exception();    // ???
            System.err.println("Impossible de rendre le document : ses \"indentifiants\" ne correspondent pas..");
            return;
        }
        
        if (!lib1.hasDocument(doc)){
            throw new DocumentNotAvailable("Impossible de retirer ce document de la bibliothèque car il n'y est pas.");
        }
        
        lib1.exchangeDocument(lib2, doc);
        System.out.println("La bibliothèque " + lib1.getName() + " vient de tranférer le document "
                + doc.getTitle() + " à la bibliothèque " + lib2.getName() + ".");
    }
    
    
    /**
     * Return the corresponding Series or a new Series if not found in network.
     * @param seriesTitle
     * @return 
     */
    public Series getSeries(String seriesTitle) {
        Series existingSeries = null;
        
        for(Entry<Integer, Series> entry : this.seriesList.entrySet()) {
            if (entry.getValue().getTitle().equals(seriesTitle)) {
                existingSeries = entry.getValue();
                break;
            }
        }
        
        if (existingSeries == null) {
            existingSeries = new Series(seriesTitle);
            this.seriesList.put(existingSeries.getId(), existingSeries);
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
        Author existingAuthor = null;
        
        for(Entry<Integer, Author> entry : this.authList.entrySet()) {
            if (entry.getValue().getName().equals(authorName)) {
                if (entry.getValue().getSurname().equals(authorSurname)) {
                    existingAuthor = entry.getValue();
                    break;
                }
            }
        }
        
        if (existingAuthor == null) {
            existingAuthor = new Author(authorName, authorSurname);
            authList.put(existingAuthor.getId(), existingAuthor);
        }
        
        return existingAuthor;
    }

    public HashMap<String, Document> getDocs() {
        return docs;
    }

    public HashMap<Integer, Author> getAuthList() {
        return authList;
    }

    public HashMap<Integer, User> getUsers() {
        return users;
    }

    public HashMap<Integer, Library> getLibs() {
        return libs;
    }

    
    public void listLibraries() {        
        this.libs.entrySet().forEach(entry -> {
            System.out.println(entry.getValue());  
         });
    }
}