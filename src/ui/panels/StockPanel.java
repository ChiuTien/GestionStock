package ui.panels;

import controllers.StockController;
import models.LotStock;
import models.Produits;
import models.StockDetail;
import services.ProduitService;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.List;

public class StockPanel extends JPanel {

    private JComboBox<Object> produitBox;

    private JTextField dateField = new JTextField(LocalDate.now().toString());

    private JButton filterBtn = new JButton("Filtrer");

    private JTable stockTable;
    private JTable detailTable;

    private List<StockDetail> currentStocks;

    private StockController controller = new StockController();

    private ProduitService produitService = new ProduitService();

    private JPanel centerPanel;

    public StockPanel() throws Exception {
        setLayout(new BorderLayout());
        initFilters();
        initCenter();
        loadStocks(null, LocalDate.now());
    }

    //INITIALISATION
    private void initCenter() {
        centerPanel = new JPanel(new GridLayout(2,1));

        stockTable = new JTable();
        detailTable = new JTable();

        centerPanel.add(new JScrollPane(stockTable));
        centerPanel.add(new JScrollPane(detailTable));

        stockTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = stockTable.getSelectedRow();
                if (row != -1) {
                    StockDetail detail = currentStocks.get(row);
                    buildDetailTable(detail.getLotsRestants());
                }
            }
        });
        add(centerPanel, BorderLayout.CENTER);
    }   

    // FILTRES
    private void initFilters() throws Exception {
        JPanel top = new JPanel();
        produitBox = new JComboBox<>();
        produitBox.addItem("Tous");

        try {
            List<Produits> produits = produitService.getAll();
            for (Produits p : produits) {
                produitBox.addItem(p);
            }
        } catch (Exception e) {
            throw e;
        }
        filterBtn.addActionListener(e -> applyFilter());

        top.add(new JLabel("Produit"));
        top.add(produitBox);

        top.add(new JLabel("Date"));
        top.add(dateField);

        top.add(filterBtn);

        add(top, BorderLayout.NORTH);
    }

    // CHARGEMENT STOCKS
    private void loadStocks(Produits produit, LocalDate date) throws Exception {

        try {
            if (produit == null) {
                currentStocks = controller.getAllStocks(date);
            } else {
                currentStocks = controller.getStocksByProduit(produit,date);
            }
            buildStockTable();
        } catch (Exception e) {
            throw e;
        }
    }

    // TABLE PRINCIPALE
    private void buildStockTable() {

        String[] cols = {
                "Produit",
                "Quantité",
                "Valeur"
        };

        Object[][] rows = new Object[currentStocks.size()][3];

        for (int i = 0; i < currentStocks.size(); i++) {
            StockDetail s = currentStocks.get(i);

            rows[i][0] = s.getProduit().getNom_produit();
            rows[i][1] = s.getQuantiteTotale();
            rows[i][2] = s.getValeurTotale();
        }

        stockTable.setModel(new DefaultTableModel(rows,cols));
    }

    // TABLE DETAILS
    private void buildDetailTable(List<LotStock> lots) {

        String[] cols = {
                "Mouvement",
                "Quantité",
                "Prix Unitaire"
        };

        Object[][] rows = new Object[lots.size()][3];

        for (int i = 0; i < lots.size(); i++) {
            LotStock lot = lots.get(i);

            rows[i][0] = lot.getMouvement_id();
            rows[i][1] = lot.getQuantite();
            rows[i][2] = lot.getPrix_unitaire();
        }

        detailTable.setModel(new DefaultTableModel(rows,cols));
    }

    // FILTRE
    private void applyFilter() {

        try {
            Object selected = produitBox.getSelectedItem();

            Produits produit = null;

            if (selected instanceof Produits) {
                produit = (Produits) selected;
            }

            LocalDate date = LocalDate.parse(dateField.getText());

            loadStocks(produit, date);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Erreur filtre : "+ e.getMessage());
        }
    }
}