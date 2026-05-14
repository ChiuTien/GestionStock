package services;

import java.util.ArrayList;
import java.util.List;

import models.LotStock;

public class FIFOService {
    //Constructeur
    public FIFOService() {}

    //Methode
    public List<LotStock> appliquer(List<LotStock> lots, double sortie) {
        List<LotStock> result = new ArrayList<>();
        for (LotStock lot : lots) {
            if(sortie<=0) {
                result.add(lot);
                continue;
            }
            double qte = lot.getQuantite();
            if(qte <= sortie) {
                sortie -= qte;
                continue;
            }
            result.add(new LotStock(
                qte-sortie,
                lot.getPrix_unitaire()
            ));
            sortie = 0;
        }
        return result;
    }
}
