package ui.generic;

import java.awt.GridLayout;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controllers.ControllerGeneric;

public class InsertGenericPanel<T> extends JPanel {

    private ControllerGeneric<T> controller = new ControllerGeneric<>();

    public InsertGenericPanel(Class<T> clazz) {

        setLayout(new GridLayout(0, 2));

        try {

            T obj = clazz.getDeclaredConstructor().newInstance();

            List<JTextField> fieldsInput = new ArrayList<>();
            List<Field> fields = Arrays.asList(clazz.getDeclaredFields());

            for (Field f : fields) {

                add(new JLabel(f.getName()));

                JTextField tf = new JTextField();
                fieldsInput.add(tf);

                add(tf);
            }

            JButton btn = new JButton("Save");

            btn.addActionListener(e -> {
                try {
                    int i = 0;
                    for (Field f : fields) {

                        String setterName =
                                "set" + Character.toUpperCase(f.getName().charAt(0))
                                + f.getName().substring(1);

                        Method setter = clazz.getMethod(setterName, f.getType());

                        Object value = convert(fieldsInput.get(i).getText(), f.getType());

                        setter.invoke(obj, value);

                        i++;
                    }

                    controller.save(obj);

                    JOptionPane.showMessageDialog(this, "Ajout réussi");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erreur: " + ex.getMessage());
                }
            });

            add(btn);

        } catch (Exception e) {
            add(new JLabel("Erreur: " + e.getMessage()));
        }
    }

    private Object convert(String value, Class<?> type) {

        if (type == int.class || type == Integer.class) {
            return Integer.parseInt(value);
        }

        if (type == double.class || type == Double.class) {
            return Double.parseDouble(value);
        }

        return value;
    }
}