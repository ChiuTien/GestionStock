package main;

import models.Mouvements_stock;
import repositories.Dao;

public class Main {
    public static void main(String[] args) throws Exception{
        Dao dao = new Dao();
        Mouvements_stock mvn = new Mouvements_stock();
        //dao.update(mvn);
    }
}