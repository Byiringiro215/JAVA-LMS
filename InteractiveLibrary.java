package library;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class InteractiveLibrary {
    private static LibraryCollection<Book> library = new LibraryCollection<>();
    private static Map<Integer, Student> students = new HashMap<>();
    private static Map<Integer, Staff> staffMembers = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);
    private static int nextStudentId = 1001;
    private static int nextStaffId = 2001;
    private static final String AUTO_SAVE_FILE = "library_export.csv";
    
    // Auto-save library to CSV after changes
    private static void autoSaveLibrary() {
        FileManager.exportToCSV(library.getAllBooks(), AUTO_SAVE_FILE);
    }

    public static void main(String[] args) {
        initializeLibrary();
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║   WELCOME TO LIBRARY MANAGEMENT SYSTEM     ║");
        System.out.println("╚════════════════════════════════════════════╝\n");

        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    bookManagementMenu();
                    break;
                case 2:
                    studentMenu();
                    break;
                case 3:
                    staffMenu();
                    break;
                case 4:
                    viewReportsMenu();
                    break;
                case 5:
                    demonstrateAdvancedFeatures();
                    break;
                case 0:
                    System.out.println("\n✓ Thank you for using Library Management System!");
                    running = false;
                    break;
                default:
                    System.out.println("\n✗ Invalid choice. Please try again.\n");
            }
        }
        scanner.close();
    }

    private static void displayMainMenu() {
        System.out.println("═══════════════ MAIN MENU ═══════════════");
        System.out.println("1. Book Management");
        System.out.println("2. Student Operations");
        System.out.println("3. Staff Operations");
        System.out.println("4. View Reports & Statistics");
        System.out.println("5. Demonstrate Advanced Features");
        System.out.println("0. Exit");
        System.out.println("═════════════════════════════════════════");
    }

    // ========== BOOK MANAGEMENT ==========
    private static void bookManagementMenu() {
        System.out.println("\n--- BOOK MANAGEMENT ---");
        System.out.println("1. Add New Book");
        System.out.println("2. View All Books");
        System.out.println("3. View Available Books");
        System.out.println("4. Search Book by Title");
        System.out.println("5. Search Book by ISBN");
        System.out.println("0. Back to Main Menu");

        int choice = getIntInput("Enter your choice: ");

        switch (choice) {
            case 1:
                addNewBook();
                break;
            case 2:
                viewAllBooks();
                break;
            case 3:
                viewAvailableBooks();
                break;
            case 4:
                searchBookByTitle();
                break;
            case 5:
                searchBookByISBN();
                break;
            case 0:
                return;
            default:
                System.out.println("✗ Invalid choice.\n");
        }
    }

    private static void addNewBook() {
        System.out.println("\n--- ADD NEW BOOK ---");
        scanner.nextLine(); // consume newline
        
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        
        // Sanitize title
        String sanitizedTitle = ValidationUtils.sanitizeTitle(title);
        if (!sanitizedTitle.equals(title)) {
            System.out.println("ℹ Title sanitized: " + sanitizedTitle);
            title = sanitizedTitle;
        }
        
        if (title.trim().isEmpty()) {
            System.out.println("✗ Title cannot be empty.\n");
            return;
        }
        
        System.out.print("Enter author name: ");
        String author = scanner.nextLine();
        
        // Validate author name
        if (!ValidationUtils.isValidName(author)) {
            System.out.println("✗ Invalid author name. Use only letters, spaces, hyphens, and apostrophes.\n");
            return;
        }
        
        System.out.print("Enter ISBN (or press Enter to skip): ");
        String isbn = scanner.nextLine();
        
        // Validate ISBN if provided
        if (!isbn.trim().isEmpty()) {
            if (ValidationUtils.isValidISBN(isbn)) {
                System.out.println("✓ Valid ISBN format");
            } else {
                System.out.println("✗ Invalid ISBN format. Book will be added without ISBN.");
                isbn = null;
            }
        } else {
            isbn = null;
        }
        
        System.out.print("Enter publish date (yyyy-MM-dd) or press Enter to skip: ");
        String dateStr = scanner.nextLine();
        LocalDate publishDate = null;
        
        if (!dateStr.trim().isEmpty()) {
            try {
                publishDate = LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE);
            } catch (DateTimeParseException e) {
                System.out.println("✗ Invalid date format. Book added without publish date.");
            }
        }

        Book book = new Book(title, author, isbn, publishDate);
        library.addBook(book);
        autoSaveLibrary();  // Auto-save after adding book
        FileManager.logTransaction("Added book: " + title + " by " + author);
        System.out.println("✓ Book added successfully!\n");
    }

    private static void viewAllBooks() {
        System.out.println("\n--- ALL BOOKS IN LIBRARY ---");
        List<Book> books = library.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("No books in library.\n");
            return;
        }
        
        System.out.println("Total books: " + books.size());
        System.out.println("─────────────────────────────────────────");
        for (int i = 0; i < books.size(); i++) {
            System.out.println((i + 1) + ". " + books.get(i));
        }
        System.out.println();
    }

    private static void viewAvailableBooks() {
        System.out.println("\n--- AVAILABLE BOOKS ---");
        List<Book> available = library.getAvailableBooks();
        if (available.isEmpty()) {
            System.out.println("No books available.\n");
            return;
        }
        
        System.out.println("Available books: " + available.size());
        System.out.println("─────────────────────────────────────────");
        for (int i = 0; i < available.size(); i++) {
            System.out.println((i + 1) + ". " + available.get(i));
        }
        System.out.println();
    }

    private static void searchBookByTitle() {
        scanner.nextLine(); // consume newline
        System.out.print("\nEnter book title to search: ");
        String searchTitle = scanner.nextLine().toLowerCase();
        
        List<Book> books = library.getAllBooks();
        List<Book> found = new ArrayList<>();
        
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(searchTitle)) {
                found.add(book);
            }
        }
        
        if (found.isEmpty()) {
            System.out.println("✗ No books found with that title.\n");
        } else {
            System.out.println("\n✓ Found " + found.size() + " book(s):");
            for (Book book : found) {
                System.out.println("  - " + book);
            }
            System.out.println();
        }
    }

    private static void searchBookByISBN() {
        scanner.nextLine(); // consume newline
        System.out.print("\nEnter ISBN: ");
        String isbn = scanner.nextLine();
        
        // Validate ISBN format
        if (!ValidationUtils.isValidISBN(isbn)) {
            System.out.println("✗ Invalid ISBN format. Please enter a valid ISBN-10 or ISBN-13.\n");
            return;
        }
        
        Optional<Book> book = library.findByIsbn(isbn);
        if (book.isPresent()) {
            System.out.println("\n✓ Book found: " + book.get() + "\n");
        } else {
            System.out.println("✗ No book found with that ISBN.\n");
        }
    }

    // ========== STUDENT OPERATIONS ==========
    private static void studentMenu() {
        System.out.println("\n--- STUDENT OPERATIONS ---");
        System.out.println("1. Register New Student");
        System.out.println("2. View All Students");
        System.out.println("3. Borrow Book");
        System.out.println("4. Return Book");
        System.out.println("0. Back to Main Menu");

        int choice = getIntInput("Enter your choice: ");

        switch (choice) {
            case 1:
                registerStudent();
                break;
            case 2:
                viewAllStudents();
                break;
            case 3:
                borrowBook();
                break;
            case 4:
                returnBook();
                break;
            case 0:
                return;
            default:
                System.out.println("✗ Invalid choice.\n");
        }
    }

    private static void registerStudent() {
        scanner.nextLine(); // consume newline
        System.out.println("\n--- REGISTER NEW STUDENT ---");
        
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        
        // Validate student name
        if (!ValidationUtils.isValidName(name)) {
            System.out.println("✗ Invalid name format. Use only letters, spaces, hyphens, and apostrophes.\n");
            return;
        }
        
        System.out.print("Enter department: ");
        String department = scanner.nextLine();
        
        if (department.trim().isEmpty()) {
            System.out.println("✗ Department cannot be empty.\n");
            return;
        }
        
        int id = nextStudentId++;
        Student student = new Student(name, id, department);
        students.put(id, student);
        
        System.out.println("✓ Student registered successfully!");
        System.out.println("  Student ID: " + id);
        System.out.println("  Name: " + name);
        System.out.println("  Department: " + department + "\n");
    }

    private static void viewAllStudents() {
        System.out.println("\n--- ALL STUDENTS ---");
        if (students.isEmpty()) {
            System.out.println("No students registered.\n");
            return;
        }
        
        System.out.println("Total students: " + students.size());
        System.out.println("─────────────────────────────────────────");
        for (Student student : students.values()) {
            System.out.println(student);
        }
        System.out.println();
    }

    private static void borrowBook() {
        if (students.isEmpty()) {
            System.out.println("\n✗ No students registered. Please register a student first.\n");
            return;
        }
        
        System.out.println("\n--- BORROW BOOK ---");
        int studentId = getIntInput("Enter student ID: ");
        
        Student student = students.get(studentId);
        if (student == null) {
            System.out.println("✗ Student not found.\n");
            return;
        }
        
        viewAvailableBooks();
        scanner.nextLine(); // consume newline
        System.out.print("Enter book title to borrow: ");
        String title = scanner.nextLine();
        
        List<Book> books = library.getAllBooks();
        Book foundBook = null;
        
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title) && book.isAvailable()) {
                foundBook = book;
                break;
            }
        }
        
        if (foundBook != null) {
            student.borrowBook(foundBook);
            FileManager.logTransaction("Student " + student.getName() + " borrowed: " + foundBook.getTitle());
            autoSaveLibrary();  // Auto-save after borrowing
        } else {
            System.out.println("✗ Book not available or not found.\n");
        }
    }

    private static void returnBook() {
        if (staffMembers.isEmpty()) {
            // Auto-create a staff member for returns
            Staff autoStaff = new Staff("System", 9999);
            staffMembers.put(9999, autoStaff);
        }
        
        System.out.println("\n--- RETURN BOOK ---");
        scanner.nextLine(); // consume newline
        System.out.print("Enter book title to return: ");
        String title = scanner.nextLine();
        
        List<Book> books = library.getAllBooks();
        Book foundBook = null;
        
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title) && !book.isAvailable()) {
                foundBook = book;
                break;
            }
        }
        
        if (foundBook != null) {
            Staff staff = staffMembers.get(9999);
            staff.manageBook(foundBook, true);
            FileManager.logTransaction("Book returned: " + foundBook.getTitle());
            autoSaveLibrary();  // Auto-save after returning
        } else {
            System.out.println("✗ Book not found or already available.\n");
        }
    }

    // ========== STAFF OPERATIONS ==========
    private static void staffMenu() {
        System.out.println("\n--- STAFF OPERATIONS ---");
        System.out.println("1. Register New Staff");
        System.out.println("2. View All Staff");
        System.out.println("3. Add Book to Library");
        System.out.println("4. Remove Book from Library");
        System.out.println("0. Back to Main Menu");

        int choice = getIntInput("Enter your choice: ");

        switch (choice) {
            case 1:
                registerStaff();
                break;
            case 2:
                viewAllStaff();
                break;
            case 3:
                staffAddBook();
                break;
            case 4:
                staffRemoveBook();
                break;
            case 0:
                return;
            default:
                System.out.println("✗ Invalid choice.\n");
        }
    }

    private static void registerStaff() {
        scanner.nextLine(); // consume newline
        System.out.println("\n--- REGISTER NEW STAFF ---");
        
        System.out.print("Enter staff name: ");
        String name = scanner.nextLine();
        
        // Validate staff name
        if (!ValidationUtils.isValidName(name)) {
            System.out.println("✗ Invalid name format. Use only letters, spaces, hyphens, and apostrophes.\n");
            return;
        }
        
        int id = nextStaffId++;
        Staff staff = new Staff(name, id);
        staffMembers.put(id, staff);
        
        System.out.println("✓ Staff registered successfully!");
        System.out.println("  Staff ID: " + id);
        System.out.println("  Name: " + name + "\n");
    }

    private static void viewAllStaff() {
        System.out.println("\n--- ALL STAFF MEMBERS ---");
        if (staffMembers.isEmpty()) {
            System.out.println("No staff members registered.\n");
            return;
        }
        
        System.out.println("Total staff: " + staffMembers.size());
        System.out.println("─────────────────────────────────────────");
        for (Staff staff : staffMembers.values()) {
            System.out.println(staff);
        }
        System.out.println();
    }

    private static void staffAddBook() {
        if (staffMembers.isEmpty()) {
            System.out.println("\n✗ No staff registered. Please register staff first.\n");
            return;
        }
        
        System.out.println("\n--- STAFF: ADD BOOK ---");
        int staffId = getIntInput("Enter staff ID: ");
        
        Staff staff = staffMembers.get(staffId);
        if (staff == null) {
            System.out.println("✗ Staff not found.\n");
            return;
        }
        
        viewAllBooks();
        scanner.nextLine(); // consume newline
        System.out.print("Enter book title to add back: ");
        String title = scanner.nextLine();
        
        List<Book> books = library.getAllBooks();
        Book foundBook = null;
        
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                foundBook = book;
                break;
            }
        }
        
        if (foundBook != null) {
            staff.manageBook(foundBook, true);
            FileManager.logTransaction("Staff " + staff.getName() + " added book to library: " + foundBook.getTitle());
            autoSaveLibrary();  // Auto-save after staff adds book
        } else {
            System.out.println("✗ Book not found.\n");
        }
    }

    private static void staffRemoveBook() {
        if (staffMembers.isEmpty()) {
            System.out.println("\n✗ No staff registered. Please register staff first.\n");
            return;
        }
        
        System.out.println("\n--- STAFF: REMOVE BOOK ---");
        int staffId = getIntInput("Enter staff ID: ");
        
        Staff staff = staffMembers.get(staffId);
        if (staff == null) {
            System.out.println("✗ Staff not found.\n");
            return;
        }
        
        viewAllBooks();
        scanner.nextLine(); // consume newline
        System.out.print("Enter book title to remove: ");
        String title = scanner.nextLine();
        
        List<Book> books = library.getAllBooks();
        Book foundBook = null;
        
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                foundBook = book;
                break;
            }
        }
        
        if (foundBook != null) {
            staff.manageBook(foundBook, false);
            FileManager.logTransaction("Staff " + staff.getName() + " removed book from library: " + foundBook.getTitle());
            autoSaveLibrary();  // Auto-save after staff removes book
        } else {
            System.out.println("✗ Book not found.\n");
        }
    }

    // ========== REPORTS & STATISTICS ==========
    private static void viewReportsMenu() {
        System.out.println("\n--- REPORTS & STATISTICS ---");
        System.out.println("1. Sort Books by Title (Comparable)");
        System.out.println("2. Sort Books by Author (Comparator)");
        System.out.println("3. Sort Books by Popularity (Comparator)");
        System.out.println("4. Sort Books by Publish Date (Comparator)");
        System.out.println("5. Library Statistics");
        System.out.println("0. Back to Main Menu");

        int choice = getIntInput("Enter your choice: ");

        switch (choice) {
            case 1:
                sortByTitle();
                break;
            case 2:
                sortByAuthor();
                break;
            case 3:
                sortByPopularity();
                break;
            case 4:
                sortByPublishDate();
                break;
            case 5:
                showStatistics();
                break;
            case 0:
                return;
            default:
                System.out.println("✗ Invalid choice.\n");
        }
    }

    private static void sortByTitle() {
        System.out.println("\n--- BOOKS SORTED BY TITLE (Comparable) ---");
        List<Book> sorted = library.getSortedBooks();
        if (sorted.isEmpty()) {
            System.out.println("No books in library.\n");
            return;
        }
        library.printBooks(sorted);
        System.out.println();
    }

    private static void sortByAuthor() {
        System.out.println("\n--- BOOKS SORTED BY AUTHOR (Comparator) ---");
        List<Book> sorted = library.getSortedBooks(new BookComparators.AuthorComparator());
        if (sorted.isEmpty()) {
            System.out.println("No books in library.\n");
            return;
        }
        library.printBooks(sorted);
        System.out.println();
    }

    private static void sortByPopularity() {
        System.out.println("\n--- BOOKS SORTED BY POPULARITY (Comparator) ---");
        List<Book> sorted = library.getSortedBooks(new BookComparators.BorrowCountComparator());
        if (sorted.isEmpty()) {
            System.out.println("No books in library.\n");
            return;
        }
        library.printBooks(sorted);
        System.out.println();
    }

    private static void sortByPublishDate() {
        System.out.println("\n--- BOOKS SORTED BY PUBLISH DATE (Comparator) ---");
        List<Book> sorted = library.getSortedBooks(new BookComparators.PublishDateComparator());
        if (sorted.isEmpty()) {
            System.out.println("No books in library.\n");
            return;
        }
        library.printBooks(sorted);
        System.out.println();
    }

    private static void showStatistics() {
        System.out.println("\n--- LIBRARY STATISTICS ---");
        List<Book> allBooks = library.getAllBooks();
        List<Book> available = library.getAvailableBooks();
        
        System.out.println("Total Books: " + allBooks.size());
        System.out.println("Available Books: " + available.size());
        System.out.println("Borrowed Books: " + (allBooks.size() - available.size()));
        System.out.println("Total Students: " + students.size());
        System.out.println("Total Staff: " + staffMembers.size());
        
        int totalBorrows = 0;
        for (Book book : allBooks) {
            totalBorrows += book.getBorrowCount();
        }
        System.out.println("Total Borrow Count: " + totalBorrows);
        System.out.println();
    }

    // ========== ADVANCED FEATURES DEMO ==========
    private static void demonstrateAdvancedFeatures() {
        System.out.println("\n--- ADVANCED FEATURES DEMONSTRATION ---");
        System.out.println("1. Demonstrate Generics");
        System.out.println("2. Demonstrate Wildcards");
        System.out.println("3. Demonstrate Threads (Concurrent Borrowing)");
        System.out.println("4. Demonstrate Lambda Expressions");
        System.out.println("5. Demonstrate Regular Expressions (Regex)");
        System.out.println("6. Demonstrate File I/O Operations");
        System.out.println("7. Demonstrate All Features");
        System.out.println("0. Back to Main Menu");

        int choice = getIntInput("Enter your choice: ");

        switch (choice) {
            case 1:
                demonstrateGenerics();
                break;
            case 2:
                demonstrateWildcards();
                break;
            case 3:
                demonstrateThreads();
                break;
            case 4:
                demonstrateLambdas();
                break;
            case 5:
                demonstrateRegex();
                break;
            case 6:
                demonstrateFileIO();
                break;
            case 7:
                demonstrateAllFeatures();
                break;
            case 0:
                return;
            default:
                System.out.println("✗ Invalid choice.\n");
        }
    }

    private static void demonstrateGenerics() {
        System.out.println("\n=== GENERICS DEMONSTRATION ===");
        System.out.println("LibraryCollection<T extends Book> is a generic class.");
        System.out.println("It provides type-safe operations for Book and its subclasses.");
        System.out.println("Current library size: " + library.size());
        System.out.println("✓ Generics ensure compile-time type safety!\n");
    }

    private static void demonstrateWildcards() {
        System.out.println("\n=== WILDCARDS DEMONSTRATION ===");
        
        // Upper bounded wildcard
        List<Book> bookList = new ArrayList<>();
        library.copyTo(bookList);
        System.out.println("✓ Upper bounded wildcard (? extends T):");
        System.out.println("  Copied " + bookList.size() + " books to Book list");
        
        // Lower bounded wildcard
        List<Object> objectList = new ArrayList<>();
        library.copyTo(objectList);
        System.out.println("✓ Lower bounded wildcard (? super T):");
        System.out.println("  Copied " + objectList.size() + " books to Object list");
        
        System.out.println("✓ Wildcards provide flexible type handling!\n");
    }

    private static void demonstrateThreads() {
        System.out.println("\n=== THREADS DEMONSTRATION ===");
        System.out.println("Creating a popular book and 3 students trying to borrow it concurrently...\n");
        
        Book popularBook = new Book("Concurrent Programming", "Expert Author");
        library.addBook(popularBook);
        
        Student s1 = new Student("Thread-Student-1", 9001, "CS");
        Student s2 = new Student("Thread-Student-2", 9002, "IT");
        Student s3 = new Student("Thread-Student-3", 9003, "CS");
        
        Thread t1 = new Thread(new BorrowTask(s1, popularBook, 100));
        Thread t2 = new Thread(new BorrowTask(s2, popularBook, 50));
        Thread t3 = new Thread(new BorrowTask(s3, popularBook, 150));
        
        System.out.println("Starting concurrent borrow attempts...");
        t1.start();
        t2.start();
        t3.start();
        
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            System.err.println("Thread interrupted: " + e.getMessage());
        }
        
        System.out.println("\nFinal book status: " + popularBook);
        System.out.println("✓ Thread synchronization prevents race conditions!\n");
    }

    private static void demonstrateAllFeatures() {
        demonstrateGenerics();
        demonstrateWildcards();
        demonstrateThreads();
        demonstrateLambdas();
        demonstrateRegex();
        demonstrateFileIO();
    }

    private static void demonstrateLambdas() {
        System.out.println("\n=== LAMBDA EXPRESSIONS DEMONSTRATION ===");
        System.out.println("1. Filter available books");
        System.out.println("2. Search books by author");
        System.out.println("3. Find books with minimum borrows");
        System.out.println("4. Top N most borrowed books");
        System.out.println("5. Group books by author");
        System.out.println("6. Show borrow statistics");
        System.out.println("7. Search books by title keyword");
        System.out.println("0. Back");
        
        int choice = getIntInput("\nEnter your choice: ");
        List<Book> allBooks = library.getAllBooks();
        
        switch (choice) {
            case 1:
                System.out.println("\n--- Filter Available Books ---");
                List<Book> available = allBooks.stream()
                    .filter(Book::isAvailable)
                    .collect(java.util.stream.Collectors.toList());
                System.out.println("Available: " + available.size() + " out of " + allBooks.size() + " books");
                available.forEach(book -> System.out.println("  - " + book.getTitle()));
                break;
                
            case 2:
                scanner.nextLine();
                System.out.print("\nEnter author name to search: ");
                String author = scanner.nextLine();
                System.out.println("\n--- Books by " + author + " ---");
                allBooks.stream()
                    .filter(book -> book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                    .forEach(book -> System.out.println("  - " + book.getTitle() + " by " + book.getAuthor()));
                break;
                
            case 3:
                int minBorrows = getIntInput("\nEnter minimum borrow count: ");
                System.out.println("\n--- Books borrowed at least " + minBorrows + " times ---");
                List<Book> filtered = allBooks.stream()
                    .filter(book -> book.getBorrowCount() >= minBorrows)
                    .collect(java.util.stream.Collectors.toList());
                if (filtered.isEmpty()) {
                    System.out.println("  No books found with that criteria.");
                } else {
                    filtered.forEach(book -> System.out.println("  - " + book.getTitle() + " (" + book.getBorrowCount() + " borrows)"));
                }
                break;
                
            case 4:
                int topN = getIntInput("\nEnter number of top books to show: ");
                System.out.println("\n--- Top " + topN + " Most Borrowed Books ---");
                allBooks.stream()
                    .sorted((b1, b2) -> Integer.compare(b2.getBorrowCount(), b1.getBorrowCount()))
                    .limit(topN)
                    .forEach(book -> System.out.println("  - " + book.getTitle() + " (" + book.getBorrowCount() + " borrows)"));
                break;
                
            case 5:
                System.out.println("\n--- Books Grouped by Author ---");
                allBooks.stream()
                    .collect(java.util.stream.Collectors.groupingBy(Book::getAuthor))
                    .forEach((auth, books) -> {
                        System.out.println("  " + auth + " (" + books.size() + " book(s)):");
                        books.forEach(book -> System.out.println("    - " + book.getTitle()));
                    });
                break;
                
            case 6:
                System.out.println("\n--- Borrow Statistics ---");
                java.util.IntSummaryStatistics stats = allBooks.stream()
                    .mapToInt(Book::getBorrowCount)
                    .summaryStatistics();
                System.out.println("  Total books: " + stats.getCount());
                System.out.println("  Total borrows: " + stats.getSum());
                System.out.println("  Average borrows: " + String.format("%.2f", stats.getAverage()));
                System.out.println("  Max borrows: " + stats.getMax());
                System.out.println("  Min borrows: " + stats.getMin());
                break;
                
            case 7:
                scanner.nextLine();
                System.out.print("\nEnter keyword to search in titles: ");
                String keyword = scanner.nextLine();
                System.out.println("\n--- Books containing '" + keyword + "' ---");
                List<Book> matches = allBooks.stream()
                    .filter(book -> book.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                    .collect(java.util.stream.Collectors.toList());
                if (matches.isEmpty()) {
                    System.out.println("  No books found.");
                } else {
                    matches.forEach(book -> System.out.println("  - " + book.getTitle()));
                }
                break;
                
            case 0:
                return;
                
            default:
                System.out.println("✗ Invalid choice.\n");
        }
        System.out.println();
    }

    private static void demonstrateRegex() {
        System.out.println("\n=== REGULAR EXPRESSIONS DEMONSTRATION ===");
        System.out.println("1. Validate ISBN");
        System.out.println("2. Validate Email");
        System.out.println("3. Validate Phone Number");
        System.out.println("4. Validate Name");
        System.out.println("5. Validate Student ID");
        System.out.println("6. Extract ISBN from text");
        System.out.println("7. Format ISBN");
        System.out.println("8. Sanitize book title");
        System.out.println("0. Back");
        
        int choice = getIntInput("\nEnter your choice: ");
        scanner.nextLine(); // consume newline
        
        switch (choice) {
            case 1:
                System.out.print("\nEnter ISBN to validate: ");
                String isbn = scanner.nextLine();
                boolean isbnValid = ValidationUtils.isValidISBN(isbn);
                System.out.println("Result: " + isbn + " -> " + (isbnValid ? "✓ Valid ISBN" : "✗ Invalid ISBN"));
                break;
                
            case 2:
                System.out.print("\nEnter email to validate: ");
                String email = scanner.nextLine();
                boolean emailValid = ValidationUtils.isValidEmail(email);
                System.out.println("Result: " + email + " -> " + (emailValid ? "✓ Valid Email" : "✗ Invalid Email"));
                break;
                
            case 3:
                System.out.print("\nEnter phone number to validate: ");
                String phone = scanner.nextLine();
                boolean phoneValid = ValidationUtils.isValidPhone(phone);
                System.out.println("Result: " + phone + " -> " + (phoneValid ? "✓ Valid Phone" : "✗ Invalid Phone"));
                if (phoneValid) {
                    String formatted = ValidationUtils.formatPhoneNumber(phone);
                    System.out.println("Formatted: " + formatted);
                }
                break;
                
            case 4:
                System.out.print("\nEnter name to validate: ");
                String name = scanner.nextLine();
                boolean nameValid = ValidationUtils.isValidName(name);
                System.out.println("Result: " + name + " -> " + (nameValid ? "✓ Valid Name" : "✗ Invalid Name"));
                break;
                
            case 5:
                System.out.print("\nEnter student ID to validate: ");
                String studentId = scanner.nextLine();
                boolean idValid = ValidationUtils.isValidStudentId(studentId);
                System.out.println("Result: " + studentId + " -> " + (idValid ? "✓ Valid Student ID" : "✗ Invalid Student ID"));
                break;
                
            case 6:
                System.out.print("\nEnter text containing ISBN: ");
                String text = scanner.nextLine();
                String extracted = ValidationUtils.extractISBN(text);
                System.out.println("Extracted ISBN: " + (extracted != null ? extracted : "None found"));
                break;
                
            case 7:
                System.out.print("\nEnter ISBN to format (13 digits): ");
                String rawIsbn = scanner.nextLine();
                String formatted = ValidationUtils.formatISBN13(rawIsbn);
                System.out.println("Formatted: " + formatted);
                break;
                
            case 8:
                System.out.print("\nEnter book title to sanitize: ");
                String title = scanner.nextLine();
                String sanitized = ValidationUtils.sanitizeTitle(title);
                System.out.println("Original: " + title);
                System.out.println("Sanitized: " + sanitized);
                break;
                
            case 0:
                return;
                
            default:
                System.out.println("✗ Invalid choice.\n");
        }
        System.out.println();
    }

    private static void demonstrateFileIO() {
        System.out.println("\n=== FILE I/O DEMONSTRATION ===");
        System.out.println("1. Save books to file (Serialization)");
        System.out.println("2. Load books from file (Deserialization)");
        System.out.println("3. Add transaction to log");
        System.out.println("4. View transaction log");
        System.out.println("5. Export books to CSV");
        System.out.println("6. Clear transaction log");
        System.out.println("0. Back");
        
        int choice = getIntInput("\nEnter your choice: ");
        List<Book> books = library.getAllBooks();
        
        switch (choice) {
            case 1:
                scanner.nextLine();
                System.out.print("\nEnter filename to save (e.g., mybooks.dat): ");
                String saveFile = scanner.nextLine();
                if (saveFile.trim().isEmpty()) saveFile = "library_books.dat";
                FileManager.saveBooks(books, saveFile);
                System.out.println("✓ " + books.size() + " books saved to " + saveFile);
                break;
                
            case 2:
                scanner.nextLine();
                System.out.print("\nEnter filename to load (e.g., mybooks.dat): ");
                String loadFile = scanner.nextLine();
                if (loadFile.trim().isEmpty()) loadFile = "library_books.dat";
                List<Book> loaded = FileManager.loadBooks(loadFile);
                System.out.println("✓ Loaded " + loaded.size() + " books from " + loadFile);
                if (!loaded.isEmpty()) {
                    System.out.println("\nFirst 3 books:");
                    for (int i = 0; i < Math.min(3, loaded.size()); i++) {
                        System.out.println("  " + (i+1) + ". " + loaded.get(i).getTitle());
                    }
                }
                break;
                
            case 3:
                scanner.nextLine();
                System.out.print("\nEnter transaction description: ");
                String transaction = scanner.nextLine();
                FileManager.logTransaction(transaction);
                System.out.println("✓ Transaction logged successfully");
                break;
                
            case 4:
                System.out.println("\n--- Transaction Log ---");
                List<String> transactions = FileManager.readTransactionLog();
                if (transactions.isEmpty()) {
                    System.out.println("  No transactions logged yet.");
                } else {
                    int start = Math.max(0, transactions.size() - 10);
                    System.out.println("  Showing last " + (transactions.size() - start) + " transactions:");
                    for (int i = start; i < transactions.size(); i++) {
                        System.out.println("  " + transactions.get(i));
                    }
                }
                break;
                
            case 5:
                scanner.nextLine();
                System.out.print("\nEnter CSV filename (e.g., books.csv): ");
                String csvFile = scanner.nextLine();
                if (csvFile.trim().isEmpty()) csvFile = "library_export.csv";
                FileManager.exportToCSV(books, csvFile);
                System.out.println("✓ " + books.size() + " books exported to " + csvFile);
                break;
                
            case 6:
                System.out.print("\nAre you sure you want to clear the transaction log? (yes/no): ");
                scanner.nextLine();
                String confirm = scanner.nextLine();
                if (confirm.equalsIgnoreCase("yes")) {
                    FileManager.clearTransactionLog();
                    System.out.println("✓ Transaction log cleared");
                } else {
                    System.out.println("✗ Operation cancelled");
                }
                break;
                
            case 0:
                return;
                
            default:
                System.out.println("✗ Invalid choice.\n");
        }
        System.out.println();
    }

    // ========== UTILITY METHODS ==========
    private static void initializeLibrary() {
        // Add some initial books
        library.addBook(new Book("Harry Potter", "J.K. Rowling", "978-0439708180", LocalDate.of(1998, 9, 1)));
        library.addBook(new Book("Clean Code", "Robert C. Martin", "978-0132350884", LocalDate.of(2008, 8, 1)));
        library.addBook(new Book("Effective Java", "Joshua Bloch", "978-0134685991", LocalDate.of(2017, 12, 27)));
        library.addBook(new Book("Design Patterns", "Gang of Four", "978-0201633610", LocalDate.of(1994, 10, 31)));
        library.addBook(new Book("The Pragmatic Programmer", "Andrew Hunt", "978-0135957059", LocalDate.of(2019, 9, 13)));
        
        // Initial save to CSV
        autoSaveLibrary();
        System.out.println("✓ Library initialized and saved to " + AUTO_SAVE_FILE + "\n");
    }

    private static int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("✗ Invalid input. Please enter a number: ");
            scanner.next();
        }
        return scanner.nextInt();
    }
}
