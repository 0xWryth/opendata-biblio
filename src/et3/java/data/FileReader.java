package et3.java.data;

import et3.java.application.Network;
import et3.java.exceptions.EANAlreadyExists;
import et3.java.exceptions.ISBNAlreadyExists;
import et3.java.model.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FileReader is an utility class used to parse a .csv into a Network wich can
 * be manipulated by the user.
 * @author EugenieBrasier, Lucas, Antonin
 */
public class FileReader
{
    /**
     *
     * @param csvFilePath
     * @return A filled Network object
     * @author EugenieBrasier, adepreis
     */
    public static Network loadDataFromCSVFile(String csvFilePath)
    {
        Library libAimeCesaire      = new Library("Aime Cesaire");
        Library libEdmondRostand    = new Library("Edmond Rostand");
        Library libJeanPierreMelville = new Library("Jean Pierre Melville");
        Library libOscarWilde       = new Library("Oscar Wilde");
        Library libSaintSimon       = new Library("Saint Simon");
        
        Network network = new Network();
        
        String line     = "";
        String[] data   = null;
        String separator = ";(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
        
        //Document data
        String isbn;
        String ean;
        String title;
        String publisher;
        String date;
        String seriesTitle;
        Integer seriesNumber;
        String authorName;
        String authorSurname;
        String type;
        int totalCopies;
        int numberCopyAimeCesaire;
        int numberCopyEdmondRostand;
        int numberCopyJeanPierreMelville;
        int numberCopyOscarWilde;
        int numberCopySaintSimon;
        
        try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(csvFilePath), StandardCharsets.ISO_8859_1))
        {
            //Read the first line
            line = bufferedReader.readLine();
            
            //Get data from the line
            data = line.split(separator, -1);
            
            if(data.length != 16)
            {
                System.err.println("[FileReader] The file at " + csvFilePath + " does not contain the right number of columns.");
                return null;
            }
            
            //Read the file line by line
            while ((line = bufferedReader.readLine()) != null)
            {
                Document newDoc;
                
                //Get data from the line
                data = line.split(separator, -1);
                
                //Sort data
                
                //Get the ISBN number
                isbn = data[0];
                
                //Get the EAN number
                ean = data[1];
                
                
                /* TODO : IF A DOC WITH THE SAME EAN EXISTS, WHAT DO ? */
                
                
                if(ean.equals("") && isbn.equals(""))
                    continue;   // ignore document with no "serial number"
                
                //Get the title of the document
                title = data[2];
                
                //Get the name of the publisher
                publisher = data[3];
                
                //Get the publication date
                try
                {
                    int dateInt = Integer.parseInt(data[4].replaceAll("[^0-9]", ""));
                    
                    if(dateInt%10000 >= 2021 || dateInt%10000 < 0)
                    {
                        date = "?";
                    }
                    else if(dateInt/10000 == 0)
                    {
                        date = Integer.toString(dateInt%10000);
                    }
                    else
                    {
                        date = dateInt%10000 + "-" + dateInt/10000;
                    }
                }
                catch (Exception exception)
                {
                    date = "?";
                }
                
                //Get the title of the series
                seriesTitle = data[5];
                
                //Get the number of this document in the series
                try
                {
                    seriesNumber = Integer.parseInt(data[6]);
                }
                catch (Exception exception)
                {
                    seriesNumber = null;
                }
                
                //Get the name of the author
                authorSurname = data[7];
                
                //Get the surname of the author
                authorName = data[8];

                
                //Get the type of the document
                type = data[9];
                
                //Get the total number of copy in Paris for this document
                try
                {
                    totalCopies = Integer.parseInt(data[10]);
                }
                catch (Exception exception)
                {
                    totalCopies = 0;
                }
                
                //Get the number of copy in the library "Aime Cesaire"
                try
                {
                    numberCopyAimeCesaire = Integer.parseInt(data[11]);
                }
                catch (Exception exception)
                {
                    numberCopyAimeCesaire = 0;
                }
                
                //Get the number of copy in the library "Edmond Rostand"
                try
                {
                    numberCopyEdmondRostand = Integer.parseInt(data[12]);
                }
                catch (Exception exception)
                {
                    numberCopyEdmondRostand = 0;
                }
                
                //Get the number of copy in the library "Jean-Pierre Melville"
                try
                {
                    numberCopyJeanPierreMelville = Integer.parseInt(data[13]);
                }
                catch (Exception exception)
                {
                    numberCopyJeanPierreMelville = 0;
                }
                
                //Get the number of copy in the library "Oscar Wilde"
                try
                {
                    numberCopyOscarWilde = Integer.parseInt(data[14]);
                }
                catch (Exception exception)
                {
                    numberCopyOscarWilde = 0;
                }
                
                //Get the number of copy in the library "Saint-Simon"
                try
                {
                    numberCopySaintSimon = Integer.parseInt(data[15]);
                }
                catch (Exception exception)
                {
                    numberCopySaintSimon = 0;
                }
                
                                
                newDoc = initDocByType(type, title, ean, isbn, date, publisher);
                
                
                if (!authorName.equals("") || !authorSurname.equals("")) {
                    Author docAuthor = network.getAuthor(authorName, authorSurname);
                    newDoc.setAuthor(docAuthor);
                }
                
                
                
                try {
                    // TODO :
                    // newDoc.setTotalCopies(); // useful ????
                    
                    network.addDocument(newDoc);
                } catch (EANAlreadyExists | ISBNAlreadyExists ex) {
                    System.err.println(ex.getMessage());
                }
                
                if (!seriesTitle.equals("")) {
                    // use seriesNumber ???
                    Series docSerie = network.getSeries(seriesTitle);
                    docSerie.addDoc(newDoc);
                }
                
                if (numberCopyAimeCesaire > 0)          libAimeCesaire.addDoc(newDoc, numberCopyAimeCesaire);
                if (numberCopyEdmondRostand > 0)        libEdmondRostand.addDoc(newDoc, numberCopyEdmondRostand);
                if (numberCopyJeanPierreMelville > 0)   libJeanPierreMelville.addDoc(newDoc, numberCopyJeanPierreMelville);
                if (numberCopyOscarWilde > 0)           libOscarWilde.addDoc(newDoc, numberCopyOscarWilde);
                if (numberCopySaintSimon > 0)           libSaintSimon.addDoc(newDoc, numberCopySaintSimon);
            }
        }
        catch (IOException exception)
        {
            System.err.println(exception);
        }
        
        
        network.addLibrary(libAimeCesaire);
        network.addLibrary(libEdmondRostand);
        network.addLibrary(libJeanPierreMelville);
        network.addLibrary(libOscarWilde);
        network.addLibrary(libSaintSimon);
        
        return network;
    }

    public static Document initDocByType(String type, String title, String ean, String isbn, String date, String publisher) {
        switch(type) {
            case "Partition" :
                return new SheetMusic(title, ean, date, publisher);
            case "Revue de Fonds specialises" :
            case "Revue pour adulte" :
            case "Revue jeunesse" :
                return new Review(title, ean, date, publisher);
            case "Carte ou plan" :
                return new Plan(title, ean, date, publisher);
            case "DVD-video tous publics" :
            case "DVD- video > 18 ans" :
            case "DVD- video > 12 ans" :
            case "DVD-video > 16 ans" :
            case "DVD jeunesse" :
                return new DVD(title, ean, date, publisher);
            case "Vinyle" :
                return new VinylDisc(title, ean, date, publisher);
            case "Disque compact" :
                return new CD(title, ean, date, publisher);
            case "Jeux de societe" :
                return new BoardGame(title, ean, date, publisher);
            case "Jeux Videos tous publics" :
            case "Jeux videos > 18 ans" :
                return new VideoGame(title, ean, date, publisher);
            case "Livre de section jeunesse > 12 ans" :
            case "Livres et periodiques DAiSY" :
            case "Livre de Fonds specialises" :
            case "Livre en langue etrangere" :
            case "Livre en gros caracteres" :
            case "Livre sonore pour adulte" :
            case "Livre sonore jeunesse" :
            case "Livre pour adulte" :
            case "Livre jeunesse" :
                return new Book(title, ean, isbn, date, publisher);
            case "Bande dessinee jeunesse >12 ans" :
            case "Bande dessinee pour adulte" :
            case "BD adulte non reservable" :
            case "Bande dessinee jeunesse" :
                return new Comic(title, ean, isbn, date, publisher);
            case "Documents numeriques et multimedia jeunesse" :
            case "Documents numeriques et multimedia adulte" :
            case "Enregistrement musical pour la jeunesse" :
            case "Diapositives jeunesse" :
            case "Methode de langue" :
            case "Methode musicale" :
            case "Non empruntable" :
            case "Nouveaute" :
            case "Usuels" :
            default:
                return new Other(title, ean, date, publisher);
        }
    }
}
