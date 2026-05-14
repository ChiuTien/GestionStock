package main;

import java.sql.Connection;

import database.connection.DatabaseConnexion;

public class Main {
    public static void main(String[] args) throws Exception{
        try {
            Connection cn = DatabaseConnexion.getConnection();
            System.out.println("Connexion reussi");
            cn.close();
        } catch (Exception e) {
            throw e;
        }
    }
}