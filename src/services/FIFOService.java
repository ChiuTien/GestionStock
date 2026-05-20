package services;

import java.util.List;

import models.ConsommationLot;
import models.LotStock;
import models.Mouvements_stock;

public class FIFOService {

    // Constructeur
    public FIFOService() {}

    // Methode
    public List<LotStock> appliquer(List<LotStock> lots, Mouvements_stock sortie) throws Exception {
        double restant = sortie.getQuantite();

        for (LotStock lot : lots) {
            if (restant <= 0) {
                break;
            }
            if (lot.getQuantite() <= 0) {
                continue;
            }

            double pris = 0;

            // Consommation totale du lot
            if (lot.getQuantite() <= restant) {
                pris = lot.getQuantite();
                restant -= lot.getQuantite();
                lot.setQuantite(0);
            }

            // Consommation partielle
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