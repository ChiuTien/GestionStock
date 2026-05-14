package models;

public class Produits {
    //Attributs
    private int id;    
    private String nom_produit;
    private String type_id;

    //Constructeur
    public Produits() {}
    public Produits(int id, String nom_produit, String type_id) {
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
    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    //Getters
    public int getId() {
        return id;
    }
    public String getNom_produit() {
        return nom_produit;
    }
    public String getType_id() {
        return type_id;
    }    
}
