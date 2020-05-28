package et3.java.application;

import java.io.File;

import et3.java.data.FileReader;
import et3.java.exceptions.DocumentBorrowingException;
import et3.java.exceptions.EANAlreadyExists;
import et3.java.exceptions.ISBNAlreadyExists;
import et3.java.gui.Window;
import et3.java.model.Author;
import et3.java.model.Document;
import et3.java.model.Library;
import et3.java.model.Plan;
import et3.java.model.Series;
import et3.java.model.User;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;

/**
 * Main is the class where the user can operate on a "database" that contains
 * the representation of the entities involved in the "open data biblio case".
 * @author EugenieBrasier, Lucas, Antonin
 */
public class Main
{
    static Network network = null;
        
    static Scanner sc = new Scanner(System.in);
    
    public static void main(String[] args) {
        
        if(args.length > 0)
        {
            File tempFile = new File(args[0]);
            
            if(tempFile.exists())
            {
                System.out.println("[Main] Reading the file " + args[0] + " ...");
                
                //We start by reading the CSV file
//                FileReader.getDataFromCSVFile(args[0]);
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
     * Defines actions to do when a key has been pressed.
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
                System.out.println("Entrez le title du document à ajouter : (not null)");
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
                
                // TODO : in which library ? (optionnal ?)
                
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
        
        JFrame f = new Window();
        
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
        
        System.out.println("\nFermez la fenêtre \"Consultation\" pour revenir à l'accueil.\n");
        
        
        // Why are 'd' or 'b' or 'u' sometime skipped ?!?
        // Why in these cases the window isnt active  ?!?
        // forced to put a    Thread.sleep(100);    to let the window open up...
    
        System.out.println("[debug] isActive: " + f.isActive());
        
        while (((Window) f).isActive()) { }     // wait window closing
        
        f.dispose();
    }

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
        
        
        // TODO : use JFrame if more than XX results ??
        
        
        switch (searchChoice.toLowerCase()) {
            case "a":
                // TODO : perform research by author (by name, surmane or both)
                break;
            case "i":
                // TODO : perform research
                break;
            case "e":
                // TODO : perform research
                break;
            case "t":
                // TODO : perform research
                break;
            case "r":
                return;
            default:
                System.err.println("Touche non reconnue !");
        }
    }


    private static void performBorrowing() {
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
                int userId = sc.nextInt();
                
                // find document to borrow
                System.out.println("EAN du document à emprunter : (nullable)");
                String borrowedDocEAN = sc.nextLine();
                
                String borrowedDocISBN = "";
                if (borrowedDocEAN.isEmpty()) {
                    do {
                        System.out.println("ISBN du document à emprunter : (not null)");
                        borrowedDocISBN = sc.nextLine();
                    } while (borrowedDocISBN.isEmpty());
                } else {
                    System.out.println("ISBN du document à emprunter : (nullable)");
                    borrowedDocISBN = sc.nextLine();
                }
                
                System.out.println("Saisisez où emprunter le document parmi les bibliothèques ci-dessous :");
                network.listLibraries();
                
                // TODO : what do if NaN ?
                int libId = sc.nextInt();
                
                try {
                    network.registerBorrowing(userId, borrowedDocEAN + borrowedDocISBN, libId);
                } catch (DocumentBorrowingException dbe) {
                    System.err.println(dbe.getMessage());
                }
                break;
            case "r":
                // TODO
                
                // user = getUser("user")
                // Return document
                // user.returnDocument(carteAlpes, newLib);
                break;
            case "t":
                // TODO
                
                System.out.println("Bibliothèque d'origine ? ... :");
                network.listLibraries();
                
                // TODO : what do if NaN ?
                int lib1 = sc.nextInt();
                
                System.out.println("Bibliothèque destinataire ? ... :");
                network.listLibraries();
                
                // TODO : what do if NaN ?
                int lib2 = sc.nextInt();
                
                System.out.println("EAN du document à transférer ? ... :");
                String docEAN = sc.nextLine();
                
                // Doc doc = getDocByEAN(docEAN);
                
                // if (doc != null && lib1.hasDoc())
                //    lib1.exchangeDocument(Library lib2)
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
