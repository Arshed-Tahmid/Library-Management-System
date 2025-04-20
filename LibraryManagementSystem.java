package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LibraryManagementSystem {
    static List<Book> books = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    static int bookIdCounter = 1;

    public static void main(String[] args) {
        // Load books from the database at startup
        books = Book.getAllBooks();

        while (true) {
            System.out.println("\n===== Library Management System =====");
            System.out.println("1. Add Book");
            System.out.println("2. View All Books");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> addBook();
                case 2 -> viewBooks();
                case 3 -> borrowBook();
                case 4 -> returnBook();
                case 5 -> {
                    System.out.println("Exiting system. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    static void addBook() {
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();

        System.out.print("Enter author name: ");
        String author = scanner.nextLine();

        Book newBook = new Book(bookIdCounter++, title, author);
        newBook.saveToDatabase(); // Save to database
        books.add(newBook); // Optionally keep in memory
        System.out.println("Book added successfully!");
    }

    static void viewBooks() {
        books = Book.getAllBooks(); // Fetch from database
        if (books.isEmpty()) {
            System.out.println("No books available.");
            return;
        }

        System.out.println("\nAvailable Books:");
        for (Book book : books) {
            System.out.println(book);
        }
    }

    static void borrowBook() {
        System.out.print("Enter book ID to borrow: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        for (Book book : books) {
            if (book.id == id) {
                if (book.isAvailable) {
                    book.isAvailable = false;
                    book.updateAvailabilityInDatabase(); // Update in database
                    System.out.println("Book borrowed successfully!");
                } else {
                    System.out.println("Sorry, the book is already borrowed.");
                }
                return;
            }
        }
        System.out.println("Book not found.");
    }

    static void returnBook() {
        System.out.print("Enter book ID to return: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        for (Book book : books) {
            if (book.id == id) {
                if (!book.isAvailable) {
                    book.isAvailable = true;
                    book.updateAvailabilityInDatabase(); // Update in database
                    System.out.println("Book returned successfully!");
                } else {
                    System.out.println("This book was not borrowed.");
                }
                return;
 
           }
        }
        System.out.println("Book not found.");
    }
}