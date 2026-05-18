package ui.tables;

import javax.swing.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class GenericTableBuilder {

    public static <T> JTable build(List<T> data, Class<T> clazz) throws Exception {
        Field[] fields = clazz.getDeclaredFields();
        String[] columns = new String[fields.length];

        for (int i = 0; i < fields.length; i++) {
            columns[i] = fields[i].getName();
        }

        Object[][] rows = new Object[data.size()][fields.length];

        for (int i = 0; i < data.size(); i++) {
            T obj = data.get(i);
            for (int j = 0; j < fields.length; j++) {
                String getter =
                        "get" + Character.toUpperCase(fields[j].getName().charAt(0))
                        + fields[j].getName().substring(1);

                Method method = clazz.getMethod(getter);
                rows[i][j] = method.invoke(obj);
            }
        }

        return new JTable(rows, columns);
    }
}