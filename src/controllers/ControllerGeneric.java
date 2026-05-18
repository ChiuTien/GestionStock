package controllers;

import repositories.Dao;

import java.util.List;

public class ControllerGeneric<T> {

    private Dao dao = new Dao();

    public void save(T obj) throws Exception {
        dao.save(obj);
    }

    public void update(T obj) throws Exception {
        dao.update(obj);
    }

    public void delete(T obj) throws Exception {
        dao.delete(obj);
    }

    public List<T> getAll(Class<T> clazz) throws Exception {
        return dao.getAll(clazz);
    }

    public T getById(Class<T> clazz, Object id) throws Exception {
        return dao.getById(clazz, id);
    }
}