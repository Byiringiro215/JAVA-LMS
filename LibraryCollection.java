package library;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

// Generic collection class with thread-safe operations
public class LibraryCollection<T extends Book> {
    private final List<T> books;
    private final Map<String, T> booksByIsbn;
    private final ReadWriteLock lock;

    public LibraryCollection() {
        this.books = new ArrayList<>();
        this.booksByIsbn = new ConcurrentHashMap<>();
        this.lock = new ReentrantReadWriteLock();
    }

    // Add a book (thread-safe)
    public void addBook(T book) {
        lock.writeLock().lock();
        try {
            books.add(book);
            if (book.getIsbn() != null) {
                booksByIsbn.put(book.getIsbn(), book);
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    // Add multiple books using wildcards
    public void addAll(Collection<? extends T> newBooks) {
        lock.writeLock().lock();
        try {
            for (T book : newBooks) {
                books.add(book);
                if (book.getIsbn() != null) {
                    booksByIsbn.put(book.getIsbn(), book);
                }
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    // Get all books (thread-safe)
    public List<T> getAllBooks() {
        lock.readLock().lock();
        try {
            return new ArrayList<>(books);
        } finally {
            lock.readLock().unlock();
        }
    }

    // Find book by ISBN
    public Optional<T> findByIsbn(String isbn) {
        return Optional.ofNullable(booksByIsbn.get(isbn));
    }

    // Get available books
    public List<T> getAvailableBooks() {
        lock.readLock().lock();
        try {
            List<T> available = new ArrayList<>();
            for (T book : books) {
                if (book.isAvailable()) {
                    available.add(book);
                }
            }
            return available;
        } finally {
            lock.readLock().unlock();
        }
    }

    // Sort books using Comparable (natural ordering)
    public List<T> getSortedBooks() {
        lock.readLock().lock();
        try {
            List<T> sorted = new ArrayList<>(books);
            Collections.sort(sorted);
            return sorted;
        } finally {
            lock.readLock().unlock();
        }
    }

    // Sort books using Comparator
    public List<T> getSortedBooks(Comparator<? super T> comparator) {
        lock.readLock().lock();
        try {
            List<T> sorted = new ArrayList<>(books);
            sorted.sort(comparator);
            return sorted;
        } finally {
            lock.readLock().unlock();
        }
    }

    // Wildcard method - accepts any collection of books or subclasses
    public void printBooks(List<? extends Book> bookList) {
        for (Book book : bookList) {
            System.out.println(book);
        }
    }

    // Wildcard method - can add to any list that accepts Book or its superclasses
    public void copyTo(List<? super T> destination) {
        lock.readLock().lock();
        try {
            destination.addAll(books);
        } finally {
            lock.readLock().unlock();
        }
    }

    public int size() {
        lock.readLock().lock();
        try {
            return books.size();
        } finally {
            lock.readLock().unlock();
        }
    }
}
