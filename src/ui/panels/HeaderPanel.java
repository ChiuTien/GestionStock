package ui.panels;

import ui.frames.MainFrame;

import javax.swing.*;
import java.awt.*;

public class HeaderPanel extends JPanel {

    public HeaderPanel(MainFrame frame) {

        setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton btnProduit = new JButton("Produits");
        JButton btnStock = new JButton("Stock");

        btnProduit.addActionListener(e -> frame.showPanel("produit"));
        btnStock.addActionListener(e -> frame.showPanel("stock"));

        add(btnProduit);
        add(btnStock);
    }
}