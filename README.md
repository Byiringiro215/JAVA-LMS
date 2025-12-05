# Library Management System

A comprehensive Java library management system demonstrating advanced OOP concepts including generics, threads, wildcards, comparators, and comparables.

## Project Structure

```
library/
├── Book.java                  - Book entity with Comparable implementation
├── Person.java                - Base person class
├── Student.java               - Student class extending Person
├── Staff.java                 - Staff class extending Person
├── LibraryUser.java           - Abstract base for library users
├── StudentUser.java           - Student user implementation
├── StaffUser.java             - Staff user implementation
├── LibraryCollection.java     - Generic collection with thread-safe operations
├── BookComparators.java       - Custom comparators for sorting
├── BorrowTask.java            - Runnable for concurrent borrowing
└── Library.java               - Main class demonstrating all features
```

## Features Implemented

### 1. Generics
- **LibraryCollection<T extends Book>**: Generic collection class that works with Book and its subclasses
- Type-safe operations for adding, retrieving, and sorting books
- Bounded type parameters ensuring type safety

### 2. Threads
- **BorrowTask**: Implements Runnable for concurrent book borrowing
- Thread-safe operations using synchronized blocks
- ReadWriteLock in LibraryCollection for concurrent access
- Demonstrates race conditions and thread synchronization

### 3. Wildcards
- **Upper bounded wildcards** (`? extends T`): Used in `addAll()` method
- **Lower bounded wildcards** (`? super T`): Used in `copyTo()` method
- **Unbounded wildcards** (`? extends Book`): Used in `printBooks()` method
- Flexible type handling for collections

### 4. Comparable
- **Book implements Comparable<Book>**: Natural ordering by title
- `compareTo()` method for alphabetical sorting
- Used with `Collections.sort()` for default sorting

### 5. Comparators
- **AuthorComparator**: Sorts books by author name
- **BorrowCountComparator**: Sorts by popularity (borrow count)
- **PublishDateComparator**: Sorts by publication date
- Multiple sorting strategies without modifying Book class

## Running the Project

### Compile:
```bash
javac library/*.java
```

### Run Interactive Mode (Recommended):
```bash
java -cp .. library.InteractiveLibrary

```
Interactive console-based system where you can:
- Add/search books
- Register students and staff
- Borrow and return books
- View reports and statistics
- Demonstrate advanced features

### Run Demo Mode:
```bash
java library.Library
```
Automated demonstration of all features with pre-configured data.

See [INTERACTIVE_GUIDE.md](INTERACTIVE_GUIDE.md) for detailed usage instructions.

## Key Concepts Demonstrated

### Generics Benefits
- Type safety at compile time
- Code reusability
- Elimination of type casting

### Thread Safety
- Synchronized methods for critical sections
- ReadWriteLock for better concurrency
- Proper handling of shared resources

### Wildcard Usage
- Flexibility in method parameters
- PECS principle (Producer Extends, Consumer Super)
- Type variance in collections

### Sorting Strategies
- Natural ordering via Comparable
- Custom ordering via Comparator
- Multiple sort criteria support

## Example Output

The program demonstrates:
1. Basic book operations
2. Student borrowing functionality
3. Staff management operations
4. Polymorphic behavior
5. Generic collection operations
6. Comparable sorting (by title)
7. Comparator sorting (by author, borrow count, date)
8. Wildcard operations (copying to different collection types)
9. Concurrent borrowing with threads
10. Adding collections with wildcards

## Thread Safety Features

- `synchronized` keyword on critical methods
- `ReadWriteLock` for concurrent read/write operations
- `ConcurrentHashMap` for thread-safe ISBN lookups
- Proper lock acquisition and release patterns
