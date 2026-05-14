package models;

import java.math.BigDecimal;
import java.util.List;

public class StockDetail {
    //Attributs
    private String produit;
    private List<LotStock> lotsRestants;
    private int quantiteTotale;
    private BigDecimal valeurTotale;

    //Constructeurs
    public StockDetail() {}
    public StockDetail(String produit,List<LotStock> lotsRestants,
    int quantiteTotale,BigDecimal valeurTotale) {
        this.produit = produit;
        this.lotsRestants = lotsRestants;
        this.quantiteTotale = quantiteTotale;
        this.valeurTotale = valeurTotale;
    }

    //Setters
    public void setProduit(String produit) {
        this.produit = produit;
    }
    public void setLotsRestants(List<LotStock> lotsRestants) {
        this.lotsRestants = lotsRestants;
    }
    public void setQuantiteTotale(int quantiteTotale) {
        this.quantiteTotale = quantiteTotale;
    }
    public String getProduit() {
        return produit;
    }

    //Getters
    public List<LotStock> getLotsRestants() {
        return lotsRestants;
    }
    public int getQuantiteTotale() {
        return quantiteTotale;
    }
    public BigDecimal getValeurTotale() {
        return valeurTotale;
    }
    public void setValeurTotale(BigDecimal valeurTotale) {
        this.valeurTotale = valeurTotale;
    }
}