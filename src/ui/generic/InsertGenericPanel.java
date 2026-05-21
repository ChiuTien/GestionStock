package ui.generic;

import controllers.ControllerGeneric;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InsertGenericPanel<T> extends JPanel {

    private ControllerGeneric<T> controller = new ControllerGeneric<>();

    public InsertGenericPanel(Class<T> clazz) {

        setLayout(new GridLayout(0, 2));

        List<JTextField> inputs = new ArrayList<>();
        List<Field> fields = List.of(clazz.getDeclaredFields());

        // ===== GENERATION FORM =====
        for (Field f : fields) {

            add(new JLabel(f.getName()));

            JTextField tf = new JTextField();
            inputs.add(tf);

            add(tf);
        }

        JButton saveBtn = new JButton("Save");

        saveBtn.addActionListener(e -> {

            try {

                // IMPORTANT : nouvel objet à chaque clic
                T obj = clazz.getDeclaredConstructor().newInstance();

                int i = 0;

                for (Field f : fields) {

                    String setterName =
                            "set" + Character.toUpperCase(f.getName().charAt(0))
                                    + f.getName().substring(1);

                    Method setter = clazz.getMethod(setterName, f.getType());

                    Object value = convert(inputs.get(i).getText(), f.getType());

                    setter.invoke(obj, value);

                    i++;
                }

                controller.save(obj);

                JOptionPane.showMessageDialog(this, "Ajout réussi");

                clearFields(inputs);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Erreur: " + ex.getMessage());
            }
        });

        add(saveBtn);
    }

    // ===================== CONVERSION ROBUSTE =====================
    private Object convert(String value, Class<?> type) {

        value = value.trim();

        if (value.isEmpty()) {
            throw new RuntimeException("Champ vide interdit");
        }

        if (type == int.class || type == Integer.class) {
            return Integer.parseInt(value);
        }

        if (type == double.class || type == Double.class) {
            return Double.parseDouble(value);
        }

        if (type == long.class || type == Long.class) {
            return Long.parseLong(value);
        }

        if (type == boolean.class || type == Boolean.class) {
            return Boolean.parseBoolean(value);
        }

        if (type == BigDecimal.class) {
            return new BigDecimal(value);
        }

        if (type == LocalDate.class) {
            return LocalDate.parse(value);
        }

        // fallback
        return value;
    }

    // ===================== CLEAR FORM =====================
    private void clearFields(List<JTextField> inputs) {
        for (JTextField tf : inputs) {
            tf.setText("");
        }
    }
}