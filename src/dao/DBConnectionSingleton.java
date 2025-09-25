package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionSingleton {

    private static DBConnectionSingleton instance; // instance unique
    private Connection connection;

    private static final String URL = "jdbc:mysql://localhost:3306/assurance";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private DBConnectionSingleton() throws SQLException {
        this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static DBConnectionSingleton getInstance() throws SQLException {
        if (instance == null) {
            instance = new DBConnectionSingleton();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
