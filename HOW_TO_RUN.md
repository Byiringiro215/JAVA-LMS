# How to Run the Library Management System

## Quick Start (Easiest Way)

**Double-click `run.bat`** and choose:
- Option 1: Complete Demo (shows all features automatically)
- Option 2: Interactive System (menu-based interaction)

---

## Manual Commands

### Step 1: Compile
```cmd
javac *.java
```

### Step 2: Run

**Option A - Complete Demo (All Features Shown Automatically):**
```cmd
java -cp .. library.Library
```

This will demonstrate:
- Comparable & Comparators
- Threads & Multithreading
- Lambda Expressions
- Regular Expressions (Regex)
- File I/O Operations
- Generics & Wildcards

**Option B - Interactive System (Menu-Based):**
```cmd
java -cp .. library.InteractiveLibrary
```

This lets you:
- Add/view/search books
- Register students and staff
- Borrow and return books
- View reports and statistics
- **Interactively test Lambda Expressions** (filter, search, group books)
- **Interactively test Regular Expressions** (validate ISBN, email, phone, etc.)
- **Interactively test File I/O** (save/load books, manage transaction logs)

---

## What Each File Does

| File | Purpose |
|------|---------|
| `Library.java` | Complete automated demo of all features |
| `InteractiveLibrary.java` | Interactive menu-based system |
| `Book.java` | Book entity with Comparable |
| `BookComparators.java` | Custom comparators for sorting |
| `BookFilters.java` | Lambda expressions for filtering |
| `FileManager.java` | File I/O operations |
| `ValidationUtils.java` | Regex validation utilities |
| `BorrowTask.java` | Thread for concurrent borrowing |
| `LibraryCollection.java` | Generic thread-safe collection |

---

## Features Demonstrated

✅ **Comparable** - Natural ordering by title  
✅ **Comparators** - Custom sorting (author, popularity, date)  
✅ **Threads** - Concurrent borrowing with synchronization  
✅ **Lambda Expressions** - Functional programming with streams  
✅ **File I/O** - Serialization, text files, CSV export  
✅ **Regular Expressions** - ISBN, email, phone validation  
✅ **Generics** - Type-safe collections  
✅ **Wildcards** - Flexible type handling  

---

## Troubleshooting

**Error: "Could not find or load main class"**
- Make sure you're using `-cp ..` to set the classpath
- Run from inside the `library` folder

**Error: "Invalid filename: library/*.java"**
- You're already in the library folder
- Just use `javac *.java` without the `library/` prefix

**Files created during execution:**
- `library_books.dat` - Serialized book data
- `library_transactions.txt` - Transaction log
- `library_export.csv` - CSV export of books
