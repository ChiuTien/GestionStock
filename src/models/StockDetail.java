package models;

import java.math.BigDecimal;
import java.util.List;

public class StockDetail {
    //Attributs
    private Produits produit;
    private List<LotStock> lotsRestants;
    private double quantiteTotale;
    private BigDecimal valeurTotale;

    //Constructeurs
    public StockDetail() {}
    public StockDetail(Produits produit,List<LotStock> lotsRestants,
    double quantiteTotale,BigDecimal valeurTotale) {
        this.produit = produit;
        this.lotsRestants = lotsRestants;
        this.quantiteTotale = quantiteTotale;
        this.valeurTotale = valeurTotale;
    }

    //Setters
    public void setProduit(Produits produit) {
        this.produit = produit;
    }
    public void setLotsRestants(List<LotStock> lotsRestants) {
        this.lotsRestants = lotsRestants;
    }
    public void setQuantiteTotale(double quantiteTotale) {
        this.quantiteTotale = quantiteTotale;
    }

    //Getters
    public Produits getProduit() {
        return produit;
    }
    public List<LotStock> getLotsRestants() {
        return lotsRestants;
    }
    public double getQuantiteTotale() {
        return quantiteTotale;
    }
    public BigDecimal getValeurTotale() {
        return valeurTotale;
    }
    public void setValeurTotale(BigDecimal valeurTotale) {
        this.valeurTotale = valeurTotale;
    }
}