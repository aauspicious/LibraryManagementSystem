package library;

import java.util.List;
import java.util.Scanner;

public class LibrarySystem {
    private static final BookManager bookManager = new BookManager();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("--- Library Management System ---");
            System.out.println("1. Add Book");
            System.out.println("2. View All Books");
            System.out.println("3. Update Book Quantity");
            System.out.println("4. Delete Book");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter author: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter ISBN: ");
                    String isbn = scanner.nextLine();
                    System.out.print("Enter number of copies: ");
                    int copies;
                    try {
                        copies = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input for copies. Please enter a valid number.");
                        break;
                    }
                    bookManager.addBook(new Book(0, title, author, isbn, copies));
                    break;
                case 2:
                    List<Book> books = bookManager.getAllBooks(); // Changed from viewAllBooks
                    if (books.isEmpty()) {
                        System.out.println("No books found in the library.");
                    } else {
                        for (Book book : books) {
                            System.out.println(book.getId() + ". " + book.getTitle() + " by " + book.getAuthor() + 
                                    " (ISBN: " + book.getIsbn() + ") - Copies: " + book.getCopies());
                        }
                    }
                    break;
                case 3:
                    System.out.print("Enter ISBN to update: "); // Changed from book ID to ISBN
                    String isbnToUpdate = scanner.nextLine();
                    System.out.print("Enter new quantity: ");
                    int newCopies;
                    try {
                        newCopies = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input for quantity. Please enter a valid number.");
                        break;
                    }
                    if (bookManager.updateBookQuantity(isbnToUpdate, newCopies)) {
                        System.out.println("Book quantity updated successfully!");
                    } else {
                        System.out.println("Book not found with given ISBN.");
                    }
                    break;
                case 4:
                    System.out.print("Enter ISBN to delete: "); // Changed from book ID to ISBN
                    String isbnToDelete = scanner.nextLine();
                    if (bookManager.deleteBook(isbnToDelete)) {
                        System.out.println("Book deleted successfully!");
                    } else {
                        System.out.println("Book not found with given ISBN.");
                    }
                    break;
                case 5:
                    System.out.println("Exiting the system. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}