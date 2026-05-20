package services;

import models.LotStock;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class CUMPService {

    public List<LotStock> appliquer(List<LotStock> lots, double sortie) {

        int totalQte = 0;

        for (LotStock lot : lots) {
            totalQte += lot.getQuantite();
        }
        if (sortie > totalQte) {
            throw new IllegalArgumentException(
                    "Stock insuffisant"
            );
        }

        BigDecimal prixMoyen = calculerPrixMoyen(lots);

        double reste = totalQte - sortie;

        List<LotStock> result = new ArrayList<>();

        if (reste > 0) {
            result.add(new LotStock(reste, prixMoyen)
            );
        }

        return result;
    }

    public BigDecimal calculerPrixMoyen(List<LotStock> lots) {

        BigDecimal totalValeur = BigDecimal.ZERO;
        int totalQte = 0;

        for (LotStock lot : lots) {

            BigDecimal valeur =
                    lot.getPrix_unitaire()
                        .multiply(
                                BigDecimal.valueOf(
                                    lot.getQuantite()
                                )
                            );

            totalValeur = totalValeur.add(valeur);

            totalQte += lot.getQuantite();
        }

        if (totalQte == 0) {
            return BigDecimal.ZERO;
        }

        return totalValeur.divide(
                BigDecimal.valueOf(totalQte),
                2,
                RoundingMode.HALF_UP
        );
    }
}