package et3.java.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TabbedArray extends JPanel {
    private JScrollPane scrollPane;
    private DefaultTableModel model;
    private JTable arr;

    TabbedArray(Object[] headers) {
        Object[][] emptyData = {};
        model = new DefaultTableModel(emptyData, headers);
        arr = new JTable(model);
        scrollPane = new JScrollPane(arr);
        this.add(scrollPane);
    }

    public DefaultTableModel getModel() {
        return model;
    }

    public void setModel(DefaultTableModel model) {
        this.model = model;
        arr.setModel(model);
    }
}
