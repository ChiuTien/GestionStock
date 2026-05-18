package models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Mouvements_stock {
    //Attributs
    private int id;
    private int produit_id;
    private LocalDate date_mouvement;
    private double quantite;
    private BigDecimal prix_unitaire;
    private int type_id;
    private int source;

    //Constructeur
    public Mouvements_stock() {}
    public Mouvements_stock(int id,int produit_id, LocalDate date_mouvement, double quantite,
    BigDecimal prix_unitaire, int type, int soucre) {
        this.id = id;
        this.produit_id = produit_id;
        this.date_mouvement = date_mouvement;
        this.quantite = quantite;
        this.prix_unitaire = prix_unitaire;
        this.type_id = type;
        this.source = soucre;
    }

    //Setters
    public void setId(int id) {
        this.id = id;
    }
    public void setProduit_id(int id) {
        this.produit_id = id;
    }
    public void setDate_mouvement(LocalDate date_mouvement) {
        this.date_mouvement = date_mouvement;
    }
    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }
    public void setPrix_unitaire(BigDecimal prix_unitaire) {
        this.prix_unitaire = prix_unitaire;
    }
    public void setType_id(int type_id) {
        this.type_id = type_id;
    }
    public void setSource(int soucre) {
        this.source = soucre;
    }

    //Getters
    public int getId() {
        return id;
    }
    public int getProduit_id() {
        return produit_id;
    }
    public LocalDate getDate_mouvement() {
        return date_mouvement;
    }
    public double getQuantite() {
        return quantite;
    }
    public BigDecimal getPrix_unitaire() {
        return prix_unitaire;
    }
    public int getType_id() {
        return type_id;
    }
    public int getSource() {
        return this.source;
    }

    //Methodes utilitaires
    public boolean isEntree() {
        if(type_id == 4) {
            return true;
        }
        return false;
    }
    public boolean isSortie() {
        if(type_id == 5) {
            return true;
        }
        return false;
    }
} 
