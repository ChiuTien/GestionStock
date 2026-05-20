package services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import models.ConsommationLot;
import models.LotStock;
import models.Mouvements_stock;

public class CUMPService {

    // Constructeur
    public CUMPService() {}

    // Methode
    public List<LotStock> appliquer(List<LotStock> lots, Mouvements_stock sortie) throws Exception {
        double stockTotal = 0;
        BigDecimal valeurTotale = BigDecimal.ZERO;

        // Calcul stock + valeur totale
        for (LotStock lot : lots) {
            stockTotal += lot.getQuantite();
            valeurTotale = valeurTotale.add(
                    lot.getPrix_unitaire().multiply(
                            BigDecimal.valueOf(
                                lot.getQuantite()
                            )
                    )
            );
        }

        if (sortie.getQuantite() > stockTotal) {
            throw new Exception(
                    "Stock insuffisant"
            );
        }

        // Prix moyen pondéré
        BigDecimal prixMoyen = valeurTotale.divide(
                BigDecimal.valueOf(stockTotal),
                2,
                RoundingMode.HALF_UP
        );

        double restant = sortie.getQuantite();

        // Consommation proportionnelle
        for (LotStock lot : lots) {
            if (restant <= 0) {
                break;
            }
            if (lot.getQuantite() <= 0) {
                continue;
            }

            double pris = 0;

            if (lot.getQuantite() <= restant) {
                pris = lot.getQuantite();
                restant -= lot.getQuantite();
                lot.setQuantite(0);
            }
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
                            prixMoyen
                    )
            );
        }
        return lots;
    }
}