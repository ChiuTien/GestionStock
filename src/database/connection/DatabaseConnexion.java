package database.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnexion {
    //Attributs
    private static final String url="jdbc:postgresql://127.0.0.1:5432/stock";
    private static final String user="chiu";
    private static final String password="chiu";

    //Fonction appel Static
    public static Connection getConnection()  throws Exception{
        return DriverManager.getConnection(url,user,password);
    }
}
