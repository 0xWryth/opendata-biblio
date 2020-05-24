package et3.java.application;

import java.io.File;

import et3.java.data.FileReader;
import et3.java.gui.Window;
import et3.java.model.Author;
import et3.java.model.Document;
import et3.java.model.Library;
import et3.java.model.Plan;
import et3.java.model.User;

import javax.swing.*;

/**
 * Main is the class where the user can operate on a "database" that contains
 * the representation of the entities involved in the "open data biblio case".
 * @author EugenieBrasier, Lucas, Antonin
 */
public class Main
{
    public static void main(String[] args)
    {
        JFrame f = new Window();

        Network network = null;
        
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
            System.err.println("[Main] You should enter the CSV file path as a parameter.");
        }
        
        //TODO Project :)
        if (network == null)
            return;

        // ------------------------------------------------------------------------------
        // TESTING TO ADD 2 TIME THE SAME LIBRARY
        Library newLib = new Library("Médiathèque des quais");
        network.addLibrary(newLib);

        // Adding the same library a second time
        network.addLibrary(newLib); /* => triggers Serr("non add car existe") */

        // Adding the same library with a different reference
        network.addLibrary(new Library("Médiathèque des quais"));

        // Printing library list
        network.listLibraries();
        //        network.listDocuments();
        // ------------------------------------------------------------------------------



        // ------------------------------------------------------------------------------
        // TESTING TO ADD 2 TIME THE SAME USER
        User paul = new User("Paul", "Dupont");
        network.addUser(new User("Pierre", "Dupont"), newLib);


        network.addUser(new User("Pierre", "Dupont"), newLib);
        network.addUser(paul, newLib);
        network.addUser(paul, newLib);     // triggers Serr("non add car existe")
        // ------------------------------------------------------------------------------



        // ------------------------------------------------------------------------------
        // TESTING TO ADD AN USER TO AN UNKNOWN LIBRARY
        network.addUser(new User("test", "test"), new Library("Lib test"));     // triggers Serr("non add car lib inconnue")
        // ------------------------------------------------------------------------------



        // ------------------------------------------------------------------------------
        // TESTING TO ADD REGISTER AN USER A SECOND TIME TO A LIBRARY HE IS ALREADY REGISTERED ON
        
        // triggers Serr("déjà abonné") but should'nt be called here (should be
        // called across the Network) because newLib is here for test purpose
        newLib.registerUser(paul);
        // ------------------------------------------------------------------------------



        // ------------------------------------------------------------------------------
        // TESTING TO ADD TWO TIME THE SAME DOCUMENT
        Document carteAlpes = new Plan("Carte des Alpes", "iueuhfpiuezgfp", "2020", "Office de tourisme des Alpes");
        carteAlpes.setAuthor(new Author("aaaa", "bbb"));
        network.addDocument(carteAlpes);

        network.addDocument(carteAlpes); // triggers Serr()
        // ------------------------------------------------------------------------------

        ((Window) f).setLibraryData(network.getLibs());
        ((Window) f).setDocData(network.getDocs());
        ((Window) f).setAuthorData(network.getAuthList());
        ((Window) f).setUserData(network.getUsers());
    }


    /**
     * Defines actions to do when a key has been pressed.
     */
    public void handleKeyboard() {
        // TODO
        throw new UnsupportedOperationException();
    }
}
