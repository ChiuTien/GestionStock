package main;

import java.util.List;

import models.LotStock;
import models.Mouvements_stock;
import models.Produits;
import models.StockDetail;
import repositories.Dao;
import services.StockService;

public class Main {
    public static void main(String[] args) throws Exception{
        System.out.println("On va faire des testes");
        Dao db = new Dao();
        StockService service = new StockService();
        Produits p = db.getById(Produits.class, 1);
        List<Mouvements_stock> mvnt = db.getAll(Mouvements_stock.class);
        StockDetail resultat = service.getStock(p, mvnt, 8);
        for (LotStock lotStock : resultat.getLotsRestants()) {
            System.out.println(lotStock.getQuantite()+" => "+lotStock.getPrix_unitaire());
        }
        System.out.println(resultat.getQuantiteTotale()+" => "+resultat.getValeurTotale());
    }
}