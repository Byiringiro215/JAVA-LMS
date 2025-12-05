# Interactive Library Management System - User Guide

## Quick Start

### Compile:
```bash
javac library/*.java
```

### Run Interactive Mode:
```bash
java library.InteractiveLibrary
```

### Run Demo Mode (Original):
```bash
java library.Library
```

## Features

### 1. Book Management
- **Add New Book**: Add books with title, author, ISBN, and publish date
- **View All Books**: Display complete book inventory
- **View Available Books**: Show only books available for borrowing
- **Search by Title**: Find books by partial title match
- **Search by ISBN**: Find books by exact ISBN

### 2. Student Operations
- **Register Student**: Create new student accounts with ID and department
- **View All Students**: List all registered students
- **Borrow Book**: Students can borrow available books
- **Return Book**: Return borrowed books to library

### 3. Staff Operations
- **Register Staff**: Create new staff accounts
- **View All Staff**: List all staff members
- **Add Book**: Staff can mark books as available
- **Remove Book**: Staff can mark books as unavailable

### 4. Reports & Statistics
- **Sort by Title**: Uses Comparable (natural ordering)
- **Sort by Author**: Uses Comparator
- **Sort by Popularity**: Uses Comparator (borrow count)
- **Sort by Publish Date**: Uses Comparator
- **Library Statistics**: View comprehensive stats

### 5. Advanced Features Demo
- **Generics**: Demonstrates LibraryCollection<T>
- **Wildcards**: Shows upper/lower bounded wildcards
- **Threads**: Concurrent borrowing simulation
- **All Features**: Complete demonstration

## Sample Workflow

### Scenario 1: Student Borrowing a Book
1. Select `2` (Student Operations)
2. Select `1` (Register New Student)
3. Enter student details
4. Select `3` (Borrow Book)
5. Enter student ID
6. Enter book title from available list

### Scenario 2: Adding a New Book
1. Select `1` (Book Management)
2. Select `1` (Add New Book)
3. Enter book details (title, author, ISBN, date)
4. Book is added to library

### Scenario 3: Viewing Reports
1. Select `4` (View Reports & Statistics)
2. Choose sorting method (1-4)
3. View sorted book list

### Scenario 4: Staff Management
1. Select `3` (Staff Operations)
2. Select `1` (Register New Staff)
3. Enter staff name
4. Use staff ID for book management operations

## Pre-loaded Data

The system comes with 5 books pre-loaded:
- Harry Potter by J.K. Rowling
- Clean Code by Robert C. Martin
- Effective Java by Joshua Bloch
- Design Patterns by Gang of Four
- The Pragmatic Programmer by Andrew Hunt

## Advanced Features Explained

### Generics (LibraryCollection<T>)
- Type-safe collection that works with Book and subclasses
- Prevents runtime ClassCastException
- Provides compile-time type checking

### Wildcards
- **Upper bounded (? extends T)**: Used in `addAll()` for flexibility
- **Lower bounded (? super T)**: Used in `copyTo()` for type safety
- **Unbounded (? extends Book)**: Used in `printBooks()` for display

### Comparable
- Book implements Comparable<Book>
- Natural ordering by title (alphabetical)
- Used in default sorting

### Comparators
- **AuthorComparator**: Sort by author name
- **BorrowCountComparator**: Sort by popularity (descending)
- **PublishDateComparator**: Sort by publication date

### Threads
- BorrowTask implements Runnable
- Demonstrates concurrent book borrowing
- Uses synchronized blocks for thread safety
- ReadWriteLock in LibraryCollection for performance

## Menu Navigation

- Enter the number corresponding to your choice
- Use `0` to go back or exit
- Invalid inputs will prompt for re-entry
- All operations provide feedback messages

## Tips

1. **Register users first**: Create students/staff before performing operations
2. **Check availability**: View available books before borrowing
3. **Use search**: Find books quickly by title or ISBN
4. **View statistics**: Monitor library usage and trends
5. **Try advanced demos**: Learn about Java features in action

## Error Handling

- Invalid menu choices prompt for re-entry
- Non-existent IDs show error messages
- Unavailable books cannot be borrowed
- Date parsing errors are handled gracefully

## Exit

Select `0` from the main menu to exit the system gracefully.
