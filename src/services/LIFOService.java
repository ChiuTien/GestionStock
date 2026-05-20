package services;

import java.util.List;

import models.ConsommationLot;
import models.LotStock;
import models.Mouvements_stock;

public class LIFOService {

    // Constructeur
    public LIFOService() {}

    // Methode
    public List<LotStock> appliquer(List<LotStock> lots, Mouvements_stock sortie) throws Exception {
        double restant = sortie.getQuantite();

        for (int i = lots.size() - 1; i >= 0; i--) {
            if (restant <= 0) {
                break;
            }

            LotStock lot = lots.get(i);
            if (lot.getQuantite() <= 0) {
                continue;
            }

            double pris = 0;

            // Lot totalement consommé
            if (lot.getQuantite() <= restant) {
                pris = lot.getQuantite();
                restant -= lot.getQuantite();
                lot.setQuantite(0);
            }

            // Lot partiellement consommé
            else {
                pris = restant;
                lot.setQuantite(lot.getQuantite() - restant);
                restant = 0;
            }

            // Historique consommation
            lot.getConsommation().add(
                    new ConsommationLot(
                            sortie.getId(),
                            sortie.getDate_mouvement(),
                            sortie.getQuantite(),
                            lot.getMouvement_id(),
                            lot.getDateEntree(),
                            pris,
                            lot.getPrix_unitaire()
                    )
            );
        }
        if (restant > 0) {
            throw new Exception(
                    "Stock insuffisant"
            );
        }
        return lots;
    }
}