package main;

import java.util.List;

import models.Mouvements_stock;
import models.Produits;
import models.StockDetail;
import repositories.Dao;

public class Main {
    public static void main(String[] args) throws Exception{
        System.out.println("On va faire des testes");
        Dao db = new Dao();
        StockDetail resultat = new StockDetail();
        Produits p = db.getById(Produits.class, 1);
        List<Mouvements_stock> mvnt = db.getAll(Mouvements_stock.class);
        for (Mouvements_stock mouvements_stock : mvnt) {
            System.out.println(mouvements_stock.getDate_mouvement());
        }
    }
}