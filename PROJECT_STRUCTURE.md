# Library Management System - Project Structure

```
library/
│
├── Core Classes
│   ├── Book.java                      - Book entity (implements Comparable<Book>)
│   ├── Person.java                    - Base person class
│   ├── Student.java                   - Student class (extends Person)
│   ├── Staff.java                     - Staff class (extends Person)
│   ├── LibraryUser.java               - Abstract base for library users
│   ├── StudentUser.java               - Student user implementation
│   └── StaffUser.java                 - Staff user implementation
│
├── Generic Collections
│   └── LibraryCollection.java         - Generic collection with thread-safe operations
│                                        Uses: LibraryCollection<T extends Book>
│
├── Comparators
│   └── BookComparators.java           - Custom comparators for sorting
│       ├── AuthorComparator           - Sort by author name
│       ├── BorrowCountComparator      - Sort by popularity
│       └── PublishDateComparator      - Sort by publish date
│
├── Threading
│   └── BorrowTask.java                - Runnable for concurrent borrowing operations
│
├── Main Applications
│   ├── Library.java                   - Demo mode (automated demonstration)
│   └── InteractiveLibrary.java        - Interactive console application
│
├── Documentation
│   ├── README.md                      - Project overview and features
│   ├── INTERACTIVE_GUIDE.md           - User guide for interactive mode
│   └── PROJECT_STRUCTURE.md           - This file
│
└── Batch Scripts (Windows)
    ├── run-demo.bat                   - Run automated demo
    └── run-interactive.bat            - Run interactive mode

```

## Class Relationships

```
Person (base class)
├── Student (extends Person)
└── Staff (extends Person)

LibraryUser (abstract)
├── StudentUser (extends LibraryUser)
└── StaffUser (extends LibraryUser)

Book (implements Comparable<Book>, Serializable)

LibraryCollection<T extends Book> (generic class)
├── Uses ReadWriteLock for thread safety
├── Uses ConcurrentHashMap for ISBN lookups
└── Supports wildcards (? extends T, ? super T)

BorrowTask (implements Runnable)
└── Used for concurrent borrowing operations

BookComparators (utility class)
├── AuthorComparator (implements Comparator<Book>)
├── BorrowCountComparator (implements Comparator<Book>)
└── PublishDateComparator (implements Comparator<Book>)
```

## Key Design Patterns

### 1. Generic Type Parameters
- `LibraryCollection<T extends Book>` - Bounded type parameter
- Ensures type safety while allowing flexibility

### 2. Comparable Pattern
- `Book implements Comparable<Book>`
- Natural ordering by title

### 3. Comparator Pattern
- Multiple sorting strategies without modifying Book class
- Strategy pattern for sorting algorithms

### 4. Thread Safety
- Synchronized methods for critical sections
- ReadWriteLock for concurrent access
- ConcurrentHashMap for thread-safe lookups

### 5. Wildcard Usage
- Upper bounded: `? extends T` (producer)
- Lower bounded: `? super T` (consumer)
- PECS principle (Producer Extends, Consumer Super)

## Feature Matrix

| Feature | Class/File | Description |
|---------|-----------|-------------|
| Generics | LibraryCollection.java | Type-safe collection with bounded parameters |
| Comparable | Book.java | Natural ordering implementation |
| Comparator | BookComparators.java | Custom sorting strategies |
| Threads | BorrowTask.java | Concurrent operations |
| Wildcards | LibraryCollection.java | Flexible type handling |
| Synchronization | Book.java, LibraryCollection.java | Thread-safe operations |
| Inheritance | Person, Student, Staff | OOP hierarchy |
| Polymorphism | LibraryUser hierarchy | Abstract classes |
| Encapsulation | All entity classes | Private fields with getters/setters |

## Compilation Order

The Java compiler handles dependencies automatically, but the logical order is:

1. Base classes: Person, LibraryUser, Book
2. Extended classes: Student, Staff, StudentUser, StaffUser
3. Utility classes: BookComparators, BorrowTask
4. Generic collection: LibraryCollection
5. Main applications: Library, InteractiveLibrary

## Running Instructions

### Option 1: Using Batch Files (Windows)
```bash
# For interactive mode
run-interactive.bat

# For demo mode
run-demo.bat
```

### Option 2: Manual Compilation
```bash
# Compile all files
javac library/*.java

# Run interactive mode
java library.InteractiveLibrary

# Run demo mode
java library.Library
```

### Option 3: From Parent Directory
```bash
# Compile
javac library/*.java

# Run
java library.InteractiveLibrary
```

## Dependencies

- Java 8 or higher (for LocalDate, lambda expressions)
- No external libraries required
- Uses only Java standard library

## Thread Safety Features

1. **Synchronized Methods**: `setAvailable()`, `incrementBorrowCount()`
2. **ReadWriteLock**: In LibraryCollection for concurrent reads
3. **ConcurrentHashMap**: For thread-safe ISBN lookups
4. **Synchronized Blocks**: In BorrowTask for borrowing operations

## Extensibility

The system is designed to be easily extended:

- Add new Book subclasses (e.g., EBook, AudioBook)
- Create new Comparators for different sorting criteria
- Implement new LibraryUser types
- Add more concurrent operations with new Runnable tasks
