package library;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class LibraryGUI {
    private final BookManager bookManager;
    private JTable bookTable;
    private DefaultListModel<Book> bookListModel;

    public LibraryGUI() {
        bookManager = new BookManager();
        initializeGUI();
    }

    private void initializeGUI() {
        JFrame frame = new JFrame("Library Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Add Book Tab
        tabbedPane.add("Add Book", createAddBookPanel());
        
        // View Books Tab
        tabbedPane.add("View Books", createViewBooksPanel());
        
        // Update Book Tab
        tabbedPane.add("Update Book", createUpdateBookPanel());
        
        // Delete Book Tab
        tabbedPane.add("Delete Book", createDeleteBookPanel());

        frame.add(tabbedPane);
        frame.setVisible(true);
    }

    private JPanel createAddBookPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField titleField = new JTextField();
        JTextField authorField = new JTextField();
        JTextField isbnField = new JTextField();
        JTextField copiesField = new JTextField();
        JButton addButton = new JButton("Add Book");

        panel.add(new JLabel("Title:"));
        panel.add(titleField);
        panel.add(new JLabel("Author:"));
        panel.add(authorField);
        panel.add(new JLabel("ISBN:"));
        panel.add(isbnField);
        panel.add(new JLabel("Copies:"));
        panel.add(copiesField);
        panel.add(new JLabel(""));
        panel.add(addButton);

        addButton.addActionListener((ActionEvent e) -> {
            String title = titleField.getText();
            String author = authorField.getText();
            String isbn = isbnField.getText();

            if (title.isEmpty() || author.isEmpty() || isbn.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Please fill in all fields.");
                return;
            }

            try {
                int copies = Integer.parseInt(copiesField.getText());
                bookManager.addBook(new Book(0, title, author, isbn, copies));
                JOptionPane.showMessageDialog(panel, "Book added successfully!");
                
                // Clear fields after successful addition
                titleField.setText("");
                authorField.setText("");
                isbnField.setText("");
                copiesField.setText("");
                
                // Refresh the book table if it exists
                refreshBookTable();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Invalid input for copies. Please enter a number.");
            }
        });

        return panel;
    }

    private JPanel createViewBooksPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create table with columns
        String[] columns = {"ID", "Title", "Author", "ISBN", "Copies"};
        bookTable = new JTable(new DefaultTableModel(columns, 0));
        JScrollPane scrollPane = new JScrollPane(bookTable);

        // Refresh button
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> refreshBookTable());

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(refreshButton, BorderLayout.SOUTH);

        // Initial load of books
        refreshBookTable();

        return panel;
    }

    private JPanel createUpdateBookPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField isbnField = new JTextField();
        JTextField newCopiesField = new JTextField();
        JButton updateButton = new JButton("Update Quantity");

        panel.add(new JLabel("ISBN:"));
        panel.add(isbnField);
        panel.add(new JLabel("New Quantity:"));
        panel.add(newCopiesField);
        panel.add(new JLabel(""));
        panel.add(updateButton);

        updateButton.addActionListener(e -> {
            String isbn = isbnField.getText();
            if (isbn.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Please enter ISBN.");
                return;
            }

            try {
                int newCopies = Integer.parseInt(newCopiesField.getText());
                if (bookManager.updateBookQuantity(isbn, newCopies)) {
                    JOptionPane.showMessageDialog(panel, "Book quantity updated successfully!");
                    isbnField.setText("");
                    newCopiesField.setText("");
                    refreshBookTable();
                } else {
                    JOptionPane.showMessageDialog(panel, "Book not found with given ISBN.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Invalid input for copies. Please enter a number.");
            }
        });

        return panel;
    }

    private JPanel createDeleteBookPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField isbnField = new JTextField();
        JButton deleteButton = new JButton("Delete Book");

        panel.add(new JLabel("ISBN:"));
        panel.add(isbnField);
        panel.add(new JLabel(""));
        panel.add(deleteButton);

        deleteButton.addActionListener(e -> {
            String isbn = isbnField.getText();
            if (isbn.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Please enter ISBN.");
                return;
            }

            if (bookManager.deleteBook(isbn)) {
                JOptionPane.showMessageDialog(panel, "Book deleted successfully!");
                isbnField.setText("");
                refreshBookTable();
            } else {
                JOptionPane.showMessageDialog(panel, "Book not found with given ISBN.");
            }
        });

        return panel;
    }

    private void refreshBookTable() {
        DefaultTableModel model = (DefaultTableModel) bookTable.getModel();
        model.setRowCount(0); // Clear existing rows

        List<Book> books = bookManager.getAllBooks();
        for (Book book : books) {
            model.addRow(new Object[]{
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getCopies()
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LibraryGUI());
    }
}