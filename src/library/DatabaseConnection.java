package library;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Database credentials and URL
    private static final String URL = "jdbc:mysql://localhost:3306/LibraryDB";
    private static final String USER = "shubham"; // Replace with your MySQL username
    private static final String PASSWORD = "AdmiN#!77"; // Replace with your MySQL password

    // Method to establish connection
    public static Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connected successfully!");
            return connection;
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
            e.printStackTrace(); // Print the full error for debugging
            return null;
        }
    }
}
