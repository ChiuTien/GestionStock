package ui.generic;

import controllers.ControllerGeneric;
import ui.tables.GenericTableBuilder;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class UpdateGenericPanel<T> extends JPanel {

    private ControllerGeneric<T> controller = new ControllerGeneric<>();

    private JTable table;
    private JScrollPane scroll;

    private JPanel formPanel = new JPanel(new GridLayout(0, 2));
    private JButton updateBtn = new JButton("Modifier");

    private T selectedObject;

    public UpdateGenericPanel(Class<T> clazz) {

        setLayout(new BorderLayout());

        try {
            List<T> data = controller.getAll(clazz);

            if (data == null || data.isEmpty()) {
                add(new JLabel("Aucune donnée à modifier"), BorderLayout.CENTER);
                return;
            }

            table = GenericTableBuilder.build(data, clazz);

            scroll = new JScrollPane(table);

            add(scroll, BorderLayout.CENTER);

            JButton loadBtn = new JButton("Charger sélection");

            loadBtn.addActionListener(e -> loadSelected(clazz));

            updateBtn.addActionListener(e -> updateObject(clazz));

            JPanel bottom = new JPanel();

            bottom.add(loadBtn);
            bottom.add(updateBtn);

            add(bottom, BorderLayout.SOUTH);

            add(formPanel, BorderLayout.EAST);

        } catch (Exception e) {
            add(new JLabel("Erreur: " + e.getMessage()));
        }
    }

    private void loadSelected(Class<T> clazz) {

        try {

            int row = table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Sélectionne une ligne");
                return;
            }

            List<T> data = controller.getAll(clazz);

            selectedObject = data.get(row);

            formPanel.removeAll();

            Field[] fields = clazz.getDeclaredFields();

            for (Field f : fields) {

                JLabel label = new JLabel(f.getName());
                JTextField fieldInput = new JTextField();

                String getterName =
                        "get" + Character.toUpperCase(f.getName().charAt(0))
                        + f.getName().substring(1);

                Method getter = clazz.getMethod(getterName);

                Object value = getter.invoke(selectedObject);

                fieldInput.setText(value != null ? value.toString() : "");

                fieldInput.setName(f.getName());

                formPanel.add(label);
                formPanel.add(fieldInput);
            }

            formPanel.revalidate();
            formPanel.repaint();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur chargement: " + e.getMessage());
        }
    }

    private void updateObject(Class<T> clazz) {

        try {

            if (selectedObject == null) {
                JOptionPane.showMessageDialog(this, "Aucun objet sélectionné");
                return;
            }

            Component[] comps = formPanel.getComponents();

            Field[] fields = clazz.getDeclaredFields();

            int index = 0;

            for (Field f : fields) {

                if (comps[index + 1] instanceof JTextField tf) {

                    String setterName =
                            "set" + Character.toUpperCase(f.getName().charAt(0))
                            + f.getName().substring(1);

                    Method setter = clazz.getMethod(setterName, f.getType());

                    Object value = convert(tf.getText(), f.getType());

                    setter.invoke(selectedObject, value);
                }

                index += 2;
            }

            controller.update(selectedObject);

            JOptionPane.showMessageDialog(this, "Modification réussie");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur update: " + e.getMessage());
        }
    }

    private Object convert(String value, Class<?> type) {

        if (type == int.class || type == Integer.class) {
            return Integer.parseInt(value);
        }
        if (type == double.class || type == Double.class) {
            return Double.parseDouble(value);
        }
        if (type == java.math.BigDecimal.class) {
            return new java.math.BigDecimal(value);
        }

        return value;
    }
}