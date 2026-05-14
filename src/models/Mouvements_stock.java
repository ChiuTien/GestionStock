package models;

import java.time.LocalDate;

public class Mouvements_stock {
    //Attributs
    private int id;
    private LocalDate date_mouvement;
    private double quantite;
    private double prix_unitaire;
    private int type_id;

    //Constructeur
    public Mouvements_stock() {}
    public Mouvements_stock(int id, LocalDate date_mouvement, double quantite,
    double prix_unitaire, int type) {
        this.id = id;
        this.date_mouvement = date_mouvement;
        this.quantite = quantite;
        this.prix_unitaire = prix_unitaire;
        this.type_id = type;
    }

    //Setters
    public void setId(int id) {
        this.id = id;
    }
    public void setDate_mouvement(LocalDate date_mouvement) {
        this.date_mouvement = date_mouvement;
    }
    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }
    public void setPrix_unitaire(double prix_unitaire) {
        this.prix_unitaire = prix_unitaire;
    }
    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    //Getters
    public int getId() {
        return id;
    }
    public LocalDate getDate_mouvement() {
        return date_mouvement;
    }
    public double getQuantite() {
        return quantite;
    }
    public double getPrix_unitaire() {
        return prix_unitaire;
    }
    public int getType_id() {
        return type_id;
    }
} 
