package ui.generic;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controllers.ControllerGeneric;
import ui.tables.GenericTableBuilder;

public class DeleteGenericPanel<T> extends JPanel {

    private ControllerGeneric<T> controller = new ControllerGeneric<>();

    private JTable table;
    private List<T> data;

    public DeleteGenericPanel(Class<T> clazz) {

        setLayout(new BorderLayout());

        try {
            data = controller.getAll(clazz);

            table = GenericTableBuilder.build(data, clazz);

            JButton deleteBtn = new JButton("Supprimer");

            deleteBtn.addActionListener(e -> deleteSelected(clazz));

            add(new JScrollPane(table), BorderLayout.CENTER);
            add(deleteBtn, BorderLayout.SOUTH);

        } catch (Exception e) {
            add(new JLabel("Erreur: " + e.getMessage()));
        }
    }

    private void deleteSelected(Class<T> clazz) {

        try {
            int row = table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Sélectionne une ligne");
                return;
            }

            T obj = data.get(row);

            controller.delete(obj);

            ((DefaultTableModel) table.getModel()).removeRow(row);

            JOptionPane.showMessageDialog(this, "Supprimé");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur delete: " + e.getMessage());
        }
    }
}