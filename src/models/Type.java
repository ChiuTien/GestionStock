package models;

public class Type {
    //Attributs
    private int id;
    private String libeller;

    //Constructeur
    public Type() {}
    public Type(int id, String libeller) {
        this.id = id;
        this.libeller = libeller;
    }

    //Setters
    public void setId(int id) {
        this.id = id;
    }
    public void setLibeller(String libeller) {
        this.libeller = libeller;
    }

    //Getters
    public int getId() {
        return id;
    }
    public String getLibeller() {
        return libeller;
    }

}
