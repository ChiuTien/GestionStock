package repositories;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import database.connection.DatabaseConnexion;

public class Dao {
    //Constructeur
    public Dao() {}

    //Nom de table
    private String getNameTable(Class<?> type) {
        String table = type.getSimpleName();
        return Character.toLowerCase(table.charAt(0)) + table.substring(1);
    }

    //Reglage du type (lecture/ecriture)
    private Object getResultValue(ResultSet rs, String column, Class<?> type) throws Exception {
        if (type == int.class || type == Integer.class) {
            return rs.getInt(column);
        } else if (type == double.class || type == Double.class) {
            return rs.getDouble(column);
        } else if (type == String.class) {
            return rs.getString(column);
        } else if (type == java.math.BigDecimal.class) {
            return rs.getBigDecimal(column);
        } else if (type == java.time.LocalDate.class) {
            java.sql.Date sqlDate = rs.getDate(column);
            if(sqlDate != null) {
                return sqlDate.toLocalDate();
            }
        }
        return rs.getObject(column);
    }
    private void setStatementValue(PreparedStatement ps, int index, Object value, Class<?> type) throws Exception {
        if(value == null) {
            ps.setObject(index, null);
            return;
        }
        if (type == int.class || type == Integer.class) {
            ps.setInt(index, (Integer) value);
        } else if (type == double.class || type == Double.class) {
            ps.setDouble(index, (Double) value);
        } else if (type == String.class) {
            ps.setString(index, (String) value);
        } else if (type == java.math.BigDecimal.class) {
            ps.setBigDecimal(index, (java.math.BigDecimal) value);
        } else if (type == java.time.LocalDate.class) {
            ps.setDate(
                index,
                java.sql.Date.valueOf((java.time.LocalDate)value)
            );
        } else {
            ps.setObject(index, value);
        }
}

    //Save
    public void save(Object obj) throws Exception {
        Connection co = null;
        try {
            co = DatabaseConnexion.getConnection();
            save(obj,co);
        } catch (Exception e) {
            throw e;
        } finally {
            if(co!=null) {co.close();}
        }
    }
    public void save(Object obj,Connection co) throws Exception {
        Class<?> clazz = obj.getClass();
        String table = getNameTable(clazz);

        Field[] fields = clazz.getDeclaredFields();
        
        List<Field> columns = new ArrayList<>();

        for (Field f : fields) {
            if(!f.getName().equalsIgnoreCase("id")) {
                columns.add(f);
            }
        }
        
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ").append(table).append(" (");

        for(int i=0; i<columns.size(); i++) {
            sql.append(columns.get(i).getName());
            if(i<columns.size()-1) {
                sql.append(", ");
            }
        }

        sql.append(") VALUES (");

        for(int i=0; i<columns.size(); i++) {
            sql.append("?");
            if(i<columns.size()-1) {
                sql.append(", ");
            }
        }

        sql.append(")");

        PreparedStatement ps = null;
        try {
            ps = co.prepareStatement(sql.toString());
            for (int i=0; i<columns.size(); i++) {
                Field f = columns.get(i);
                String getterName = 
                    "get"+Character.toUpperCase(f.getName().charAt(0))
                        +f.getName().substring(1);

                Method getter = clazz.getMethod(getterName);
                Object value = getter.invoke(obj);
                setStatementValue(ps, i+1, value, f.getType());
            }
            ps.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            if(ps!=null) {ps.close();}
        }
    }

    //delete
    public void delete(Object obj) throws Exception {
        Connection co = null;
        try {
            co = DatabaseConnexion.getConnection();
            delete(obj,co);
        } catch (Exception e) {
            throw e;
        } finally {
            if(co!=null) {co.close();}
        }
    }
    public void delete(Object obj,Connection co) throws Exception {
        Class<?> clazz = obj.getClass();
        String table = getNameTable(clazz);

        Field idField = clazz.getDeclaredField("id");

        String getterName = 
                "get"+ Character.toUpperCase(idField.getName().charAt(0))
                +idField.getName().substring(1);

        Method getter = clazz.getMethod(getterName);

        String sql = "DELETE FROM "+ table +" WHERE id=?";

        PreparedStatement ps = null;
        try {
            ps = co.prepareStatement(sql);
            ps.setObject(1, getter.invoke(obj));
            ps.executeUpdate();
        } catch (Exception e) {
            throw e; 
        } finally {
            if(ps!=null){ps.close();}
        }
    }

    //update
    public void update(Object obj) throws Exception {
        Connection co = null;
        try {
            co = DatabaseConnexion.getConnection();
            update(obj,co);
        } catch (Exception e) {
            throw e;
        } finally {
            if(co!=null) {co.close();}
        }
    }
    public void update(Object obj, Connection co) throws Exception {
        Class<?> clazz = obj.getClass();
        String table = getNameTable(clazz);

        Field[] fields = clazz.getDeclaredFields();

        List<Field> columns = new ArrayList<>();
        Field idField = null;

        for (Field f : fields) {
            if(f.getName().equalsIgnoreCase("id")) {
                idField = f;
            } else {
                columns.add(f);
            }
        }

        StringBuilder sql = new StringBuilder("UPDATE ");
        sql.append(table).append(" SET ");

        for(int i=0; i<columns.size(); i++) {
            sql.append(columns.get(i).getName()).append(" = ?");
            if(i<columns.size()-1) {
                sql.append(", ");
            }
        }

        sql.append(" WHERE id = ?");

        PreparedStatement ps = null;
        try {
            ps = co.prepareStatement(sql.toString());

            int index = 1;

            for (Field f : columns) {
                String getterName = 
                    "get"+Character.toUpperCase(f.getName().charAt(0))
                    +f.getName().substring(1);

                Method getter = clazz.getMethod(getterName);
                Object value = getter.invoke(obj);
                setStatementValue(ps, index++, value, f.getType());
            }

            String idGetterName = 
                "get"+ Character.toUpperCase(idField.getName().charAt(0))
                + idField.getName().substring(1);

            Method idGetter = clazz.getMethod(idGetterName);

            ps.setObject(index, idGetter.invoke(obj));

            ps.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            if(ps!=null) {ps.close();}
        }
    }

    //getAll
    public <T> List<T> getAll(Class<T> clazz) throws Exception {
        Connection co = null;
        List<T> list = new ArrayList<>();
        try {
            co = DatabaseConnexion.getConnection();
            list = getAll(clazz, co);
        } catch (Exception e) {
            throw e;
        } finally {
            if(co!=null) {co.close();}
        }
        return list;
    }
    public <T> List<T> getAll(Class<T> clazz,Connection co) throws Exception {
        String table = getNameTable(clazz);

        String sql = "SELECT * FROM "+table;

        List<T> list = new ArrayList<>();

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = co.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                T obj = clazz.getDeclaredConstructor().newInstance();

                for (Field f : clazz.getDeclaredFields()) {
                    String setterName = 
                        "set"+Character.toUpperCase(f.getName().charAt(0))
                        +f.getName().substring(1);

                    Method setter = clazz.getMethod(setterName,f.getType());

                    Object value = getResultValue(rs, f.getName(), f.getType());

                    setter.invoke(obj, value);
                }
                list.add(obj);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if(rs!=null){rs.close();}
            if(ps!=null){ps.close();}
        }
        return list;
    }

    //getById
    public <T> T getById(Class<T> clazz, Object id) throws Exception {
        Connection co = null;
        T obj = null;
        try {
            co = DatabaseConnexion.getConnection();
            obj = getById(clazz, id, co);
        } catch (Exception e) {
            throw e;
        } finally {
            if(co!=null) {co.close();}
        }
        return obj;
    }
    public <T> T getById(Class<T> clazz, Object id, Connection co) throws Exception {
        String table = getNameTable(clazz);

        String sql = "SELECT * FROM "+table+" WHERE id = ?";

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = co.prepareStatement(sql);
            ps.setObject(1, id);
            rs = ps.executeQuery();
            if(rs.next()) {
                T obj = clazz.getDeclaredConstructor().newInstance();

                for (Field f : clazz.getDeclaredFields()) {
                    String setterName = 
                        "set"+Character.toUpperCase(f.getName().charAt(0))
                        +f.getName().substring(1);

                    Method setter = clazz.getMethod(setterName, f.getType());

                    Object value = getResultValue(rs, f.getName(), f.getType());

                    setter.invoke(obj, value);
                }
                return obj;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if(rs!=null){rs.close();}   
            if(ps!=null){ps.close();}
        }
        return null;
    }
}
