package services;

import models.LotStock;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class CUMPService {

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

        // Evite division par zéro
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