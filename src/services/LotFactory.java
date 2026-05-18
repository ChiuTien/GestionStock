package services;

import java.util.ArrayList;
import java.util.List;

import models.LotStock;
import models.Mouvements_stock;

public class LotFactory {
    //Constructeur
    public LotFactory() {}
    
    //Methode
    public List<LotStock> buildLots(List<Mouvements_stock> mouvements) {
        List<LotStock> lots = new ArrayList<>();
        for (Mouvements_stock m : mouvements) {
            if(m.isEntree()) {
                lots.add(new LotStock(
                    m.getId(),
                    m.getQuantite(),
                    m.getPrix_unitaire()
                ));
            }
        }
        return lots;
    }
}
