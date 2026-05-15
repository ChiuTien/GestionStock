package ui.panels;

import javax.swing.*;
import java.awt.*;

public class ProduitPanel extends JPanel {

    public ProduitPanel() {

        setLayout(new BorderLayout());

        JLabel title = new JLabel("Gestion Produits");
        add(title, BorderLayout.NORTH);

        JPanel form = new JPanel();
        form.add(new JLabel("Nom"));
        form.add(new JTextField(15));

        add(form, BorderLayout.CENTER);
    }
}