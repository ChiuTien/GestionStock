package services;

import models.LotStock;
import models.Mouvements_stock;
import models.Produits;
import models.StockDetail;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StockService {

    // Services
    private MouvementService mouvementService = new MouvementService();

    private FIFOService fifo = new FIFOService();
    private LIFOService lifo = new LIFOService();
    private CUMPService cump = new CUMPService();


    // APPLICATION STRATEGIE
    public List<LotStock> appliquerStrategie(int id, List<LotStock> lots, Mouvements_stock mouvement) throws Exception {
        List<LotStock> result = new ArrayList<>();
        switch (id) {
            case 1:
                result = fifo.appliquer(lots, mouvement);
                break;
            case 2:
                result = lifo.appliquer(lots, mouvement);
                break;
            case 3:
                result = cump.appliquer(lots, mouvement);
                break;
            default:
                throw new Exception(
                        "Type de valorisation inconnu"
                );
        }
        return result;
    }


    // CONSTRUCTION STOCK
    public List<LotStock> getStock(Produits produit, LocalDate date) throws Exception {
        List<Mouvements_stock> mouvements_stocks = mouvementService.getAll();

        mouvements_stocks = mouvementService.filterDateAndProduit(
                        mouvements_stocks,
                        date,
                        produit
                );

        // Tri chronologique
        mouvements_stocks.sort(Comparator.comparing(Mouvements_stock::getDate_mouvement));

        List<LotStock> lots = new ArrayList<>();

        for (Mouvements_stock mouvement : mouvements_stocks) {
            if (mouvement.isEntree()) {
                lots.add(
                        new LotStock(
                                mouvement.getId(),
                                mouvement.getQuantite(),
                                mouvement.getDate_mouvement(),
                                mouvement.getPrix_unitaire()
                        )
                );
            }
            else {
                lots = appliquerStrategie(
                        produit.getType_id(),
                        lots,
                        mouvement
                );
            }
        }
        return lots;
    }

    // DETAIL STOCK
    public StockDetail getStockDetail(Produits produit, LocalDate date) throws Exception {
        List<LotStock> lots = getStock(produit, date);

        double quantiteTotale = 0;
        BigDecimal totalValeur = BigDecimal.ZERO;

        if (produit.getType_id() == 1||produit.getType_id() == 2) {
            for (LotStock lot : lots) {
                quantiteTotale += lot.getQuantite();
                BigDecimal valeur =
                        lot.getPrix_unitaire().multiply(
                                BigDecimal.valueOf(
                                        lot.getQuantite()
                                )
                        );
                totalValeur = totalValeur.add(valeur);
            }
        }
        else if (produit.getType_id() == 3) {
            List<Mouvements_stock> mouvements = mouvementService.getAll();
            mouvements = mouvementService.filterDateAndProduit(mouvements, date, produit);
            mouvements.sort(Comparator.comparing(Mouvements_stock::getDate_mouvement));

            BigDecimal stockQuantite = BigDecimal.ZERO;

            BigDecimal stockValeur = BigDecimal.ZERO;

            // Calcul réel CUMP
            for (Mouvements_stock m : mouvements) {
                BigDecimal qte = BigDecimal.valueOf(m.getQuantite());

                if (m.isEntree()) {
                    BigDecimal valeurEntree = m.getPrix_unitaire().multiply(qte);
                    stockValeur = stockValeur.add(valeurEntree);
                    stockQuantite = stockQuantite.add(qte);
                }

                // SORTIE
                else {
                    if (stockQuantite.compareTo(BigDecimal.ZERO) <= 0) {
                        throw new Exception(
                                "Stock insuffisant"
                        );
                    }
                    BigDecimal prixMoyen = stockValeur.divide(stockQuantite,6,RoundingMode.HALF_UP);
                    BigDecimal valeurSortie = prixMoyen.multiply(qte);
                    stockValeur = stockValeur.subtract(valeurSortie);
                    stockQuantite = stockQuantite.subtract(qte);
                }
            }

            quantiteTotale = stockQuantite.doubleValue();
            totalValeur = stockValeur.setScale(2,RoundingMode.HALF_UP);
        }

        // RESULTAT
        StockDetail detail = new StockDetail();
        detail.setProduit(produit);
        detail.setLotsRestants(lots);
        detail.setQuantiteTotale(quantiteTotale);
        detail.setValeurTotale(totalValeur);
        return detail;
    }
}