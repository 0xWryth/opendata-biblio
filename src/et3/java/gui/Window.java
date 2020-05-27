package et3.java.gui;

import et3.java.model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.soap.Node;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Window extends JFrame {
    private JPanel mainPanel = new JPanel(new GridLayout(0, 1));
    private JTabbedPane tab = new JTabbedPane();

    // ------------
    String[] docHeader = {"Type", "EAN", "Title", "Date", "Publisher", "ISBN"};
    TabbedArray docTab =  new TabbedArray(docHeader);

    // ------------
    String[] libraryHeader = {"Id", "Library"};
    TabbedArray libTab =  new TabbedArray(libraryHeader);

    // ------------
    String[] authorArray = {"Id", "Name", "Surname"};
    TabbedArray authorTab =  new TabbedArray(authorArray);

    // ------------
    String[] userHeader = {"Id", "Name", "Surname"};
    TabbedArray userTab =  new TabbedArray(userHeader);

    // ------------
    String[] searchHeader = {""};
    TabbedArray searchTab =  new TabbedArray(searchHeader, "Dernière recherche");

    public Window() {
        super("Consultation");
        
        // do not close app on consultation window close
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        mainPanel.setLayout(new BorderLayout());

        setSize(500, 600);

        tab.addTab("Library", libTab);
        tab.addTab("Documents", docTab);
        tab.addTab("Author", authorTab);
        tab.addTab("Users", userTab);
        tab.addTab("Recherche : -", searchTab);
        mainPanel.add(tab);
        setContentPane(mainPanel);

        setVisible(true);
        setAlwaysOnTop(true);
    }

    public void setLibraryData(HashMap<Integer, Library> hm) {
        DefaultTableModel m = libTab.getModel();
        hm.forEach((i, l) -> {
            Object[] toAdd = {l.getId(), l.getName()};
            m.addRow(toAdd);
        });
        libTab.setModel(m);
    }

    public void setDocData(ArrayList<Document> docs) {
        setDocData(docs, docTab);
    }

    public void setDocData(ArrayList<Document> docs, TabbedArray ta) {
        DefaultTableModel m = ta.getModel();
        for (Document doc : docs) {
            String ISBN = doc instanceof Book ? ((Book) doc).getISBN() : "";
            Object[] toAdd = {doc.getClass().getSimpleName(), doc.getEAN(), doc.getTitle(), doc.getDate(), doc.getPublisher(), ISBN};
            m.addRow(toAdd);
        }
        ta.setModel(m);
    }

    public void setUserData(HashMap<Integer, User> hm) {
        DefaultTableModel m = userTab.getModel();
        hm.forEach((i, u) -> {
            Object[] toAdd = {u.getId(), u.getName(), u.getSurname()};
            m.addRow(toAdd);
        });
        userTab.setModel(m);
    }

    public void setAuthorData(HashMap<Integer, Author> hm) {
        setAuthorData(hm, authorTab);
    }

    public void setAuthorData(HashMap<Integer, Author> hm, TabbedArray ta) {
        DefaultTableModel m = ta.getModel();
        hm.forEach((i, auth) -> {
            Object[] toAdd = {auth.getId(), auth.getName(), auth.getSurname()};
            m.addRow(toAdd);
        });
        ta.setModel(m);
    }

    public void setResearchData(String type, String strSearch, Object data) {
        switch (type) {
            case "authors":
                tab.setTitleAt(4, "Recherche - Auteurs : " + strSearch);
                searchTab.updateHeaders(authorArray);
                HashMap<Integer, Author> authorData = new HashMap<Integer, Author>();

                ((Set<Map.Entry<Integer, Author>>) data).forEach(d -> {
                    authorData.put(d.getKey(), d.getValue());
                });
                setAuthorData(authorData, searchTab);
                break;
            case "ISBN":
                tab.setTitleAt(4, "Recherche - ISBN : " + strSearch);
                searchTab.updateHeaders(docHeader);
                setDocData((ArrayList<Document>) data, searchTab);
                break;
            case "EAN":
                tab.setTitleAt(4, "Recherche - EAN : " + strSearch);
                searchTab.updateHeaders(docHeader);
                setDocData((ArrayList<Document>) data, searchTab);
                break;
            case "DATE":
                tab.setTitleAt(4, "Recherche - Date : " + strSearch);
                searchTab.updateHeaders(docHeader);
                setDocData((ArrayList<Document>) data, searchTab);
                break;
        }
    }
}
