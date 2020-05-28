package et3.java.application;

import java.io.File;

import et3.java.data.FileReader;
import et3.java.exceptions.*;
import et3.java.gui.Window;
import et3.java.model.*;
import java.util.Scanner;

/**
 * Main is the class where the user can operate on a "database" that contains
 * the representation of the entities involved in the "open data biblio case".
 * @author EugenieBrasier, Lucas, Antonin
 */
public class Main
{
    static Network network = null;
        
    static Scanner sc = new Scanner(System.in);

    static Window f = new Window();
    
    public static void main(String[] args) {
        
        if(args.length > 0)
        {
            File tempFile = new File(args[0]);
            
            if(tempFile.exists())
            {
                System.out.println("[Main] Reading the file " + args[0] + " ...");
                
                // hydrate network
                network = FileReader.loadDataFromCSVFile(args[0]);
                
                System.out.println("[Main] End of the file " + args[0] + ".");
            }
            else
            {
                System.err.println("[Main] No file " + args[0]);
            }
        }
        else
        {
            System.err.println("[Main] You should Entrez le CSV file path as a parameter.");
        }
        
        //TODO Project :)
        if (network == null)
            return;
        
        handleKeyboard();
        
        sc.close();
    }


    /**
     * Defines actions to do when the user interacts with the application.
     */
    private static void handleKeyboard() {
        boolean exitAsked = false;
        do {
            printSeparator();
            System.out.println("Que souhaitez vous faire ? (entrer la première lettre pour sélectionner l'action)\n");

            System.out.println("[A]jouter");
            System.out.println("[L]ister");
            System.out.println("[C]hercher");
            System.out.println("[E]mprunter / Retourner");
            System.out.println("[O]uverte de l'interface graphique");
            System.out.println("\n[Q]uitter\n");

            String actionChoice = null;
            
            do {
                actionChoice = sc.nextLine();
            } while (actionChoice.isEmpty());
            

            switch (actionChoice.toLowerCase()) {
                case "a":
                    performAdding();
                    break;
                case "l":
                    performListing();
                    break;
                case "c":
                    performFinding();
                    break;
                case "o":
                    f.setVisible(true);
                    break;
                case "e":
                    performBorrowing();
                    break;
                case "q":
                    exitAsked = true;
                    break;
                default:
                    System.err.println("Touche non reconnue !");
            }
        } while (!exitAsked);
        
        
        System.out.println("Fin du programme");    
    }

    /**
     * CLI adding methods
     */
    private static void performAdding() {
        printSeparator();
        System.out.println("Que souhaitez vous ajouter ? (entrer la première lettre pour sélectionner l'action)\n");
        
        System.out.println("[B]ibliothèque");
        System.out.println("[D]ocument");
        System.out.println("[U]tilisateur");
        System.out.println("\n[R]etour\n");
        
        String addChoice = null;
        
        do {
            addChoice = sc.nextLine();
        } while (addChoice.isEmpty());
        
        switch (addChoice.toLowerCase()) {
            case "b":
                System.out.println("Entrez le nom de la bibliothèque à ajouter :");
                
                String libName = sc.nextLine();

                Library newLib = new Library(libName);
                network.addLibrary(newLib);
                
                break;
            case "d":
                String type = null;
                System.out.println("Entrez le type du document à ajouter : (not null)");
                do {
                    type = sc.nextLine();
                } while (type.isEmpty());
                
                String title = null;
                System.out.println("Entrez le titre du document à ajouter : (not null)");
                do {
                    title = sc.nextLine();
                } while (title.isEmpty());
                
                String ean = null;
                System.out.println("Entrez l'EAN du document à ajouter : (nullable)");
                ean = sc.nextLine();
                
                String isbn = null;
                if (ean.isEmpty()) {
                    System.out.println("Entrez l'ISBN du document à ajouter : (not null because EAN is null)");
                    do {
                        isbn = sc.nextLine();
                    } while (isbn.isEmpty());
                } else  {
                    System.out.println("Entrez l'ISBN du document à ajouter : (nullable)");
                    isbn = sc.nextLine();
                }
                
                String date = null;
                System.out.println("Entrez la date du document à ajouter : (nullable)");
                date = sc.nextLine();
                
                String publisher = null;
                System.out.println("Entrez l'éditeur du document à ajouter : (nullable)");
                publisher = sc.nextLine();
                
                String authorName = null;
                System.out.println("Entrez le prénom de l'auteur du document à ajouter : (nullable)");
                authorName = sc.nextLine();
                
                String authorSurname = null;
                if (!authorName.isEmpty()) {
                    System.out.println("Entrez le nom de l'auteur du document à ajouter : (not null because authorSurname is not null)");
                    do {
                        authorSurname = sc.nextLine();
                    } while (authorSurname.isEmpty());
                }
                
                String seriesTitle = null;
                System.out.println("Entrez la série à laquelle appartient le document à ajouter : (nullable)");
                seriesTitle = sc.nextLine();
                
                Document newDoc = FileReader.initDocByType(type, title, ean, isbn, date, publisher);
                
                if (!authorName.isEmpty()) {
                    Author docAuthor = network.getAuthor(authorName, authorSurname);
                    newDoc.setAuthor(docAuthor);
                    docAuthor.addWrittenDoc(newDoc);
                }
                
                if (!seriesTitle.equals("")) {
                    Series docSerie = network.getSeries(seriesTitle);
                    docSerie.addDoc(newDoc);
                }
                
                try {
                    network.addDocument(newDoc);
                } catch (EANAlreadyExists | ISBNAlreadyExists ex) {
                    System.err.println(ex.getMessage());
                }
                
                int libraryId = -1;
                do {
                    printSeparator();
                    System.out.println("Parmi les bibliothèques ci-dessous, dans laquelle "
                            + "ajouter le document :");
                    network.listLibraries();
                    System.out.println("(Tapez <Enter> lorsque vous avez fini)");
                    
                    String s = sc.nextLine();
                    if (s.isEmpty()) {
                        break;
                    }
                    
                    do {
                        try {
                            libraryId = Integer.parseInt(s);
                        } catch (NumberFormatException e) {
                            System.err.println(e.getMessage());
                        }
                    } while (libraryId < 0);
                    
                    
                    System.out.println("Entrez le nombre de copies à ajouter :");
                    int copy = -1;
                    do {
                        try {
                            copy = Integer.parseInt(sc.nextLine());
                        } catch (NumberFormatException e) {
                            System.err.println(e.getMessage());
                        }
                    } while (copy < 0);
                    
                    network.addDocumentInLib(libraryId, newDoc, copy);
                } while (true);
                
                break;
            case "u":
                System.out.println("Entrez le prénom de l'utilisateur à ajouter :");
                String name = sc.nextLine();
                
                System.out.println("Entrez le nom de l'utilisateur à ajouter :");
                String surname = sc.nextLine();
                
                
                System.out.println("Parmi les bibliothèques ci-dessous, entrez le "
                        + "numéro à laquelle inscrire le nouvel utilisateur :");
                network.listLibraries();
                
                // TODO : what do if NaN ? if not an existing libId ?
                int libNumber = sc.nextInt();
                network.addUser(new User(name, surname), libNumber);
                
                break;
            case "r":
                return;
            default:
                System.err.println("Touche non reconnue !");
        }
        
    }

    /**
     * CLI listing methods
     */
    private static void performListing() {
        printSeparator();
        
        System.out.println("Que souhaitez vous lister ? (entrer la première lettre pour sélectionner l'action)\n");
        
        System.out.println("[A]uteurs");
        System.out.println("[B]ibliothèques");
        System.out.println("[D]ocuments");
        System.out.println("[U]tilisateurs");
        System.out.println("\n[R]etour\n");
        
        String listChoice = null;
        
        do {
            listChoice = sc.nextLine();
        } while (listChoice.isEmpty());
        
        // TODO : Display one tab by one tab or all in one window doesnt matter ?
        
        
        switch (listChoice.toLowerCase()) {
            case "a":
                ((Window) f).setAuthorData(network.getAuthList());
                break;
            case "b":
                ((Window) f).setLibraryData(network.getLibs());
                break;
            case "d":
                ((Window) f).setDocData(network.getDocs());
                break;
            case "u":
                ((Window) f).setUserData(network.getUsers());
                break;
            case "r":
                return;
            default:
                System.err.println("Touche non reconnue !");
        }
    }

    /**
     * CLI finding methods
     */
    private static void performFinding() {
        printSeparator();
        
        System.out.println("Quel type de recherche souhaitez vous effectuer ? (entrez la lettre entre crochets)\n");
        
        System.out.println("[A]uteur (trouver tous les documents d’un même auteur)");
        System.out.println("[I]SBN \t(trouver un livre par son ISBN)");
        System.out.println("[E]AN \t(trouver un document par son EAN)");
        System.out.println("[T]ype \t(nombre de documents d'un type publiés dans un intervalle de temps)");
        System.out.println("\n[R]etour\n");
        
        String searchChoice = null;
        
        do {
            searchChoice = sc.nextLine();
        } while (searchChoice.isEmpty());
        
        switch (searchChoice.toLowerCase()) {
            case "a":
                System.out.println("Entrez le nom/surnom de l'auteur à rechercher :");
                String author = sc.nextLine();

                ((Window) f).setResearchData("authors", author, network.searchAuthor(author));
                break;
            case "i":
                System.out.println("Entrez l'ISBN du livre à rechercher :");
                String ISBN = sc.nextLine();

                ((Window) f).setResearchData("ISBN", ISBN, network.searchISBN(ISBN));
                break;
            case "e":
                System.out.println("Entrez l'EAN du document à rechercher :");
                String EAN = sc.nextLine();

                ((Window) f).setResearchData("EAN", EAN, network.searchEAN(EAN));
                break;
            case "t":
                printSeparator();

                System.out.println("Choix du type de documents ? (entrez la lettre entre crochets)\n");

                System.out.println("[C]omic");
                System.out.println("[B]ook");
                System.out.println("C[D]");
                System.out.println("[V]ynil");
                System.out.println("[G]ame");
                System.out.println("Bo[A]rd game");
                System.out.println("[P]lan");
                System.out.println("Revie[W]");
                System.out.println("[S]heet Music");
                System.out.println("[O]ther");
                System.out.println("\n[R]etour\n");

                String docTypeChoice = null;

                do {
                    docTypeChoice = sc.nextLine();
                } while (docTypeChoice.isEmpty());

                String type = "";
                switch (docTypeChoice.toLowerCase()) {
                    case "c": type = "Comic"; break;
                    case "b": type = "Book"; break;
                    case "d": type = "CD"; break;
                    case "v": type = "VinylDisc"; break;
                    case "g": type = "VideoGame"; break;
                    case "a": type = "BoardGame"; break;
                    case "p": type = "Plan"; break;
                    case "w": type = "Review"; break;
                    case "s": type = "SheetMusic"; break;
                    case "o": type = "Other"; break;
                    case "r": return;
                    default:
                        System.err.println("Touche non reconnue !");
                }

                printSeparator();

                System.out.println("Saisissez l'année de début de recherche : (sour le format yyyy)\n");
                String strBegining = "";
                do {
                    strBegining = sc.nextLine();
                } while (strBegining.length() != 4);

                System.out.println("Saisissez l'année de fin de recherche : (sour le format yyyy)\n");
                String strEnding = "";
                do {
                    strEnding = sc.nextLine();
                } while (strEnding.length() != 4);


                String strSearch = "[" + strBegining + "-" + strEnding + "] " + type;
                ((Window) f).setResearchData("DATE", strSearch, network.searchDocumentsByTypeAndDate(type, strBegining, strBegining));

                break;
            case "r":
                return;
            default:
                System.err.println("Touche non reconnue !");
        }
    }

    /**
     * CLI borrowing methods
     */
    private static void performBorrowing() {
        int userId = -1;
        String docEAN;
        String docISBN = "";
        int libId = -1;
        
        printSeparator();
        
        System.out.println("Action emprunt/retour ? (entrez la lettre entre crochets)\n");
        
        System.out.println("[E]mprunt utilisateur");
        System.out.println("[R]etour utilisateur");
        System.out.println("[T]ransférer des documents entre bibliothèques");
        System.out.println("\n[A]nnuler\n");
        
        String exchangeChoice = null;
        
        do {
            exchangeChoice = sc.nextLine();
        } while (exchangeChoice.isEmpty());
        
        
        switch (exchangeChoice.toLowerCase()) {
            case "e":
//                System.out.println("Prénom de l'utilisateur qui souhaite emprunter :");
//                String name = sc.nextLine();
//                
//                System.out.println("Nom de l'utilisateur qui souhaite emprunter :");
//                String surname = sc.nextLine();
                
                // Use ID instead of name/surname ?
                System.out.println("Identifiant de l'utilisateur qui souhaite emprunter :");
                do {
                    try {
                        userId = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.err.println(e.getMessage());
                    }
                } while (userId < 0);

                
                System.out.println("EAN du document à emprunter : (nullable)");
                docEAN = sc.nextLine();
                
                if (docEAN.isEmpty()) {
                    do {
                        System.out.println("ISBN du document à emprunter : (not null)");
                        docISBN = sc.nextLine();
                    } while (docISBN.isEmpty());
                } else {
                    System.out.println("ISBN du document à emprunter : (nullable)");
                    docISBN = sc.nextLine();
                }
                
                System.out.println("Saisisez où emprunter le document parmi les bibliothèques ci-dessous :");
                network.listLibraries();
                
                do {
                    try {
                        libId = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.err.println(e.getMessage());
                    }
                } while (libId < 0);
                
                try {
                    network.registerBorrowing(userId, docEAN + docISBN, libId);
                } catch (DocumentBorrowingException dbe) {
                    System.err.println(dbe.getMessage());
                }
                break;
            
            case "r":
                System.out.println("Identifiant de l'utilisateur qui souhaite emprunter :");
                do {
                    try {
                        userId = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.err.println(e.getMessage());
                    }
                } while (userId < 0);

                
                System.out.println("EAN du document à emprunter : (nullable)");
                docEAN = sc.nextLine();
                
                if (docEAN.isEmpty()) {
                    do {
                        System.out.println("ISBN du document à emprunter : (not null)");
                        docISBN = sc.nextLine();
                    } while (docISBN.isEmpty());
                } else {
                    System.out.println("ISBN du document à emprunter : (nullable)");
                    docISBN = sc.nextLine();
                }
                
                System.out.println("Saisisez où emprunter le document parmi les bibliothèques ci-dessous :");
                network.listLibraries();
                
                do {
                    try {
                        libId = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.err.println(e.getMessage());
                    }
                } while (libId < 0);
                
                try {
                    network.recordDocumentReturn(userId, docEAN + docISBN, libId);
                } catch (UnregisteredUser | NoDocumentFound ex) {
                    System.err.println(ex.getMessage());
                }
                break;
            
            case "t":                
                System.out.println("Bibliothèque d'origine ? ... :");
                network.listLibraries();
                
                int libId1 = -1;
                do {
                    try {
                        libId1 = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.err.println(e.getMessage());
                    }
                } while (libId1 < 0);
                
                System.out.println("Bibliothèque destinataire ? ... :");
                network.listLibraries();
                
                int libId2 = -1;
                do {
                    try {
                        libId2 = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.err.println(e.getMessage());
                    }
                } while (libId2 < 0);
                
                System.out.println("EAN du document à transférer ? ... :");
                docEAN = sc.nextLine();
                
                if (docEAN.isEmpty()) {
                    do {
                        System.out.println("ISBN du document à emprunter : (not null)");
                        docISBN = sc.nextLine();
                    } while (docISBN.isEmpty());
                } else {
                    System.out.println("ISBN du document à emprunter : (nullable)");
                    docISBN = sc.nextLine();
                }
                
                try {
                    network.transferDocument(libId1, libId2, docEAN + docISBN);
                } catch (DocumentNotAvailable ex) {
                    System.err.println(ex.getMessage());
                }
                break;
                
            case "a":
                return;
                
            default:
                System.err.println("Touche non reconnue !");
        }
    }
    
    /**
     * Displays a '=' line for a more readable CLI.
     */
    private static void printSeparator() {
        System.out.println("\n=======================================================================================");
    }
}
