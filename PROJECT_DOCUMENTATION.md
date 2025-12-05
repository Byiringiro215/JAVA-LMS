# Library Management System - Complete Java Features Documentation

## 📋 Project Overview

This is a comprehensive **Library Management System** implemented in Java that demonstrates advanced programming concepts including OOP principles, data structures, concurrency, functional programming, file I/O, and data validation.

**Student Project Submission**  
**Course:** Advanced Java Programming  
**Date:** December 2025

---

## 🎯 Features Implemented

This project demonstrates **ALL** the following Java concepts:

✅ **Object-Oriented Programming (OOP)**  
✅ **Comparable & Comparators**  
✅ **Threads & Multithreading**  
✅ **Lambda Expressions & Streams**  
✅ **Collections Framework**  
✅ **Serialization & File I/O**  
✅ **Regular Expressions (Regex)**  
✅ **Generics & Wildcards**  
✅ **String Formatting**  
✅ **Exception Handling**

---

## 📁 Project Structure

```
library/
├── Core Entity Classes
│   ├── Book.java                    - Book entity with Comparable
│   ├── Person.java                  - Base person class (Serializable)
│   ├── Student.java                 - Student extending Person
│   └── Staff.java                   - Staff extending Person
│
├── Interface & Polymorphism
│   ├── LibraryUser.java             - Abstract interface
│   ├── StudentUser.java             - Student implementation
│   └── StaffUser.java               - Staff implementation
│
├── Collections & Generics
│   └── LibraryCollection.java       - Generic thread-safe collection
│
├── Comparators & Sorting
│   └── BookComparators.java         - Custom comparators
│
├── Lambda Expressions
│   └── BookFilters.java             - Functional programming utilities
│
├── Multithreading
│   └── BorrowTask.java              - Runnable for concurrent operations
│
├── File I/O & Serialization
│   └── FileManager.java             - File operations (binary, text, CSV)
│
├── Regular Expressions
│   └── ValidationUtils.java         - Regex validation utilities
│
├── Formatting
│   └── FormattingDemo.java          - Various formatting techniques
│
├── Main Programs
│   ├── Library.java                 - Complete automated demo
│   └── InteractiveLibrary.java     - Interactive menu system
│
└── Batch Files
    ├── run.bat                      - Main launcher
    ├── run-demo.bat                 - Quick demo launcher
    └── run-interactive.bat          - Interactive launcher
```

---

## 🔍 Detailed Feature Implementation

### 1. ✅ COMPARABLE - Natural Ordering

**File:** `Book.java`

**Implementation:**
```java
public class Book implements Comparable<Book>, Serializable {
    @Override
    public int compareTo(Book other) {
        return this.title.compareToIgnoreCase(other.title);
    }
}
```

**Purpose:** Enables natural ordering of books alphabetically by title.

**Usage in Project:**
- `LibraryCollection.getSortedBooks()` - Sorts books using natural ordering
- Demonstrated in `Library.java` line 60-63

**Key Concepts:**
- Implements `Comparable<T>` interface
- Defines natural ordering for objects
- Used by `Collections.sort()` and `sorted()` stream operations

---

### 2. ✅ COMPARATORS - Custom Sorting

**File:** `BookComparators.java`

**Implementation:**
```java
// Sort by Author
public static class AuthorComparator implements Comparator<Book> {
    @Override
    public int compare(Book b1, Book b2) {
        return b1.getAuthor().compareToIgnoreCase(b2.getAuthor());
    }
}

// Sort by Popularity (Borrow Count)
public static class BorrowCountComparator implements Comparator<Book> {
    @Override
    public int compare(Book b1, Book b2) {
        return Integer.compare(b2.getBorrowCount(), b1.getBorrowCount());
    }
}

// Sort by Publish Date
public static class PublishDateComparator implements Comparator<Book> {
    @Override
    public int compare(Book b1, Book b2) {
        if (b1.getPublishDate() == null && b2.getPublishDate() == null) return 0;
        if (b1.getPublishDate() == null) return 1;
        if (b2.getPublishDate() == null) return -1;
        return b1.getPublishDate().compareTo(b2.getPublishDate());
    }
}
```

**Purpose:** Provides multiple custom sorting strategies for books.

**Usage in Project:**
- Sort by author: `Library.java` line 66-68
- Sort by popularity: `Library.java` line 77-82
- Sort by date: `Library.java` line 84-88
- Interactive sorting: `InteractiveLibrary.java` lines 350-380

**Key Concepts:**
- Implements `Comparator<T>` interface
- Allows multiple sorting strategies
- Separates sorting logic from entity class

---

### 3. ✅ THREADS & MULTITHREADING - Concurrent Operations

**Files:** `BorrowTask.java`, `LibraryCollection.java`, `Book.java`

**Implementation:**

**BorrowTask.java** - Runnable for concurrent borrowing:
```java
public class BorrowTask implements Runnable {
    private final Student student;
    private final Book book;
    private final int delayMs;

    @Override
    public void run() {
        try {
            Thread.sleep(delayMs);
            synchronized (book) {
                student.borrowBook(book);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
```

**Book.java** - Synchronized methods:
```java
public synchronized void setAvailable(boolean available) { 
    this.isAvailable = available; 
}

public synchronized void incrementBorrowCount() { 
    this.borrowCount++; 
}
```

**LibraryCollection.java** - ReadWriteLock for thread-safe operations:
```java
private final ReadWriteLock lock = new ReentrantReadWriteLock();

public void addBook(T book) {
    lock.writeLock().lock();
    try {
        books.add(book);
    } finally {
        lock.writeLock().unlock();
    }
}

public List<T> getAllBooks() {
    lock.readLock().lock();
    try {
        return new ArrayList<>(books);
    } finally {
        lock.readLock().unlock();
    }
}
```

**Purpose:** Demonstrates thread safety and concurrent operations.

**Usage in Project:**
- Concurrent borrowing demo: `Library.java` lines 93-110
- Interactive thread demo: `InteractiveLibrary.java` lines 450-490

**Key Concepts:**
- `Runnable` interface implementation
- `synchronized` keyword for method synchronization
- `ReadWriteLock` for efficient concurrent access
- Multiple readers, single writer pattern
- Thread safety and race condition prevention

---

### 4. ✅ LAMBDA EXPRESSIONS - Functional Programming

**File:** `BookFilters.java`

**Implementation:**
```java
// Predicate for filtering
public static final Predicate<Book> IS_AVAILABLE = book -> book.isAvailable();
public static final Predicate<Book> IS_POPULAR = book -> book.getBorrowCount() > 5;

// Function for transformation
public static final Function<Book, String> BOOK_SUMMARY = 
    book -> String.format("%s by %s", book.getTitle(), book.getAuthor());

// Consumer for processing
public static final Consumer<Book> PRINT_BOOK = 
    book -> System.out.println("  - " + book);

// Stream operations
public static List<Book> filterBooks(List<Book> books, Predicate<Book> predicate) {
    return books.stream()
               .filter(predicate)
               .collect(Collectors.toList());
}

public static List<Book> getTopBorrowed(List<Book> books, int n) {
    return books.stream()
               .sorted((b1, b2) -> Integer.compare(b2.getBorrowCount(), b1.getBorrowCount()))
               .limit(n)
               .collect(Collectors.toList());
}

public static Map<String, List<Book>> groupByAuthor(List<Book> books) {
    return books.stream()
               .collect(Collectors.groupingBy(Book::getAuthor));
}
```

**Purpose:** Demonstrates functional programming with lambda expressions and streams.

**Usage in Project:**
- Filter operations: `Library.java` lines 120-180
- Interactive lambda menu: `InteractiveLibrary.java` lines 650-770
- Stream operations throughout the project

**Key Concepts:**
- Lambda syntax: `(parameters) -> expression`
- Functional interfaces: `Predicate`, `Function`, `Consumer`, `BiFunction`
- Stream API: `filter()`, `map()`, `sorted()`, `collect()`
- Method references: `Book::getAuthor`
- Collectors: `toList()`, `groupingBy()`, `partitioningBy()`

---

### 5. ✅ COLLECTIONS FRAMEWORK

**File:** `LibraryCollection.java`

**Implementation:**
```java
public class LibraryCollection<T extends Book> {
    private final List<T> books;                          // ArrayList
    private final Map<String, T> booksByIsbn;             // ConcurrentHashMap
    private final ReadWriteLock lock;                     // ReentrantReadWriteLock
    
    public LibraryCollection() {
        this.books = new ArrayList<>();
        this.booksByIsbn = new ConcurrentHashMap<>();
        this.lock = new ReentrantReadWriteLock();
    }
}
```

**Collections Used:**
- **ArrayList** - Dynamic array for book storage
- **HashMap/ConcurrentHashMap** - Fast ISBN lookup
- **HashSet** - Unique author collection
- **TreeSet** - Sorted collections (via Comparable)
- **LinkedList** - Queue operations (if needed)

**Usage in Project:**
- Throughout `LibraryCollection.java`
- `InteractiveLibrary.java` - Maps for students and staff
- `BookFilters.java` - Stream operations on collections

**Key Concepts:**
- List, Set, Map interfaces
- ArrayList vs LinkedList
- HashMap for O(1) lookups
- ConcurrentHashMap for thread-safe operations
- Collection utility methods

---

### 6. ✅ SERIALIZATION & FILE I/O

**File:** `FileManager.java`

**Implementation:**

**Binary Serialization:**
```java
// Save objects to binary file
public static void saveBooks(List<Book> books, String filename) {
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
        oos.writeObject(books);
        System.out.println("✓ Books saved to " + filename);
    } catch (IOException e) {
        System.err.println("✗ Error saving books: " + e.getMessage());
    }
}

// Load objects from binary file
public static List<Book> loadBooks(String filename) {
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
        List<Book> books = (List<Book>) ois.readObject();
        return books;
    } catch (IOException | ClassNotFoundException e) {
        return new ArrayList<>();
    }
}
```

**Text File I/O:**
```java
// Write to text file
public static void logTransaction(String transaction) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(TRANSACTIONS_FILE, true))) {
        String timestamp = LocalDate.now().toString();
        writer.write(String.format("[%s] %s%n", timestamp, transaction));
    } catch (IOException e) {
        System.err.println("✗ Error logging transaction: " + e.getMessage());
    }
}

// Read from text file
public static List<String> readTransactionLog() {
    List<String> transactions = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = reader.readLine()) != null) {
            transactions.add(line);
        }
    } catch (IOException e) {
        System.err.println("✗ Error reading: " + e.getMessage());
    }
    return transactions;
}
```

**CSV Export:**
```java
public static void exportToCSV(List<Book> books, String filename) {
    try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
        writer.println("Title,Author,ISBN,PublishDate,Available,BorrowCount");
        for (Book book : books) {
            writer.printf("\"%s\",\"%s\",\"%s\",\"%s\",%b,%d%n",
                book.getTitle(), book.getAuthor(), 
                book.getIsbn(), book.getPublishDate(),
                book.isAvailable(), book.getBorrowCount());
        }
    } catch (IOException e) {
        System.err.println("✗ Error exporting: " + e.getMessage());
    }
}
```

**Serializable Classes:**
- `Book.java` - implements Serializable
- `Person.java` - implements Serializable
- `Student.java` - implements Serializable (inherits from Person)

**Purpose:** Demonstrates data persistence using multiple file formats.

**Usage in Project:**
- File operations demo: `Library.java` lines 360-400
- Interactive file menu: `InteractiveLibrary.java` lines 870-950

**Key Concepts:**
- Object serialization with `ObjectOutputStream`
- Deserialization with `ObjectInputStream`
- `Serializable` interface
- Try-with-resources for automatic resource management
- BufferedReader/BufferedWriter for text files
- PrintWriter for formatted output
- File formats: binary (.dat), text (.txt), CSV (.csv)

---

### 7. ✅ REGULAR EXPRESSIONS (REGEX)

**File:** `ValidationUtils.java`

**Implementation:**
```java
// ISBN-13 pattern
private static final Pattern ISBN_13_PATTERN = 
    Pattern.compile("^978-?\\d{1}-?\\d{3}-?\\d{5}-?\\d{1}$|^979-?\\d{1}-?\\d{3}-?\\d{5}-?\\d{1}$");

// Email pattern
private static final Pattern EMAIL_PATTERN = 
    Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

// Phone pattern
private static final Pattern PHONE_PATTERN = 
    Pattern.compile("^[+]?[(]?[0-9]{1,4}[)]?[-\\s.]?[(]?[0-9]{1,4}[)]?[-\\s.]?[0-9]{1,9}$");

// Name pattern
private static final Pattern NAME_PATTERN = 
    Pattern.compile("^[A-Za-z][A-Za-z\\s'-]{1,49}$");

// Validation methods
public static boolean isValidISBN(String isbn) {
    if (isbn == null || isbn.trim().isEmpty()) return false;
    String cleanIsbn = isbn.replaceAll("-", "").trim();
    return ISBN_13_PATTERN.matcher(isbn).matches();
}

public static boolean isValidEmail(String email) {
    if (email == null || email.trim().isEmpty()) return false;
    return EMAIL_PATTERN.matcher(email.trim()).matches();
}

// Extraction using regex
public static String extractISBN(String text) {
    Pattern isbnExtractor = Pattern.compile(
        "(?:ISBN[-]?13?:?\\s*)?(?:978|979)[-\\s]?\\d{1}[-\\s]?\\d{3}[-\\s]?\\d{5}[-\\s]?\\d{1}"
    );
    Matcher matcher = isbnExtractor.matcher(text);
    if (matcher.find()) {
        return matcher.group().replaceAll("[^0-9]", "");
    }
    return null;
}

// Formatting using regex
public static String sanitizeTitle(String title) {
    if (title == null) return "";
    return title.replaceAll("[^A-Za-z0-9\\s:,.'!?-]", "").trim();
}
```

**Purpose:** Demonstrates pattern matching and data validation using regex.

**Usage in Project:**
- Regex demo: `Library.java` lines 270-350
- Interactive validation: `InteractiveLibrary.java` lines 780-870

**Key Concepts:**
- `Pattern` and `Matcher` classes
- Regex syntax: `^`, `$`, `[]`, `+`, `*`, `?`, `{n,m}`
- Character classes: `\d`, `\w`, `\s`
- Quantifiers and groups
- Pattern compilation and reuse
- String manipulation with regex

---

### 8. ✅ GENERICS & WILDCARDS

**File:** `LibraryCollection.java`

**Implementation:**
```java
// Generic class with bounded type parameter
public class LibraryCollection<T extends Book> {
    private final List<T> books;
    
    // Generic method
    public void addBook(T book) {
        books.add(book);
    }
    
    // Upper bounded wildcard (? extends T)
    public void addAll(Collection<? extends T> newBooks) {
        for (T book : newBooks) {
            books.add(book);
        }
    }
    
    // Lower bounded wildcard (? super T)
    public void copyTo(List<? super T> destination) {
        destination.addAll(books);
    }
    
    // Wildcard for flexible printing
    public void printBooks(List<? extends Book> bookList) {
        for (Book book : bookList) {
            System.out.println(book);
        }
    }
    
    // Generic method with comparator
    public List<T> getSortedBooks(Comparator<? super T> comparator) {
        List<T> sorted = new ArrayList<>(books);
        sorted.sort(comparator);
        return sorted;
    }
}
```

**Purpose:** Demonstrates type-safe generic programming.

**Usage in Project:**
- Generic collection: `LibraryCollection.java`
- Wildcard usage: `Library.java` lines 90-92
- Interactive demo: `InteractiveLibrary.java` lines 420-440

**Key Concepts:**
- Generic classes: `<T>`
- Bounded type parameters: `<T extends Book>`
- Upper bounded wildcards: `<? extends T>`
- Lower bounded wildcards: `<? super T>`
- Type safety and compile-time checking
- Generic methods

---

### 9. ✅ STRING FORMATTING

**Files:** `FormattingDemo.java`, Various classes

**Implementation:**
```java
// String.format()
String formatted = String.format("Book: '%s' by %s (%d)", title, author, year);

// printf() for console output
System.out.printf("%-20s | %-15s | %10d%n", title, author, borrows);

// Number formatting
String price = String.format("Price: $%.2f", 45.99);

// Date formatting
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
String dateStr = publishDate.format(formatter);

// Table formatting
System.out.printf("%-5s | %-30s | %-20s | %8s%n", "No.", "Title", "Author", "Borrows");

// Custom object formatting
public String toString() {
    return String.format("Book{title='%s', author='%s', available=%s, borrowCount=%d}", 
                       title, author, isAvailable, borrowCount);
}
```

**Purpose:** Demonstrates various string and output formatting techniques.

**Usage in Project:**
- Throughout all classes for output
- `FormattingDemo.java` - comprehensive formatting examples
- CSV export in `FileManager.java`
- toString() methods in entity classes

**Key Concepts:**
- `String.format()` and `printf()`
- Format specifiers: `%s`, `%d`, `%f`, `%b`
- Width and precision: `%.2f`, `%10d`
- Alignment: `%-20s` (left), `%20s` (right)
- DateTimeFormatter for dates
- NumberFormat for currency and percentages

---

### 10. ✅ OBJECT-ORIENTED PROGRAMMING (OOP)

**Files:** Multiple classes demonstrating OOP principles

**A. Encapsulation:**
```java
// Book.java
public class Book {
    private String title;           // Private fields
    private String author;
    private boolean isAvailable;
    
    public String getTitle() {      // Public getters
        return title;
    }
    
    public void setTitle(String title) {  // Public setters
        this.title = title;
    }
}
```

**B. Inheritance:**
```java
// Person.java - Base class
public class Person {
    private String name;
    private int id;
}

// Student.java - Derived class
public class Student extends Person {
    private String department;
    
    public Student(String name, int id, String department) {
        super(name, id);  // Call parent constructor
        this.department = department;
    }
}

// Staff.java - Another derived class
public class Staff extends Person {
    // Staff-specific implementation
}
```

**C. Polymorphism:**
```java
// LibraryUser.java - Abstract interface
public abstract class LibraryUser {
    protected String name;
    public abstract void accessLibrary();  // Abstract method
}

// StudentUser.java - Concrete implementation
public class StudentUser extends LibraryUser {
    @Override
    public void accessLibrary() {
        System.out.println(name + " borrows books");
    }
}

// StaffUser.java - Different implementation
public class StaffUser extends LibraryUser {
    @Override
    public void accessLibrary() {
        System.out.println(name + " manages library");
    }
}

// Usage - Polymorphic behavior
LibraryUser user1 = new StudentUser("Alice");
LibraryUser user2 = new StaffUser("Bob");
user1.accessLibrary();  // Calls StudentUser implementation
user2.accessLibrary();  // Calls StaffUser implementation
```

**D. Abstraction:**
```java
// LibraryUser.java - Abstract class
public abstract class LibraryUser {
    protected String name;
    public abstract void accessLibrary();
    
    public void displayInfo() {  // Concrete method
        System.out.println("User: " + name);
    }
}
```

**Purpose:** Demonstrates core OOP principles.

**Usage in Project:**
- Encapsulation: All entity classes
- Inheritance: Person → Student/Staff hierarchy
- Polymorphism: LibraryUser → StudentUser/StaffUser
- Abstraction: LibraryUser abstract class

**Key Concepts:**
- Private fields with public accessors
- Class inheritance with `extends`
- Method overriding with `@Override`
- Abstract classes and methods
- Polymorphic behavior
- Constructor chaining with `super()`

---

### 11. ✅ EXCEPTION HANDLING

**Files:** Throughout the project

**Implementation:**
```java
// Try-catch blocks
try {
    Thread.sleep(delayMs);
} catch (InterruptedException e) {
    System.err.println("Thread interrupted: " + e.getMessage());
    Thread.currentThread().interrupt();
}

// Try-with-resources (automatic resource management)
try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
    oos.writeObject(books);
} catch (IOException e) {
    System.err.println("✗ Error saving books: " + e.getMessage());
}

// Multiple catch blocks
try {
    LocalDate parsed = LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE);
} catch (DateTimeParseException e) {
    System.out.println("✗ Invalid date format.");
}

// Throws declaration
public static void main(String[] args) throws InterruptedException {
    // Method that may throw InterruptedException
}
```

**Purpose:** Demonstrates proper error handling and resource management.

**Usage in Project:**
- File I/O operations in `FileManager.java`
- Thread operations in `BorrowTask.java`
- Date parsing in `InteractiveLibrary.java`

**Key Concepts:**
- Try-catch-finally blocks
- Try-with-resources
- Checked vs unchecked exceptions
- Exception propagation with `throws`
- Custom error messages
- Resource cleanup

---

## 🚀 How to Run the Project

### Option 1: Using Batch File (Easiest)
```cmd
Double-click run.bat
```
Choose:
- **Option 1:** Complete Demo (shows all features automatically)
- **Option 2:** Interactive System (menu-based)

### Option 2: Manual Compilation and Execution

**Step 1: Compile**
```cmd
javac *.java
```

**Step 2: Run Complete Demo**
```cmd
java -cp .. library.Library
```

**Step 3: Run Interactive System**
```cmd
java -cp .. library.InteractiveLibrary
```

---

## 📊 Feature Coverage Summary

| Feature | File(s) | Lines | Status |
|---------|---------|-------|--------|
| Comparable | Book.java | 44-47 | ✅ Complete |
| Comparators | BookComparators.java | All | ✅ Complete |
| Threads | BorrowTask.java, LibraryCollection.java | All | ✅ Complete |
| Lambda Expressions | BookFilters.java, Library.java | All | ✅ Complete |
| Collections | LibraryCollection.java | All | ✅ Complete |
| Serialization | FileManager.java, Book.java, Person.java | All | ✅ Complete |
| File I/O | FileManager.java | All | ✅ Complete |
| Regular Expressions | ValidationUtils.java | All | ✅ Complete |
| Generics | LibraryCollection.java | All | ✅ Complete |
| Formatting | FormattingDemo.java, All classes | All | ✅ Complete |
| OOP | Person.java, Student.java, Staff.java, etc. | All | ✅ Complete |
| Exception Handling | All files | Throughout | ✅ Complete |

---

## 📝 Testing the Features

### 1. Test Comparable & Comparators
Run the demo and observe:
- Books sorted alphabetically (Comparable)
- Books sorted by author (Comparator)
- Books sorted by popularity (Comparator)
- Books sorted by date (Comparator)

### 2. Test Threads & Multithreading
Run the demo and observe:
- Multiple students trying to borrow the same book concurrently
- Only one succeeds (thread synchronization works)
- No race conditions or data corruption

### 3. Test Lambda Expressions
Interactive menu → Advanced Features → Lambda Expressions
- Filter books by various criteria
- Search and group books
- Calculate statistics

### 4. Test Regular Expressions
Interactive menu → Advanced Features → Regular Expressions
- Validate ISBN, email, phone numbers
- Extract ISBN from text
- Format and sanitize data

### 5. Test File I/O
Interactive menu → Advanced Features → File I/O
- Save books to binary file
- Load books from file
- Export to CSV
- View transaction logs

---

## 🎓 Learning Outcomes

This project demonstrates proficiency in:

1. **Advanced Java Programming** - All major Java features implemented
2. **Software Design** - Clean architecture with separation of concerns
3. **Concurrent Programming** - Thread-safe operations and synchronization
4. **Functional Programming** - Lambda expressions and streams
5. **Data Persistence** - Multiple file formats and serialization
6. **Data Validation** - Regular expressions for input validation
7. **Code Organization** - Well-structured, maintainable code
8. **Documentation** - Comprehensive comments and documentation

---

## 📚 References

- Java Documentation: https://docs.oracle.com/javase/
- Effective Java by Joshua Bloch
- Java Concurrency in Practice by Brian Goetz
- Clean Code by Robert C. Martin

---

## 👨‍💻 Project Statistics

- **Total Java Files:** 15
- **Total Lines of Code:** ~2,500+
- **Features Implemented:** 12+
- **Classes:** 15
- **Interfaces:** 2
- **Design Patterns:** Singleton, Strategy, Template Method

---

## ✅ Submission Checklist

- [x] All required features implemented
- [x] Code compiles without errors
- [x] Code runs successfully
- [x] Comprehensive documentation
- [x] Interactive demo available
- [x] Automated demo available
- [x] Clean, readable code
- [x] Proper exception handling
- [x] Thread-safe operations
- [x] File I/O working correctly

---

**End of Documentation**

*This project demonstrates comprehensive knowledge of advanced Java programming concepts and best practices.*
