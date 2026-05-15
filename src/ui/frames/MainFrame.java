package ui.frames;

import javax.swing.*;
import java.awt.*;

import ui.panels.HeaderPanel;
import ui.panels.ProduitPanel;
import ui.panels.StockPanel;

public class MainFrame extends JFrame {

    private JPanel contentPanel;

    public MainFrame() {

        setTitle("Gestion Stock");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // HEADER
        HeaderPanel header = new HeaderPanel(this);
        add(header, BorderLayout.NORTH);

        // CONTENT AREA
        contentPanel = new JPanel(new CardLayout());

        contentPanel.add(new ProduitPanel(), "produit");
        contentPanel.add(new StockPanel(), "stock");

        add(contentPanel, BorderLayout.CENTER);
    }

    public void showPanel(String name) {
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, name);
    }
}