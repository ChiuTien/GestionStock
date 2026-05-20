package models;

import java.math.BigDecimal;
import java.util.List;

public class LotStock {
    //Attributs
    private int mouvement_id;
    private double quantite;
    private BigDecimal prix_unitaire;
    private List<ConsommationLot> consommations;

    //Constructeur
    public LotStock() {}
    public LotStock(double quantite, BigDecimal prix_unitaire) {
        this.quantite = quantite;
        this.prix_unitaire = prix_unitaire;
    }
    public LotStock(int mouvement_id, double quantite, BigDecimal prix_unitaire) {
        this.mouvement_id = mouvement_id;
        this.quantite = quantite;
        this.prix_unitaire = prix_unitaire;
    }
    public LotStock(int mouvement_id, double quantite, BigDecimal prix_unitaire, List<ConsommationLot> consommation) {
        this.mouvement_id = mouvement_id;
        this.quantite = quantite;
        this.prix_unitaire = prix_unitaire;
        this.consommations = consommation;
    }
    
    //Setters
    public void setMouvement_id(int id) {
        this.mouvement_id = id;
    }
    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }
    public void setPrix_unitaire(BigDecimal prix_unitaire) {
        this.prix_unitaire = prix_unitaire;
    }
    public void setConsommations(List<ConsommationLot> consommations) {
        this.consommations = consommations;
    }

    //Getters
    public int getMouvement_id() {
        return mouvement_id;
    }
    public double getQuantite() {
        return quantite;
    }
    public BigDecimal getPrix_unitaire() {
        return prix_unitaire;
    }
    public List<ConsommationLot> getConsommation() {
        return this.consommations;
    }
}