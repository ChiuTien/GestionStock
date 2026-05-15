package services;

import models.LotStock;
import models.Mouvements_stock;
import models.Produits;
import models.StockDetail;

import java.math.BigDecimal;
import java.util.List;

public class StockService {

    private LotFactory factory = new LotFactory();

    private FIFOService fifo = new FIFOService();
    private LIFOService lifo = new LIFOService();
    private CUMPService cump = new CUMPService();

    public StockDetail getStock(
            Produits produit,
            List<Mouvements_stock> mouvements,
            int sortie
    ) throws Exception {

        // Construction des lots depuis les mouvements
        List<LotStock> lots =
                factory.buildLots(mouvements);

        // Application de la méthode
        List<LotStock> result;

        switch (produit.getType_id()) {

            case 1:
                result = fifo.appliquer(lots, sortie);
                break;

            case 2:
                result = lifo.appliquer(lots, sortie);
                break;

            case 3:
                result = cump.appliquer(lots, sortie);
                break;

            default:
                throw new Exception(
                        "Type de valorisation inconnu"
                );
        }

        // Calcul quantité + valeur totale
        int totalQte = 0;

        BigDecimal totalValeur =
                BigDecimal.ZERO;

        for (LotStock lot : result) {

            totalQte += lot.getQuantite();

            BigDecimal valeurLot =
                    lot.getPrix_unitaire()
                            .multiply(
                                    BigDecimal.valueOf(
                                            lot.getQuantite()
                                    )
                            );

            totalValeur =
                    totalValeur.add(valeurLot);
        }

        // Construction résultat final
        StockDetail detail =
                new StockDetail();

        detail.setProduit(produit.getNom_produit());
        detail.setLotsRestants(result);
        detail.setQuantiteTotale(totalQte);
        detail.setValeurTotale(totalValeur);

        return detail;
    }
}