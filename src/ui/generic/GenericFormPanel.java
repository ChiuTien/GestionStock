package ui.generic;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class GenericFormPanel<T> extends JPanel {

    private Class<T> clazz;
    private Map<String, JComponent> fieldsMap = new HashMap<>();

    public GenericFormPanel(Class<T> clazz) {
        this.clazz = clazz;
        setLayout(new GridLayout(0, 2, 10, 10));
        buildForm();
    }

    private void buildForm() {

        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {

            String name = field.getName();

            JLabel label = new JLabel(name);
            JComponent input;

            // SIMPLE TYPE HANDLING
            if (field.getType() == int.class ||
                field.getType() == Integer.class ||
                field.getType() == String.class) {

                input = new JTextField();

            } else if (field.getType() == boolean.class) {

                input = new JCheckBox();

            } else {
                input = new JTextField();
            }

            fieldsMap.put(name, input);

            add(label);
            add(input);
        }
    }

    public T getObject() throws Exception {

        T obj = clazz.getDeclaredConstructor().newInstance();

        for (Field field : clazz.getDeclaredFields()) {

            String name = field.getName();
            JComponent comp = fieldsMap.get(name);

            String setterName =
                    "set" +
                    Character.toUpperCase(name.charAt(0)) +
                    name.substring(1);

            Method setter = clazz.getMethod(setterName, field.getType());

            Object value = null;

            if (comp instanceof JTextField) {
                String text = ((JTextField) comp).getText();

                if (field.getType() == int.class ||
                    field.getType() == Integer.class) {
                    value = Integer.parseInt(text);
                } else {
                    value = text;
                }

            } else if (comp instanceof JCheckBox) {
                value = ((JCheckBox) comp).isSelected();
            }

            setter.invoke(obj, value);
        }

        return obj;
    }
}