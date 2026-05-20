package services;

import models.LotStock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LIFOService {
    //Constructeur
    public LIFOService() {}

    //Methode
    public List<LotStock> appliquer(List<LotStock> lots, double sortie) {
        List<LotStock> reversed = new ArrayList<>(lots);

        Collections.reverse(reversed);

        List<LotStock> result = new ArrayList<>();

        for (LotStock lot : reversed) {
            if (sortie <= 0) {
                result.add(lot);
                continue;
            }
            double qte = lot.getQuantite();
            if (qte <= sortie) {
                sortie -= qte;
                continue;
            }
            result.add(new LotStock(
                    qte - sortie,
                    lot.getPrix_unitaire()
            ));
            sortie = 0;
        }

        Collections.reverse(result);
        return result;
    }
}