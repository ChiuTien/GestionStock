package ui.generic;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import controllers.ControllerGeneric;

public class DeleteGenericPanel<T> extends JPanel {

    private ControllerGeneric<T> controller = new ControllerGeneric<>();

    public DeleteGenericPanel(Class<T> clazz) {

        setLayout(new BorderLayout());

        try {

            List<T> data = controller.getAll(clazz);

            DefaultListModel<T> model = new DefaultListModel<>();

            for (T obj : data) {
                model.addElement(obj);
            }

            JList<T> list = new JList<>(model);

            JButton delete = new JButton("Supprimer");

            delete.addActionListener(e -> {
                try {
                    T selected = list.getSelectedValue();

                    controller.delete(selected);

                    model.removeElement(selected);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });

            add(new JScrollPane(list), BorderLayout.CENTER);
            add(delete, BorderLayout.SOUTH);

        } catch (Exception e) {
            add(new JLabel("Erreur: " + e.getMessage()));
        }
    }
}