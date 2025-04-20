package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Book {
    int id;
    String title;
    String author;
    boolean isAvailable;

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isAvailable = true;
    }

    public void saveToDatabase() {
        String query = "INSERT INTO books (title, author, isAvailable) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(
                DatabaseConnection.DEFAULT_URL,
                DatabaseConnection.DEFAULT_USER,
                DatabaseConnection.DEFAULT_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, this.title);
            pstmt.setString(2, this.author);
            pstmt.setBoolean(3, this.isAvailable);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Book> getAllBooks() {
        List<Book> bookList = new ArrayList<>();
        String query = "SELECT * FROM books";
        try (Connection conn = DatabaseConnection.getConnection(
                DatabaseConnection.DEFAULT_URL,
                DatabaseConnection.DEFAULT_USER,
                DatabaseConnection.DEFAULT_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Book book = new Book(rs.getInt("id"), rs.getString("title"), rs.getString("author"));
                book.isAvailable = rs.getBoolean("isAvailable");
                bookList.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookList;
    }

    public void updateAvailabilityInDatabase() {
        String query = "UPDATE books SET isAvailable = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection(
                DatabaseConnection.DEFAULT_URL,
                DatabaseConnection.DEFAULT_USER,
                DatabaseConnection.DEFAULT_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setBoolean(1, this.isAvailable);
            pstmt.setInt(2, this.id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return id + ". " + title + " by " + author + " - " + (isAvailable ? "Available" : "Borrowed");
    }
}