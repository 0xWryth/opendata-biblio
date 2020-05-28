package et3.java.gui;

import et3.java.model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;

public class Window extends JFrame {
    private JPanel mainPanel = new JPanel(new GridLayout(0, 1));
    private JTabbedPane tab = new JTabbedPane();

    private WelcomeTab welcomeTab = new WelcomeTab();

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

    /**
     * Constructs an empty <tt>Window</tt>.
     */
    public Window() {
        super("Consultation");
        
        // do not close app on consultation window close
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        mainPanel.setLayout(new BorderLayout());

        setSize(500, 600);

        tab.addTab("Info", welcomeTab);
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

    /**
     * Setting library tab array data
     * @param hm
     */
    public void setLibraryData(HashMap<Integer, Library> hm) {
        DefaultTableModel m = libTab.getModel();
        hm.forEach((i, l) -> {
            Object[] toAdd = {l.getId(), l.getName()};
            m.addRow(toAdd);
        });
        libTab.setModel(m);
    }

    /**
     * Setting document tab array data
     * @param docs
     */
    public void setDocData(HashMap<String, Document> docs) {
        setDocData(docs, docTab);
    }

    /**
     * Setting some ta tab document array data
     * @param docs
     * @param ta
     */
    public void setDocData(HashMap<String, Document> docs, TabbedArray ta) {
        DefaultTableModel m = ta.getModel();

        docs.forEach((s, d) -> {
            String ISBN = d instanceof Book ? ((Book) d).getISBN() : "";
            Object[] toAdd = {d.getClass().getSimpleName(), d.getEAN(), d.getFullTitle(), d.getDate(), d.getPublisher(), ISBN};
            m.addRow(toAdd);
        });
        ta.setModel(m);
    }


    /**
     * Setting user tab array data
     * @param hm
     */
    public void setUserData(HashMap<Integer, User> hm) {
        DefaultTableModel m = userTab.getModel();
        hm.forEach((i, u) -> {
            Object[] toAdd = {u.getId(), u.getName(), u.getSurname()};
            m.addRow(toAdd);
        });
        userTab.setModel(m);
    }

    /**
     * Setting author tab array data
     * @param hm
     */
    public void setAuthorData(HashMap<Integer, Author> hm) {
        setAuthorData(hm, authorTab);
    }

    /**
     * Setting some tab author's array data
     * @param hm
     * @param ta
     */
    public void setAuthorData(HashMap<Integer, Author> hm, TabbedArray ta) {
        DefaultTableModel m = ta.getModel();
        hm.forEach((i, auth) -> {
            Object[] toAdd = {auth.getId(), auth.getName(), auth.getSurname()};
            m.addRow(toAdd);
        });
        ta.setModel(m);
    }

    /**
     * Setting the research tab array data
     * @param type
     * @param strSearch
     * @param data
     */
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
                HashMap<String, Document> docEAN = new HashMap<String, Document>();

                ((Set<Map.Entry<String, Document>>) data).forEach(d -> {
                    docEAN.put(d.getKey(), d.getValue());
                });
                setDocData((HashMap<String, Document>) docEAN, searchTab);
                break;
            case "EAN":
                tab.setTitleAt(4, "Recherche - EAN : " + strSearch);
                searchTab.updateHeaders(docHeader);
                HashMap<String, Document> docISBN = new HashMap<String, Document>();

                ((Set<Map.Entry<String, Document>>) data).forEach(d -> {
                    docISBN.put(d.getKey(), d.getValue());
                });
                setDocData((HashMap<String, Document>) docISBN, searchTab);
                break;
            case "DATE":
                tab.setTitleAt(5, "Recherche - Date : " + strSearch);
                searchTab.updateHeaders(docHeader);
                HashMap<String, Document> docDATE = new HashMap<String, Document>();

                ((Set<Map.Entry<String, Document>>) data).forEach(d -> {
                    docDATE.put(d.getKey(), d.getValue());
                });
                setDocData((HashMap<String, Document>) docDATE, searchTab);
                break;
        }
    }
}
