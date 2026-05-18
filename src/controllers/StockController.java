package controllers;

import models.Produits;
import models.StockDetail;
import services.ProduitService;
import services.StockService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StockController {

    private StockService stockService = new StockService();
    private ProduitService produitService = new ProduitService();

    // Tous les produits
    public List<StockDetail> getAllStocks(LocalDate date) throws Exception {

        List<StockDetail> details = new ArrayList<>();

        List<Produits> produits = produitService.getAll();

        for (Produits produit : produits) {

            StockDetail detail =
                    stockService.getStockDetail(produit, date);

            details.add(detail);
        }

        return details;
    }

    // Un seul produit
    public List<StockDetail> getStocksByProduit(
            Produits produit,
            LocalDate date
    ) throws Exception {

        List<StockDetail> details = new ArrayList<>();

        details.add(
                stockService.getStockDetail(produit, date)
        );

        return details;
    }
}