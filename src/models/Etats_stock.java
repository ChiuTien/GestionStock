package models;

import java.time.LocalDate;

public class Etats_stock {
    //Attributs
    private int id;
    private int produit_id;
    private LocalDate date_etat;
    private double stock;
    private double valeur_stock;
    
    //Constructeur
    public Etats_stock() {}
    public Etats_stock(int id, int produit_id, LocalDate date_etat,
        double stock, double valeur_stock ) {
            this.id = id;
            this.produit_id = produit_id;
            this.date_etat = date_etat;
            this.stock = stock;
            this.valeur_stock = valeur_stock;
    }

    //Setters
    public void setId(int id) {
        this.id = id;
    }
    public void setProduit_id(int produit_id) {
        this.produit_id = produit_id;
    }
    public void setDate_etat(LocalDate date_etat) {
        this.date_etat = date_etat;
    }
    public void setStock(double stock) {
        this.stock = stock;
    }
    public void setValeur_stock(double valeur_stock) {
        this.valeur_stock = valeur_stock;
    }

    //Getters
    public int getId() {
        return id;
    }
    public int getProduit_id() {
        return produit_id;
    }
    public LocalDate getDate_etat() {
        return date_etat;
    }
    public double getStock() {
        return stock;
    }
    public double getValeur_stock() {
        return valeur_stock;
    }

    
}
