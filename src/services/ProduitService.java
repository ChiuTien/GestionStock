package services;

import java.util.List;

import models.Produits;
import repositories.Dao;

public class ProduitService {
    Dao dao = new Dao();

    public List<Produits> getAll() throws Exception{
        return dao.getAll(Produits.class);
    }
}
