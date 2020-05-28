package et3.java.application;

import et3.java.exceptions.*;
import et3.java.model.*;
import java.util.HashMap;
import java.util.Map.Entry;

import java.util.*;
import java.util.stream.Collectors;

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

    /**
     * Constructs an empty <tt>Network</tt>.
     */
    public Network() {
        this.libs = new HashMap<>();
        this.docs = new HashMap<>();
        this.authList = new HashMap<>();
        this.seriesList = new HashMap<>();
        this.users = new HashMap<>();
    }

    // Adding methods

    /**
     * Add document in the network if it doesnt already exists.
     * 
     * @param doc an instance of Document to add in not already in the network's
     *            collection.
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
    
    /**
     * Associates a new Library to the network's collection.
     *
     * @param lib Library instance to add.
     */
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
     * Associates a new User to the network's collection and register it
     * in the desired Library.
     * 
     * @param user  the new User instance to add.
     * @param libId the corresponding Library where the User should subscribe.
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
     * Add document in a Library of the network's collection.
     *
     * @param libraryId integer corresponding to the recipient Library.
     * @param newDoc    the new Document instance to add.
     * @param quantity  integer corresponding to the doc quantity to add.
     */
    public void addDocumentInLib(int libraryId, Document newDoc, int quantity) {
        Library lib = this.libs.get(libraryId);
        
        if (lib != null) {
            lib.addDoc(newDoc, quantity);
            System.out.println("Le document " + newDoc.toString() + " a bien été ajouté "
                    + quantity + " fois à la bibliothèque " + lib.getName() + ".");
        } else {
            System.err.println("Le document " + newDoc.toString() + " n'a pas été ajouté à la bibliothèque "
                    + libraryId + " car elle n'existe pas dans le réseau.");
        }
    }

    // Searching methods

    /**
     * Researching an author
     * @param author part of the name/surname of a searched author
     * @return a set of author
     */
    public Set<Entry<Integer, Author>> searchAuthor(String author) {
        return this.authList.entrySet().stream().filter(auth -> (
                auth.getValue().getName().contains(author) ||
                        auth.getValue().getSurname().contains(author)
        )).collect(Collectors.toSet());
    }

    /**
     * Researching a document by its ISBN
     * @param ISBN part of the researched document's ISBN
     * @return a set of documents
     */
    public Set<Entry<String, Document>> searchISBN(String ISBN) {
        return searchISBN(ISBN, false);
    }

    /**
     * Researching a document by its ISBN
     * @param ISBN the researched document's ISBN
     * @param mustMatch true if the given ISBN must match with the researched documents
     * @return a set of documents
     */
    public Set<Entry<String, Document>> searchISBN(String ISBN, boolean mustMatch) {
        return this.docs.entrySet().stream().filter(doc -> (
                doc.getValue() instanceof Book && (
                        (mustMatch && ((Book) doc.getValue()).getISBN().equals(ISBN)) ||
                                (!mustMatch && ((Book) doc.getValue()).getISBN().contains(ISBN)))
        )).collect(Collectors.toSet());
    }

    /**
     * Researching a document by its EAN
     * @param EAN the researched document's EAN
     * @return a set of documents
     */
    public Set<Entry<String, Document>> searchEAN(String EAN) {
        return searchEAN(EAN, false);
    }

    /**
     * Researching a document by its EAN
     * @param EAN EAN the researched document's EAN
     * @param mustMatch true if the given ISBN must match with the researched documents
     * @return a set of documents
     */
    public Set<Entry<String, Document>> searchEAN(String EAN, boolean mustMatch) {
        return this.docs.entrySet().stream().filter(doc -> (
                (mustMatch && doc.getValue().getEAN().equals(EAN)) ||
                        (!mustMatch && doc.getValue().getEAN().contains(EAN))
        )).collect(Collectors.toSet());
    }

    /**
     * Searching documents by a given type between two date
     * @param type researched document type
     * @param strBegining begining of the time interval
     * @param strEnding ending of the time interval
     * @return a set of documents
     */
    public Set<Entry<String, Document>> searchDocumentsByTypeAndDate(String type, String strBegining, String strEnding) {
        int begining = Integer.parseInt(strBegining);
        int ending = Integer.parseInt(strEnding);

        return this.docs.entrySet().stream().filter(doc -> (
                doc.getValue().getClass().getSimpleName().equals(type) &&
                        !doc.getValue().getDate().equals("?") &&
                        Integer.parseInt(doc.getValue().getDate().substring(0, Math.min(4, doc.getValue().getDate().length()))) >= begining &&
                        Integer.parseInt(doc.getValue().getDate().substring(0, Math.min(4, doc.getValue().getDate().length()))) <= ending
        )).collect(Collectors.toSet());
    }

    // Borrowing methods
    
    /**
     * Method in charge of the document borrowing.
     * 
     * @param userId    integer corresponding to the User identifier.
     * @param docKey    Document's EAN+ISBN concatenation corresponding to a network's Document collection index.
     * @param libId     integer corresponding to the Library identifier.
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
        System.out.println("Le document a bien été emprunté à la bibliothèque "
                + lib.getName() + " par " + user.toString() + ".");
    }

    /**
     * Method in charge of the return of documents.
     *
     * @param userId    integer corresponding to the User identifier.
     * @param docKey    Document's EAN+ISBN concatenation corresponding to a network's Document collection index.
     * @param libId     integer corresponding to the Library identifier.
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
     * Method in charge of the document's transfers.
     *
     * @param libId1    integer corresponding to the source Library identifier.
     * @param libId2    integer corresponding to the recipient Library identifier.
     * @param docKey    Document's EAN+ISBN concatenation corresponding to a network's Document collection index.
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
     * 
     * @param seriesTitle   a string corresponding to the searched Series.
     * @return the Series associated with <tt>seriesTitle</tt> in the network,
     *         or a new Series instance (built with <tt>seriesTitle</tt> as
     *         title) if it wasn't found.
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
     * 
     * @param authorName    a string corresponding to the author's name.
     * @param authorSurname a string corresponding to the author's surname.
     * @return the corresponding Author or a new Author (built with
     *         <tt>authorName</tt> and <tt>authorSurname</tt>) if
     *         not found in network.
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

    /**
     * Displays the network's Library collection.
     */
    public void listLibraries() {        
        this.libs.entrySet().forEach(entry -> {
            System.out.println(entry.getValue());  
         });
    }
}