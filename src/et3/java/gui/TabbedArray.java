package et3.java.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * TabbedArray is the class for all tabs of main Window.
 * A TabbedArray object encapsulates a pane, some controls and labels...
 */
public class TabbedArray extends JPanel {
    private JScrollPane scrollPane;
    private DefaultTableModel model;
    private JTable arr;
    private JLabel lastUpdate;
    private String lastUpdateText;

    // Constructors

    /**
     * Generating a TabbedArray with an array having some headers.
     * @param headers
     */
    TabbedArray(String[] headers) {
        this(headers, "Dernière mise à jour");
    }

    /**
     * Generating a TabbedArray with an array having some headers and a custom update text.
     * @param headers
     * @param lastUpdateText
     */
    TabbedArray(String[] headers, String lastUpdateText) {
        String[][] emptyData = {};
        model = new DefaultTableModel(emptyData, headers);
        arr = new JTable(model);
        scrollPane = new JScrollPane(arr);
        this.lastUpdateText = lastUpdateText + " : ";
        lastUpdate = new JLabel(this.lastUpdateText + "Jamais");
        this.add(lastUpdate);
        this.add(scrollPane);
    }

    // Getter

    /**
     * Getting the TabbedArray array model
     */
    public DefaultTableModel getModel() {
        return model;
    }

    // Setter

    /**
     * Setting a new model for the TabbedArray array
     * @param model
     */
    public void setModel(DefaultTableModel model) {
        this.model = model;
        arr.setModel(model);
        DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy HH:mm:ss");
        TimeZone.setDefault(TimeZone.getTimeZone("UTC+2"));
        lastUpdate.setText(this.lastUpdateText + dateFormat.format(new Date()));
    }

    // Method

    /**
     * Updating current array's headers
     * @param headers
     */
    public void updateHeaders(String[] headers) {
        String[][] emptyData = {};
        setModel(new DefaultTableModel(emptyData, headers));
    }
}
