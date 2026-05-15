package services;

import models.LotStock;
import models.Mouvements_stock;
import models.Produits;

import java.util.List;

public class StockService {

    private LotFactory factory = new LotFactory();

    private FIFOService fifo = new FIFOService();
    private LIFOService lifo = new LIFOService();
    private CUMPService cump = new CUMPService();

    public List<LotStock> getStock(
            Produits produit,
            List<Mouvements_stock> mouvements,
            int sortie
    ) throws Exception {

        List<LotStock> lots = factory.buildLots(mouvements);

        int type = produit.getType_id();

        switch (type) {

            case 1:
                return fifo.appliquer(lots, sortie);

            case 2:
                return lifo.appliquer(lots, sortie);

            case 3:
                return cump.appliquer(lots, sortie);

            default:
                throw new Exception("Type de valorisation inconnu");
        }
    }
}