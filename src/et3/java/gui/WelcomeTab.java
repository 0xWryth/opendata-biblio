package et3.java.gui;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyledDocument;

public class WelcomeTab extends JPanel {
    private JTextPane textPane = new JTextPane();

    public WelcomeTab() {
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        Style defaut = textPane.getStyle("default");
        StyledDocument sDoc = (StyledDocument)textPane.getDocument();
        String s = "Lucas Briatte\n" +
                "Antonin Depreissat\n\n" +
                "Projet de JAVA réalisé lors de la première année de cycle ingénieur à Polytech Paris Saclay\n\n" +
                "Le projet a pour but de permettre la consultation au sein d'un réseau de : \n" +
                "- documents\n" +
                "- librairies\n" +
                "- d'auteurs\n" +
                "- d'utilisateurs\n\n" +
                "Les données peuvent être recherchée, par nom/surnom pour les auteurs, par ISBN ou EAN pour les " +
                "documents, ou par type et dates.\n\n" +
                "Les documents peuvent être empruntés et rendus, des opérations d'ajouts peuvent être effectués sur " +
                "le réseau.\nL'ensemble des opérations se font sur l'invite de commande de l'application.\n" +
                "La visualisation des données se fait quant à elle sur l'interface graphique.\n\n" +
                "Lien du projet sur github: https://github.com/adepreis/opendata-biblio";
        try {
            int pos = 0;
            sDoc.insertString(pos, s, defaut);
        } catch (BadLocationException e) { }
        textPane.setEditable(false);
        this.add(textPane);
    }
}
