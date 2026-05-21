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
    public List<LotStock> appliquer(
            List<LotStock> lots,
            Mouvements_stock sortie
    ) throws Exception {

        // Vérifications
        if (lots == null || lots.isEmpty()) {
            throw new Exception("Aucun lot disponible");
        }

        if (sortie == null) {
            throw new Exception("Sortie nulle");
        }

        // Calcul quantité + valeur totale
        BigDecimal stockTotal = BigDecimal.ZERO;
        BigDecimal valeurTotale = BigDecimal.ZERO;

        for (LotStock lot : lots) {

            BigDecimal qte = BigDecimal.valueOf(
                    lot.getQuantite()
            );

            stockTotal = stockTotal.add(qte);

            valeurTotale = valeurTotale.add(
                    lot.getPrix_unitaire().multiply(qte)
            );
        }

        // Vérification stock
        BigDecimal qteSortie = BigDecimal.valueOf(
                sortie.getQuantite()
        );

        if (qteSortie.compareTo(stockTotal) > 0) {
            throw new Exception(
                    "Stock insuffisant"
            );
        }

        // Calcul du prix moyen pondéré
        BigDecimal prixMoyen = valeurTotale.divide(
                stockTotal,
                6,
                RoundingMode.HALF_UP
        );

        // Quantité restante à sortir
        BigDecimal restant = qteSortie;

        // Consommation
        for (LotStock lot : lots) {

            if (restant.compareTo(BigDecimal.ZERO) <= 0) {
                break;
            }

            if (lot.getQuantite() <= 0) {
                continue;
            }

            BigDecimal qteLot = BigDecimal.valueOf(
                    lot.getQuantite()
            );

            BigDecimal pris;

            // On vide complètement le lot
            if (qteLot.compareTo(restant) <= 0) {

                pris = qteLot;

                lot.setQuantite(0);

            }

            // On prend partiellement
            else {

                pris = restant;

                lot.setQuantite(
                        qteLot.subtract(restant).doubleValue()
                );
            }

            // Mise à jour restant
            restant = restant.subtract(pris);

            // Historique
            lot.getConsommation().add(
                    new ConsommationLot(
                            sortie.getId(),
                            sortie.getDate_mouvement(),

                            lot.getMouvement_id(),
                            lot.getDateEntree(),

                            pris.doubleValue(),

                            prixMoyen.setScale(
                                    2,
                                    RoundingMode.HALF_UP
                            )
                    )
            );
        }

        return lots;
    }
}