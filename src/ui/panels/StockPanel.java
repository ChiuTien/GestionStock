package ui.panels;

import javax.swing.*;
import java.awt.*;

public class StockPanel extends JPanel {

    public StockPanel() {

        setLayout(new BorderLayout());

        add(new JLabel("Etat du Stock"), BorderLayout.NORTH);

        JTextArea area = new JTextArea();
        add(new JScrollPane(area), BorderLayout.CENTER);
    }
}