package et3.java.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TabbedArray extends JPanel {
    private JScrollPane scrollPane;
    private DefaultTableModel model;
    private JTable arr;
    private JLabel lastUpdate;
    private String lastUpdateText;

    TabbedArray(String[] headers) {
        this(headers, "Dernière mise à jour");
    }

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

    public DefaultTableModel getModel() {
        return model;
    }

    public void setModel(DefaultTableModel model) {
        this.model = model;
        arr.setModel(model);
        DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy HH:mm:ss");
        TimeZone.setDefault(TimeZone.getTimeZone("UTC+2"));
        lastUpdate.setText(this.lastUpdateText + dateFormat.format(new Date()));
    }

    public void updateHeaders(String[] headers) {
        String[][] emptyData = {};
        setModel(new DefaultTableModel(emptyData, headers));
    }
}
