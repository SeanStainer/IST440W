package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseController {
    private Connection connection;

    public void connect(String ipAddress, String username, String password) throws SQLException {
        String url = "jdbc:postgresql://" + ipAddress + ":5432/gisapplication";
        connection = DriverManager.getConnection(url, username, password);
    }

    public Connection getConnection() {
        return connection;
    }
}