package library;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Library {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("╔═══════════════════════════════════════════════════════╗");
        System.out.println("║   LIBRARY MANAGEMENT SYSTEM - COMPLETE DEMO           ║");
        System.out.println("╚═══════════════════════════════════════════════════════╝\n");

        // ===== TASK 1: Basic Book Operations =====
        System.out.println("------TASK 1: Basic Book Operations-------");
        Book book1 = new Book("Harry Potter", "J.K. Rowling");
        System.out.println("Title: " + book1.getTitle());
        System.out.println("Author: " + book1.getAuthor());
        System.out.println("Available: " + book1.isAvailable());
        book1.setAvailable(false);
        System.out.println("Available after update: " + book1.isAvailable());

        // ===== TASK 2: Student Borrowing =====
        System.out.println("\n------TASK 2: Student Borrowing-------");
        Student s1 = new Student("Alice", 101, "Computer Science");
        Book book2 = new Book("Clean Code", "Robert C. Martin");
        s1.borrowBook(book1);
        s1.borrowBook(book2);

        // ===== TASK 3: Staff Management =====
        System.out.println("\n------TASK 3: Staff Management-------");
        Person p1 = new Staff("Bob", 201);
        Person p2 = new Student("Eve", 202, "IT");
        Staff staff1 = (Staff) p1;
        Student student2 = (Student) p2;
        staff1.manageBook(book1, false);
        student2.borrowBook(book2);

        // ===== TASK 4: Polymorphism =====
        System.out.println("\n------TASK 4: Polymorphism-------");
        LibraryUser user1 = new StudentUser("Alice");
        LibraryUser user2 = new StaffUser("Bob");
        user1.accessLibrary();
        user2.accessLibrary();

        // ===== GENERICS: LibraryCollection =====
        System.out.println("\n------GENERICS: LibraryCollection<T>-------");
        LibraryCollection<Book> library = new LibraryCollection<>();
        
        Book book3 = new Book("Effective Java", "Joshua Bloch", "978-0134685991", LocalDate.of(2017, 12, 27));
        Book book4 = new Book("Design Patterns", "Gang of Four", "978-0201633610", LocalDate.of(1994, 10, 31));
        Book book5 = new Book("The Pragmatic Programmer", "Andrew Hunt", "978-0135957059", LocalDate.of(2019, 9, 13));
        
        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);
        library.addBook(book4);
        library.addBook(book5);
        
        System.out.println("Total books in library: " + library.size());

        // ===== COMPARABLE: Natural Ordering =====
        System.out.println("\n------COMPARABLE: Sorting by Title (Natural Order)-------");
        List<Book> sortedByTitle = library.getSortedBooks();
        library.printBooks(sortedByTitle);

        // ===== COMPARATOR: Custom Sorting =====
        System.out.println("\n------COMPARATOR: Sorting by Author-------");
        List<Book> sortedByAuthor = library.getSortedBooks(new BookComparators.AuthorComparator());
        library.printBooks(sortedByAuthor);

        // Simulate some borrowing to test borrow count comparator
        book3.incrementBorrowCount();
        book3.incrementBorrowCount();
        book4.incrementBorrowCount();

        System.out.println("\n------COMPARATOR: Sorting by Borrow Count-------");
        List<Book> sortedByBorrowCount = library.getSortedBooks(new BookComparators.BorrowCountComparator());
        library.printBooks(sortedByBorrowCount);

        System.out.println("\n------COMPARATOR: Sorting by Publish Date-------");
        List<Book> sortedByDate = library.getSortedBooks(new BookComparators.PublishDateComparator());
        library.printBooks(sortedByDate);

        // ===== WILDCARDS: Flexible Type Handling =====
        System.out.println("\n------WILDCARDS: Copying to Different Collections-------");
        List<Book> bookList = new ArrayList<>();
        List<Object> objectList = new ArrayList<>();
        
        library.copyTo(bookList);  // ? super T
        library.copyTo(objectList); // ? super T
        
        System.out.println("Copied " + bookList.size() + " books to Book list");
        System.out.println("Copied " + objectList.size() + " books to Object list");

        // ===== THREADS: Concurrent Borrowing =====
        System.out.println("\n------THREADS: Concurrent Borrowing Operations-------");
        
        Book popularBook = new Book("Java Concurrency in Practice", "Brian Goetz");
        library.addBook(popularBook);
        
        Student john = new Student("John", 301, "CS");
        Student jane = new Student("Jane", 302, "IT");
        Student jack = new Student("Jack", 303, "CS");
        
        // Create threads for concurrent borrowing
        Thread t1 = new Thread(new BorrowTask(john, popularBook, 100));
        Thread t2 = new Thread(new BorrowTask(jane, popularBook, 50));
        Thread t3 = new Thread(new BorrowTask(jack, popularBook, 150));
        
        System.out.println("Starting concurrent borrow attempts...");
        t1.start();
        t2.start();
        t3.start();
        
        // Wait for all threads to complete
        t1.join();
        t2.join();
        t3.join();
        
        System.out.println("\nFinal book status: " + popularBook);
        
        // ===== WILDCARDS: Using addAll =====
        System.out.println("\n------WILDCARDS: Adding Multiple Books-------");
        List<Book> newBooks = Arrays.asList(
            new Book("Head First Java", "Kathy Sierra"),
            new Book("Thinking in Java", "Bruce Eckel")
        );
        library.addAll(newBooks);
        System.out.println("Total books after adding collection: " + library.size());

        // ===== LAMBDA EXPRESSIONS: Functional Programming =====
        demonstrateLambdas(library);

        // ===== REGULAR EXPRESSIONS: Data Validation =====
        demonstrateRegex();

        // ===== FILE I/O: Data Persistence =====
        demonstrateFileIO(library);

        System.out.println("\n╔═══════════════════════════════════════════════════════╗");
        System.out.println("║         ALL FEATURES DEMONSTRATED SUCCESSFULLY!       ║");
        System.out.println("╚═══════════════════════════════════════════════════════╝");
    }

    // ========== LAMBDA EXPRESSIONS DEMONSTRATION ==========
    private static void demonstrateLambdas(LibraryCollection<Book> library) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("LAMBDA EXPRESSIONS - Functional Programming with Streams");
        System.out.println("=".repeat(60));
        
        List<Book> allBooks = library.getAllBooks();
        
        // 1. Filter available books using lambda
        System.out.println("\n1. Filter available books using lambda:");
        List<Book> available = allBooks.stream()
            .filter(book -> book.isAvailable())
            .collect(Collectors.toList());
        System.out.println("   Available: " + available.size() + " out of " + allBooks.size() + " books");
        
        // 2. Filter popular books (borrowed > 2 times)
        System.out.println("\n2. Filter popular books (borrowed > 2 times) using lambda:");
        List<Book> popular = allBooks.stream()
            .filter(book -> book.getBorrowCount() > 2)
            .collect(Collectors.toList());
        System.out.println("   Popular books: " + popular.size());
        popular.forEach(book -> 
            System.out.println("   - " + book.getTitle() + " (" + book.getBorrowCount() + " borrows)")
        );
        
        // 3. Transform books to summaries using map
        System.out.println("\n3. Transform books to summaries using map lambda:");
        List<String> summaries = allBooks.stream()
            .map(book -> book.getTitle() + " by " + book.getAuthor())
            .limit(3)
            .collect(Collectors.toList());
        summaries.forEach(summary -> System.out.println("   - " + summary));
        
        // 4. Find books by specific author
        System.out.println("\n4. Find books by author using lambda:");
        String searchAuthor = "Gang of Four";
        List<Book> authorBooks = allBooks.stream()
            .filter(book -> book.getAuthor().equalsIgnoreCase(searchAuthor))
            .collect(Collectors.toList());
        System.out.println("   Books by " + searchAuthor + ": " + authorBooks.size());
        
        // 5. Get top 3 most borrowed books
        System.out.println("\n5. Top 3 most borrowed books using lambda:");
        allBooks.stream()
            .sorted((b1, b2) -> Integer.compare(b2.getBorrowCount(), b1.getBorrowCount()))
            .limit(3)
            .forEach(book -> 
                System.out.println("   - " + book.getTitle() + " (" + book.getBorrowCount() + " borrows)")
            );
        
        // 6. Group books by author
        System.out.println("\n6. Group books by author using lambda:");
        Map<String, List<Book>> grouped = allBooks.stream()
            .collect(Collectors.groupingBy(Book::getAuthor));
        System.out.println("   Unique authors: " + grouped.size());
        grouped.forEach((author, books) -> 
            System.out.println("   - " + author + ": " + books.size() + " book(s)")
        );
        
        // 7. Calculate statistics
        System.out.println("\n7. Borrow statistics using lambda:");
        IntSummaryStatistics stats = allBooks.stream()
            .mapToInt(Book::getBorrowCount)
            .summaryStatistics();
        System.out.println("   Total borrows: " + stats.getSum());
        System.out.println("   Average borrows: " + String.format("%.2f", stats.getAverage()));
        System.out.println("   Max borrows: " + stats.getMax());
        
        // 8. Find most borrowed book
        System.out.println("\n8. Find most borrowed book using lambda:");
        Optional<Book> mostBorrowed = allBooks.stream()
            .max(Comparator.comparingInt(Book::getBorrowCount));
        mostBorrowed.ifPresent(book -> 
            System.out.println("   Most borrowed: " + book.getTitle() + " (" + book.getBorrowCount() + " times)")
        );
        
        // 9. Search books by title pattern
        System.out.println("\n9. Search books containing 'Java' using lambda:");
        allBooks.stream()
            .filter(book -> book.getTitle().toLowerCase().contains("java"))
            .forEach(book -> System.out.println("   - " + book.getTitle()));
        
        // 10. Partition books by availability
        System.out.println("\n10. Partition books by availability using lambda:");
        Map<Boolean, List<Book>> partitioned = allBooks.stream()
            .collect(Collectors.partitioningBy(Book::isAvailable));
        System.out.println("   Available: " + partitioned.get(true).size());
        System.out.println("   Borrowed: " + partitioned.get(false).size());
        
        // 11. Get unique authors using lambda
        System.out.println("\n11. Get unique authors using lambda:");
        Set<String> uniqueAuthors = allBooks.stream()
            .map(Book::getAuthor)
            .collect(Collectors.toSet());
        System.out.println("   Total unique authors: " + uniqueAuthors.size());
        
        // 12. Check if any book matches condition
        System.out.println("\n12. Check conditions using lambda:");
        boolean hasPopularBook = allBooks.stream()
            .anyMatch(book -> book.getBorrowCount() > 5);
        System.out.println("   Has book borrowed > 5 times? " + hasPopularBook);
        
        boolean allHaveAuthors = allBooks.stream()
            .allMatch(book -> book.getAuthor() != null && !book.getAuthor().isEmpty());
        System.out.println("   All books have authors? " + allHaveAuthors);
    }

    // ========== REGULAR EXPRESSIONS DEMONSTRATION ==========
    private static void demonstrateRegex() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("REGULAR EXPRESSIONS - Data Validation with Regex");
        System.out.println("=".repeat(60));
        
        // 1. ISBN Validation
        System.out.println("\n1. ISBN Validation using Regex:");
        String[] testISBNs = {
            "978-0134685991",  // Valid ISBN-13
            "0-13-468599-1",   // Valid ISBN-10
            "invalid-isbn",    // Invalid
            "9780134685991"    // Valid ISBN-13 without hyphens
        };
        for (String isbn : testISBNs) {
            boolean valid = ValidationUtils.isValidISBN(isbn);
            System.out.println("   " + isbn + " -> " + (valid ? "✓ Valid" : "✗ Invalid"));
        }
        
        // 2. Email Validation
        System.out.println("\n2. Email Validation using Regex:");
        String[] testEmails = {
            "student@university.edu",
            "invalid.email",
            "admin@library.org",
            "user123@test.com"
        };
        for (String email : testEmails) {
            boolean valid = ValidationUtils.isValidEmail(email);
            System.out.println("   " + email + " -> " + (valid ? "✓ Valid" : "✗ Invalid"));
        }
        
        // 3. Phone Number Validation
        System.out.println("\n3. Phone Number Validation using Regex:");
        String[] testPhones = {
            "+1-555-123-4567",
            "555-1234",
            "(555) 123-4567",
            "invalid-phone"
        };
        for (String phone : testPhones) {
            boolean valid = ValidationUtils.isValidPhone(phone);
            System.out.println("   " + phone + " -> " + (valid ? "✓ Valid" : "✗ Invalid"));
        }
        
        // 4. Name Validation
        System.out.println("\n4. Name Validation using Regex:");
        String[] testNames = {
            "John Doe",
            "Mary-Jane O'Connor",
            "123Invalid",
            "Alice Smith"
        };
        for (String name : testNames) {
            boolean valid = ValidationUtils.isValidName(name);
            System.out.println("   " + name + " -> " + (valid ? "✓ Valid" : "✗ Invalid"));
        }
        
        // 5. Student ID Validation
        System.out.println("\n5. Student ID Validation using Regex:");
        String[] testIDs = {"STU12345", "ABC123", "12", "STUDENT001"};
        for (String id : testIDs) {
            boolean valid = ValidationUtils.isValidStudentId(id);
            System.out.println("   " + id + " -> " + (valid ? "✓ Valid" : "✗ Invalid"));
        }
        
        // 6. ISBN Extraction from text
        System.out.println("\n6. ISBN Extraction from text using Regex:");
        String text = "The book ISBN-13: 978-0-13-468599-1 is available.";
        String extracted = ValidationUtils.extractISBN(text);
        System.out.println("   Text: " + text);
        System.out.println("   Extracted: " + (extracted != null ? extracted : "None"));
        
        // 7. ISBN Formatting
        System.out.println("\n7. ISBN Formatting using Regex:");
        String rawIsbn = "9780134685991";
        String formatted = ValidationUtils.formatISBN13(rawIsbn);
        System.out.println("   Raw: " + rawIsbn);
        System.out.println("   Formatted: " + formatted);
        
        // 8. Title Sanitization
        System.out.println("\n8. Title Sanitization using Regex:");
        String dirtyTitle = "Clean Code: A @#$% Guide!";
        String clean = ValidationUtils.sanitizeTitle(dirtyTitle);
        System.out.println("   Original: " + dirtyTitle);
        System.out.println("   Sanitized: " + clean);
        
        // 9. Phone Formatting
        System.out.println("\n9. Phone Number Formatting using Regex:");
        String rawPhone = "5551234567";
        String formattedPhone = ValidationUtils.formatPhoneNumber(rawPhone);
        System.out.println("   Raw: " + rawPhone);
        System.out.println("   Formatted: " + formattedPhone);
        
        // 10. Alphanumeric Check
        System.out.println("\n10. Alphanumeric Check using Regex:");
        String[] testStrings = {"ABC123", "Hello World", "Test123", "Special@Char"};
        for (String str : testStrings) {
            boolean isAlphaNum = ValidationUtils.isAlphanumeric(str);
            System.out.println("   " + str + " -> " + (isAlphaNum ? "✓ Alphanumeric" : "✗ Not alphanumeric"));
        }
    }

    // ========== FILE I/O DEMONSTRATION ==========
    private static void demonstrateFileIO(LibraryCollection<Book> library) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("FILE I/O - Data Persistence and File Operations");
        System.out.println("=".repeat(60));
        
        List<Book> books = library.getAllBooks();
        
        // 1. Save books to file (Serialization)
        System.out.println("\n1. Saving books to file using Serialization:");
        FileManager.saveBooks(books, "library_books.dat");
        
        // 2. Load books from file (Deserialization)
        System.out.println("\n2. Loading books from file using Deserialization:");
        List<Book> loadedBooks = FileManager.loadBooks("library_books.dat");
        System.out.println("   Loaded " + loadedBooks.size() + " books successfully");
        
        // 3. Log transactions to text file
        System.out.println("\n3. Logging transactions to text file:");
        FileManager.logTransaction("Student Alice borrowed 'Clean Code'");
        FileManager.logTransaction("Student Bob borrowed 'Effective Java'");
        FileManager.logTransaction("Student Carol returned 'Design Patterns'");
        FileManager.logTransaction("Staff Dave added new book 'Java Concurrency'");
        System.out.println("   ✓ 4 transactions logged to file");
        
        // 4. Read transaction log
        System.out.println("\n4. Reading transaction log from file:");
        List<String> transactions = FileManager.readTransactionLog();
        int displayCount = Math.min(5, transactions.size());
        System.out.println("   Last " + displayCount + " transactions:");
        for (int i = Math.max(0, transactions.size() - displayCount); i < transactions.size(); i++) {
            System.out.println("   " + transactions.get(i));
        }
        
        // 5. Export to CSV
        System.out.println("\n5. Exporting books to CSV file:");
        FileManager.exportToCSV(books, "library_export.csv");
        System.out.println("   ✓ Books exported in CSV format");
        
        // 6. File operations summary
        System.out.println("\n6. File Operations Summary:");
        System.out.println("   ✓ Binary serialization: library_books.dat");
        System.out.println("   ✓ Text log file: " + FileManager.getTransactionsFile());
        System.out.println("   ✓ CSV export: library_export.csv");
        System.out.println("   ✓ All data persisted successfully!");
    }
}
