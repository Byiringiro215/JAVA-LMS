package library;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

// File I/O operations for persisting library data
public class FileManager {
    private static final String BOOKS_FILE = "library_books.dat";
    private static final String STUDENTS_FILE = "library_students.dat";
    private static final String TRANSACTIONS_FILE = "library_transactions.txt";

    // Save books to file using serialization
    public static void saveBooks(List<Book> books, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(books);
            System.out.println("✓ Books saved to " + filename);
        } catch (IOException e) {
            System.err.println("✗ Error saving books: " + e.getMessage());
        }
    }

    // Load books from file using deserialization
    @SuppressWarnings("unchecked")
    public static List<Book> loadBooks(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            List<Book> books = (List<Book>) ois.readObject();
            System.out.println("✓ Books loaded from " + filename);
            return books;
        } catch (FileNotFoundException e) {
            System.out.println("ℹ No saved books file found. Starting fresh.");
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("✗ Error loading books: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Save transaction log to text file
    public static void logTransaction(String transaction) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TRANSACTIONS_FILE, true))) {
            String timestamp = LocalDate.now().toString();
            writer.write(String.format("[%s] %s%n", timestamp, transaction));
        } catch (IOException e) {
            System.err.println("✗ Error logging transaction: " + e.getMessage());
        }
    }

    // Read transaction log
    public static List<String> readTransactionLog() {
        List<String> transactions = new ArrayList<>();
        File file = new File(TRANSACTIONS_FILE);
        
        if (!file.exists()) {
            System.out.println("ℹ No transaction log found.");
            return transactions;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                transactions.add(line);
            }
            System.out.println("✓ Transaction log loaded (" + transactions.size() + " entries)");
        } catch (IOException e) {
            System.err.println("✗ Error reading transaction log: " + e.getMessage());
        }
        
        return transactions;
    }

    // Export books to CSV format
    public static void exportToCSV(List<Book> books, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("Title,Author,ISBN,PublishDate,Available,BorrowCount");
            
            for (Book book : books) {
                writer.printf("\"%s\",\"%s\",\"%s\",\"%s\",%b,%d%n",
                    book.getTitle(),
                    book.getAuthor(),
                    book.getIsbn() != null ? book.getIsbn() : "",
                    book.getPublishDate() != null ? book.getPublishDate().toString() : "",
                    book.isAvailable(),
                    book.getBorrowCount()
                );
            }
            System.out.println("✓ Books exported to " + filename);
        } catch (IOException e) {
            System.err.println("✗ Error exporting to CSV: " + e.getMessage());
        }
    }

    // Clear transaction log
    public static void clearTransactionLog() {
        File file = new File(TRANSACTIONS_FILE);
        if (file.exists() && file.delete()) {
            System.out.println("✓ Transaction log cleared.");
        }
    }

    // Get default filenames
    public static String getBooksFile() { return BOOKS_FILE; }
    public static String getStudentsFile() { return STUDENTS_FILE; }
    public static String getTransactionsFile() { return TRANSACTIONS_FILE; }
}
