package ui.generic;

import java.awt.BorderLayout;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import controllers.ControllerGeneric;

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

            Field[] fields = clazz.getDeclaredFields();

            String[] columns = new String[fields.length];

            for (int i = 0; i < fields.length; i++) {
                columns[i] = fields[i].getName();
            }

            Object[][] rows = new Object[data.size()][fields.length];

            for (int i = 0; i < data.size(); i++) {
                T obj = data.get(i);

                for (int j = 0; j < fields.length; j++) {

                    String getterName =
                            "get" + Character.toUpperCase(fields[j].getName().charAt(0))
                            + fields[j].getName().substring(1);

                    Method getter = clazz.getMethod(getterName);

                    rows[i][j] = getter.invoke(obj);
                }
            }

            JTable table = new JTable(rows, columns);

            add(new JScrollPane(table), BorderLayout.CENTER);

        } catch (Exception e) {
            add(new JLabel("Erreur: " + e.getMessage()));
        }
    }
}