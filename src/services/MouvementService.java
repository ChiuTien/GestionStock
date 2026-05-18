package services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import models.Mouvements_stock;
import models.Produits;
import repositories.Dao;

public class MouvementService {
    private Dao dao = new Dao();
    //Constructeur
    public MouvementService() {}

    //Methode
    public List<Mouvements_stock> getAll() throws Exception {
        return dao.getAll(Mouvements_stock.class);
    }   
    public List<Mouvements_stock> filterDateAndProduit(List<Mouvements_stock> mouvements, LocalDate date, Produits produit) {
        List<Mouvements_stock> mouvements_stocks = new ArrayList<>();
        for (Mouvements_stock m : mouvements) {
            if(m.getProduit_id() == produit.getId()
            && !m.getDate_mouvement().isAfter(date)) {
                mouvements_stocks.add(m);
            }
        }
        return mouvements_stocks;
    }    
}
