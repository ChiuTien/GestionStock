package models;

public class Types {
    //Attributs
    private int id;
    private String libeller;

    //Constructeur
    public Types() {}
    public Types(int id, String libeller) {
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
