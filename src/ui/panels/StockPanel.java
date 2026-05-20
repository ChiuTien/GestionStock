package ui.panels;

import controllers.StockController;
import models.ConsommationLot;
import models.LotStock;
import models.Produits;
import models.StockDetail;
import services.ProduitService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.List;

public class StockPanel extends JPanel {

    // ===== FILTRES =====
    private JComboBox<Object> produitBox;
    private JTextField dateField = new JTextField(LocalDate.now().toString());
    private JButton filterBtn = new JButton("Filtrer");

    // ===== TABLES =====
    private JTable stockTable;
    private JTable lotTable;
    private JTable consoTable;

    // ===== DATA =====
    private List<StockDetail> currentStocks;
    private List<LotStock> currentLots;

    // ===== SERVICES =====
    private StockController controller = new StockController();
    private ProduitService produitService = new ProduitService();

    private JPanel centerPanel;

    public StockPanel() throws Exception {
        setLayout(new BorderLayout());

        initFilters();
        initCenter();

        loadStocks(null, LocalDate.now());
    }

    // ===================== CENTER =====================
    private void initCenter() {

        centerPanel = new JPanel(new GridLayout(3, 1));

        stockTable = new JTable();
        lotTable = new JTable();
        consoTable = new JTable();

        centerPanel.add(new JScrollPane(stockTable));
        centerPanel.add(new JScrollPane(lotTable));
        centerPanel.add(new JScrollPane(consoTable));

        add(centerPanel, BorderLayout.CENTER);

        // ===== CLICK STOCK → LOTS =====
        stockTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = stockTable.getSelectedRow();

                if (row != -1) {
                    StockDetail detail = currentStocks.get(row);
                    buildLotTable(detail.getLotsRestants());
                }
            }
        });

        // ===== CLICK LOT → CONSOMMATIONS =====
        lotTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = lotTable.getSelectedRow();

                if (row != -1 && currentLots != null) {
                    LotStock lot = currentLots.get(row);
                    buildConsommationTable(lot.getConsommation());
                }
            }
        });
    }

    // ===================== FILTERS =====================
    private void initFilters() throws Exception {

        JPanel top = new JPanel();

        produitBox = new JComboBox<>();
        produitBox.addItem("Tous");

        List<Produits> produits = produitService.getAll();
        for (Produits p : produits) {
            produitBox.addItem(p);
        }

        filterBtn.addActionListener(e -> applyFilter());

        top.add(new JLabel("Produit"));
        top.add(produitBox);

        top.add(new JLabel("Date"));
        top.add(dateField);

        top.add(filterBtn);

        add(top, BorderLayout.NORTH);
    }

    // ===================== LOAD STOCK =====================
    private void loadStocks(Produits produit, LocalDate date) throws Exception {

        if (produit == null) {
            currentStocks = controller.getAllStocks(date);
        } else {
            currentStocks = controller.getStocksByProduit(produit, date);
        }

        buildStockTable();
    }

    // ===================== STOCK TABLE =====================
    private void buildStockTable() {

        String[] cols = {"Produit", "Quantité", "Valeur"};
        Object[][] rows = new Object[currentStocks.size()][3];

        for (int i = 0; i < currentStocks.size(); i++) {

            StockDetail s = currentStocks.get(i);

            rows[i][0] = s.getProduit().getNom_produit();
            rows[i][1] = s.getQuantiteTotale();
            rows[i][2] = s.getValeurTotale();
        }

        stockTable.setModel(new DefaultTableModel(rows, cols));
    }

    // ===================== LOT TABLE =====================
    private void buildLotTable(List<LotStock> lots) {

        currentLots = lots;

        String[] cols = {"Mouvement", "Quantité", "Prix Unitaire"};
        Object[][] rows = new Object[lots.size()][3];

        for (int i = 0; i < lots.size(); i++) {

            LotStock lot = lots.get(i);

            rows[i][0] = lot.getMouvement_id();
            rows[i][1] = lot.getQuantite();
            rows[i][2] = lot.getPrix_unitaire();
        }

        lotTable.setModel(new DefaultTableModel(rows, cols));
    }

    // ===================== CONSOMMATION TABLE =====================
    private void buildConsommationTable(List<ConsommationLot> conso) {

        String[] cols = {
                "Sortie",
                "Date sortie",
                "Entrée",
                "Date entrée",
                "Quantité sortie",
                "Prix",
                "Valeur"
        };

        Object[][] rows = new Object[conso.size()][7];

        for (int i = 0; i < conso.size(); i++) {

            ConsommationLot c = conso.get(i);

            rows[i][0] = c.getSortieId();
            rows[i][1] = c.getDateSortie();
            rows[i][2] = c.getEntreeId();
            rows[i][3] = c.getDateEntree();
            rows[i][4] = c.getQuantite();
            rows[i][5] = c.getPrixUnitaire();
            rows[i][6] = c.getValeur();
        }

        consoTable.setModel(new DefaultTableModel(rows, cols));
    }

    // ===================== FILTER ACTION =====================
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
            JOptionPane.showMessageDialog(this,
                    "Erreur filtre : " + e.getMessage());
        }
    }
}