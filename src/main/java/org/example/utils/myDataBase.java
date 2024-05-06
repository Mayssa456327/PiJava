package org.example.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class myDataBase {
    private final String URL = "jdbc:mysql://localhost:3306/DocDirect";
    private final String USER = "root";
    private final String PASSWORD = "";
    private Connection connection;
    private static myDataBase instance;

    private myDataBase() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static myDataBase getInstance() {
        if(instance == null)
            instance = new myDataBase();
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
