package com.webapp.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private final Connection connection;

    static final String JDBC_DRIVER = "org.sqlite.JDBC";

    public DatabaseConnection(String dbUrl) {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(dbUrl);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public Connection getConnection() {
        return connection;
    }
    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
