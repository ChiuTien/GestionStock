package models;

import java.math.BigDecimal;

public class LotStock {
    //Attributs
    private double quantite;
    private BigDecimal prix_unitaire;

    //Constructeur
    public LotStock() {}
    public LotStock(double quantite, BigDecimal prix_unitaire) {
        this.quantite = quantite;
        this.prix_unitaire = prix_unitaire;
    }
    
    //Setters
    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }
    public void setPrix_unitaire(BigDecimal prix_unitaire) {
        this.prix_unitaire = prix_unitaire;
    }

    //Getters
    public double getQuantite() {
        return quantite;
    }
    public BigDecimal getPrix_unitaire() {
        return prix_unitaire;
    }
}