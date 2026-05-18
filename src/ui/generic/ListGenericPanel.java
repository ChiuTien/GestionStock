package ui.generic;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import controllers.ControllerGeneric;
import ui.tables.GenericTableBuilder;

public class ListGenericPanel<T> extends JPanel {

    private ControllerGeneric<T> controller = new ControllerGeneric<>();

    public ListGenericPanel(Class<T> clazz) {

        setLayout(new BorderLayout());

        try {
            List<T> data = controller.getAll(clazz);

            if (data == null || data.isEmpty()) {
                add(new JLabel("Aucune donnée"), BorderLayout.CENTER);
                return;
            }

            JTable table = GenericTableBuilder.build(data, clazz);

            add(new JScrollPane(table), BorderLayout.CENTER);

        } catch (Exception e) {
            add(new JLabel("Erreur: " + e.getMessage()));
        }
    }
}