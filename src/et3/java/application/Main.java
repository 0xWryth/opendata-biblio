package et3.java.application;

import java.io.File;

import et3.java.data.FileReader;
import et3.java.model.Author;
import et3.java.model.Document;
import et3.java.model.Library;
import et3.java.model.Plan;
import et3.java.model.User;

/**
 * Main is the class where the user can operate on a "database" that contains
 * the representation of the entities involved in the "open data biblio case".
 * @author EugenieBrasier, Lucas, Antonin
 */
public class Main
{
    public static void main(String[] args)
    {
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
        
        Library newLib = new Library("Médithèque des quais");
            
        network.addLibrary(newLib);
        network.addLibrary(newLib);     // triggers Serr("non add car existe")
        network.addLibrary(new Library("Médithèque des quais"));
        network.listLibraries();
//        network.listDocuments();

        User paul = new User("Paul", "Dupont");
        network.addUser(new User("Pierre", "Dupont"), newLib);
        network.addUser(new User("Pierre", "Dupont"), newLib);
        network.addUser(paul, newLib);
        network.addUser(paul, newLib);     // triggers Serr("non add car existe")
        network.addUser(new User("test", "test"), new Library("Lib test"));     // triggers Serr("non add car lib inconnue")
        
        // triggers Serr("déjà abonné") but should'nt be called here (should be
        // called across the Network) because newLib is here for test purpose
        newLib.registerUser(paul);
        
        Document carteAlpes = new Plan("Carte des Alpes", "iueuhfpiuezgfp", "2020", "Office de tourisme des Alpes");
        carteAlpes.setAuthor(new Author("aaaa", "bbb"));
        network.addDocument(carteAlpes);
    }
    
    /**
     * Defines actions to do when a key has been pressed.
     */
    public void handleKeyboard() {
        // TODO
        throw new UnsupportedOperationException();
    }
}
