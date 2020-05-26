package et3.java.gui;

import et3.java.model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Window extends JFrame {
    private JPanel mainPanel = new JPanel(new GridLayout(0, 1));
    private JTabbedPane tab = new JTabbedPane();

    // ------------
    Object[] docHeader = {"Type", "EAN", "Title", "Date", "Publisher", "ISBN"};
    TabbedArray docTab =  new TabbedArray(docHeader);

    // ------------
    Object[] libraryHeader = {"Id", "Library"};
    TabbedArray libTab =  new TabbedArray(libraryHeader);

    // ------------
    Object[] authorArray = {"Id", "Name", "Surname"};
    TabbedArray authorTab =  new TabbedArray(authorArray);

    // ------------
    Object[] userHeader = {"Id", "Name", "Surname"};
    TabbedArray userTab =  new TabbedArray(userHeader);

    public Window() {
        super("Consultation");
        
        // do not close app on consultation window close
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        mainPanel.setLayout(new BorderLayout());

        setSize(500, 600);

        tab.addTab("Library", libTab);
        tab.addTab("Documents", docTab);
        tab.addTab("Author", authorTab);
        tab.addTab("Users", userTab);
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
        DefaultTableModel m = docTab.getModel();
        for (Document doc : docs) {
            String ISBN = doc instanceof Book ? ((Book) doc).getISBN() : "";
            Object[] toAdd = {doc.getClass().getSimpleName(), doc.getEAN(), doc.getTitle(), doc.getDate(), doc.getPublisher(), ISBN};
            m.addRow(toAdd);
        }
        docTab.setModel(m);
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
        DefaultTableModel m = authorTab.getModel();
        hm.forEach((i, auth) -> {
            Object[] toAdd = {auth.getId(), auth.getName(), auth.getSurname()};
            m.addRow(toAdd);
        });
        authorTab.setModel(m);
    }
}
