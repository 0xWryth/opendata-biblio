package et3.java.application;

import java.io.File;

import et3.java.data.FileReader;

/**
 * Main is the class where the user can operate on a "database" that contains
 * the representation of the entities involved in the "open data biblio case".
 * @author EugenieBrasier, Lucas, Antonin
 */
public class Main
{
    public static void main(String[] args)
    {
        DB db = null;
        
        if(args.length > 0)
        {
            File tempFile = new File(args[0]);
            
            if(tempFile.exists())
            {
                System.out.println("[Main] Reading the file " + args[0] + " ...");
                
                //We start by reading the CSV file
//                FileReader.getDataFromCSVFile(args[0]);
                db = FileReader.loadDataFromCSVFile(args[0]);
                
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
        if (db == null)
            return;
            
        db.listLibraries();
        db.listDocuments();
    }
    
    /**
     * Defines actions to do when a key has been pressed.
     */
    public void handleKeyboard() {
        // TODO
        throw new UnsupportedOperationException();
    }
}
