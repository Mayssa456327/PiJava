package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDB {


    public final String URL = "jdbc:mysql://localhost:3306/integrationjava";
    public final String USERNAME = "root";
    public final String PWD = "";
//2 creer une variable de m type que la classe
    public static MyDB instance;

    private Connection connection;

//1 Rendre le constructeur Prive
    private MyDB() {

        try {
            connection = DriverManager.getConnection(URL,USERNAME,PWD);
            System.out.println("Connected");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //3 creer une methode getInstance
    public static MyDB getInstance(){
        if(instance==null){
            instance = new MyDB();
        }
        return instance;

    }

    public Connection getConnection() {
        return connection;
    }
}
