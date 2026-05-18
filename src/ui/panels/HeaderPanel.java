package ui.panels;

import models.Mouvements_stock;
import models.Produits;
import models.Types;
import ui.frames.MainFrame;
import ui.generic.DeleteGenericPanel;
import ui.generic.InsertGenericPanel;
import ui.generic.ListGenericPanel;
import ui.generic.UpdateGenericPanel;

import javax.swing.*;
import java.awt.*;

public class HeaderPanel extends JPanel {

    private MainFrame frame;

    private JMenuBar bar = new JMenuBar();

    // MENUS
    private JMenu produitMenu = new JMenu("Produits");
    private JMenu mouvementMenu = new JMenu("Mouvements");
    private JMenu typeMenu = new JMenu("Types");
    private JMenu etatMenu = new JMenu("Etats");

    // PRODUITS
    private JMenuItem produitInsert = new JMenuItem("Insertion");
    private JMenuItem produitListe = new JMenuItem("Liste");
    private JMenuItem produitUpdate = new JMenuItem("Modification");
    private JMenuItem produitDelete = new JMenuItem("Suppression");

    // MOUVEMENTS
    private JMenuItem mouvementInsert = new JMenuItem("Insertion");
    private JMenuItem mouvementListe = new JMenuItem("Liste");
    private JMenuItem mouvementUpdate = new JMenuItem("Modification");
    private JMenuItem mouvementDelete = new JMenuItem("Suppression");

    // TYPES
    private JMenuItem typeInsert = new JMenuItem("Insertion");
    private JMenuItem typeListe = new JMenuItem("Liste");
    private JMenuItem typeUpdate = new JMenuItem("Modification");
    private JMenuItem typeDelete = new JMenuItem("Suppression");

    // ETAT STOCK
    private JMenuItem etatStock = new JMenuItem("Etat du stock");

    public HeaderPanel(MainFrame frame) throws Exception {
        this.frame = frame;

        setLayout(new BorderLayout());

        setPreferredSize(new Dimension(150, 35));

        buildMenu();

        addListeners();

        add(bar, BorderLayout.CENTER);
    }

    private void buildMenu() {
        // PRODUITS
        produitMenu.add(produitInsert);
        produitMenu.add(produitListe);
        produitMenu.add(produitUpdate);
        produitMenu.add(produitDelete);

        // MOUVEMENTS
        mouvementMenu.add(mouvementInsert);
        mouvementMenu.add(mouvementListe);
        mouvementMenu.add(mouvementUpdate);
        mouvementMenu.add(mouvementDelete);

        // TYPES
        typeMenu.add(typeInsert);
        typeMenu.add(typeListe);
        typeMenu.add(typeUpdate);
        typeMenu.add(typeDelete);

        // ETAT
        etatMenu.add(etatStock);

        // AJOUT BAR
        bar.add(produitMenu);
        bar.add(mouvementMenu);
        bar.add(typeMenu);
        bar.add(etatMenu);
    }

    private void addListeners() throws Exception {
        // PRODUITS
        produitInsert.addActionListener(e -> {
            frame.setContentPanel(
                new InsertGenericPanel<>(Produits.class)
            );
        });
        produitListe.addActionListener(e -> {
            frame.setContentPanel(
                new ListGenericPanel<>(Produits.class)
            );
        });
        produitUpdate.addActionListener(e -> {
            frame.setContentPanel(
                new UpdateGenericPanel<>(Produits.class)
            );
        });
        produitDelete.addActionListener(e -> {
            frame.setContentPanel(
                new DeleteGenericPanel<>(Produits.class)
            );
        });

        // MOUVEMENTS
        mouvementInsert.addActionListener(e -> {
            frame.setContentPanel(
                new InsertGenericPanel<>(Mouvements_stock.class)
            );
        });
        mouvementListe.addActionListener(e -> {
            frame.setContentPanel(
                new ListGenericPanel<>(Mouvements_stock.class)
            );
        });
        mouvementUpdate.addActionListener(e -> {
            frame.setContentPanel(
                new UpdateGenericPanel<>(Mouvements_stock.class)
            );
        });
        mouvementDelete.addActionListener(e -> {
            frame.setContentPanel(
                new DeleteGenericPanel<>(Mouvements_stock.class)
            );
        });

        // TYPES
        typeInsert.addActionListener(e -> {
            frame.setContentPanel(
                new InsertGenericPanel<>(Types.class)
            );
        });
        typeListe.addActionListener(e -> {
            frame.setContentPanel(
                new ListGenericPanel<>(Types.class)
            );
        });
        typeUpdate.addActionListener(e -> {
            frame.setContentPanel(
                new UpdateGenericPanel<>(Types.class)
            );
        });
        typeDelete.addActionListener(e -> {
            frame.setContentPanel(
                new DeleteGenericPanel<>(Types.class)
            );
        });

        // ETAT STOCK
        etatStock.addActionListener(e -> {
            try {
                frame.setContentPanel(
                    new StockPanel()
                );
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
    }
}