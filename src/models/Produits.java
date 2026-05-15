package models;

public class Produits {
    //Attributs
    private int id;    
    private String nom_produit;
    private int type_id;

    //Constructeur
    public Produits() {}
    public Produits(int id, String nom_produit, int type_id) {
        this.id = id;
        this.nom_produit = nom_produit;
        this.type_id = type_id;
    }

    //Setters
    public void setId(int id) {
        this.id = id;
    }
    public void setNom_produit(String nom_produit) {
        this.nom_produit = nom_produit;
    }
    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    //Getters
    public int getId() {
        return id;
    }
    public String getNom_produit() {
        return nom_produit;
    }
    public int getType_id() {
        return type_id;
    }    
}
