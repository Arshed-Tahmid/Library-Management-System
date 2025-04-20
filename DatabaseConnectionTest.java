package com.example;

public class DatabaseConnectionTest {
    public static void main(String[] args) {
        System.out.println("Testing database connection...");
        boolean connected = DatabaseConnection.testConnection();
        System.out.println("Connection successful: " + connected);
    }
}
