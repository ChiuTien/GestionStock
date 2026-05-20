package models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ConsommationLot {
    //Attributs
    private int sortieId;
    private LocalDate dateSortie;
    
    private int entreeId;
    private LocalDate dateEntree;
    
    private double quantite;

    private BigDecimal prixUnitaire;
    
    private BigDecimal valeur;

    // Constructeurs
    public ConsommationLot() {}
    public ConsommationLot(int sortieId, LocalDate dateSortie, int entreeId, 
        LocalDate dateEntree, double quantite, BigDecimal prixUnitaire ) {
        this.sortieId = sortieId;
        this.dateSortie = dateSortie;

        this.entreeId = entreeId;
        this.dateEntree = dateEntree;

        this.quantite = quantite;

        this.prixUnitaire = prixUnitaire;

        this.valeur = prixUnitaire.multiply(
                BigDecimal.valueOf(quantite)
        );
    }

    // Setters
    public void setSortieId(int sortieId) {
        this.sortieId = sortieId;
    }
    public void setDateSortie(LocalDate dateSortie) {
        this.dateSortie = dateSortie;
    }
    public void setEntreeId(int entreeId) {
        this.entreeId = entreeId;
    }
    public void setDateEntree(LocalDate dateEntree) {
        this.dateEntree = dateEntree;
    }
    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }
    public void setPrixUnitaire(BigDecimal prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }
    public void setValeur(BigDecimal valeur) {
        this.valeur = valeur;
    }

    // Getters
    public int getSortieId() {
        return sortieId;
    }
    public LocalDate getDateSortie() {
        return dateSortie;
    }
    public int getEntreeId() {
        return entreeId;
    }
    public LocalDate getDateEntree() {
        return dateEntree;
    }
    public double getQuantite() {
        return quantite;
    }
    public BigDecimal getPrixUnitaire() {
        return prixUnitaire;
    }
    public BigDecimal getValeur() {
        return valeur;
    }
}