package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Configuration parameters with default values
    public static final String DEFAULT_URL = "jdbc:mysql://localhost:3306/library";
    public static final String DEFAULT_USER = "root";
    public static final String DEFAULT_PASSWORD = "";
    
    // Validate connection parameters before use
    private static void validateConnectionParams(String url, String user) {
        if (url == null || url.trim().isEmpty()) {
            throw new IllegalArgumentException("Database URL cannot be null or empty");
        }
        if (user == null || user.trim().isEmpty()) {
            throw new IllegalArgumentException("Database username cannot be null or empty");
        }
    }

    /**
     * Gets a database connection with default parameters
     */
    public static Connection getConnection() throws SQLException {
        return getConnection(DEFAULT_URL, DEFAULT_USER, DEFAULT_PASSWORD);
    }

    /**
     * Gets a database connection with custom parameters
     */
    public static Connection getConnection(String url, String user, String password) throws SQLException {
        validateConnectionParams(url, user);
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new SQLException("Failed to establish database connection: " + e.getMessage(), e);
        }
    }

    /**
     * Tests the database connection
     */
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn.isValid(2); // 2 second timeout
        } catch (SQLException e) {
            return false;
        }
    }
}
