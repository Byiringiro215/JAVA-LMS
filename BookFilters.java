package library;

import java.time.LocalDate;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

// Demonstrates Lambda Expressions and Functional Programming
public class BookFilters {
    
    // Predicate for filtering available books
    public static final Predicate<Book> IS_AVAILABLE = book -> book.isAvailable();
    
    // Predicate for filtering borrowed books
    public static final Predicate<Book> IS_BORROWED = book -> !book.isAvailable();
    
    // Predicate for popular books (borrowed more than 5 times)
    public static final Predicate<Book> IS_POPULAR = book -> book.getBorrowCount() > 5;
    
    // Function to get book summary
    public static final Function<Book, String> BOOK_SUMMARY = 
        book -> String.format("%s by %s", book.getTitle(), book.getAuthor());
    
    // Function to get book details
    public static final Function<Book, String> BOOK_DETAILS = 
        book -> String.format("%s by %s (ISBN: %s, Borrowed: %d times)",
            book.getTitle(), 
            book.getAuthor(),
            book.getIsbn() != null ? book.getIsbn() : "N/A",
            book.getBorrowCount()
        );
    
    // Consumer for printing book info
    public static final Consumer<Book> PRINT_BOOK = 
        book -> System.out.println("  - " + book);
    
    // BiFunction for comparing books by borrow count
    public static final BiFunction<Book, Book, Integer> COMPARE_BY_POPULARITY = 
        (b1, b2) -> Integer.compare(b2.getBorrowCount(), b1.getBorrowCount());

    /**
     * Filter books using a predicate (Lambda)
     */
    public static List<Book> filterBooks(List<Book> books, Predicate<Book> predicate) {
        return books.stream()
                   .filter(predicate)
                   .collect(Collectors.toList());
    }

    /**
     * Transform books using a function (Lambda)
     */
    public static List<String> transformBooks(List<Book> books, Function<Book, String> transformer) {
        return books.stream()
                   .map(transformer)
                   .collect(Collectors.toList());
    }

    /**
     * Process each book with a consumer (Lambda)
     */
    public static void processBooks(List<Book> books, Consumer<Book> processor) {
        books.forEach(processor);
    }

    /**
     * Find books by author using lambda
     */
    public static List<Book> findByAuthor(List<Book> books, String author) {
        return books.stream()
                   .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                   .collect(Collectors.toList());
    }

    /**
     * Find books published after a certain date using lambda
     */
    public static List<Book> findBooksAfter(List<Book> books, LocalDate date) {
        return books.stream()
                   .filter(book -> book.getPublishDate() != null && book.getPublishDate().isAfter(date))
                   .collect(Collectors.toList());
    }

    /**
     * Get top N most borrowed books using lambda
     */
    public static List<Book> getTopBorrowed(List<Book> books, int n) {
        return books.stream()
                   .sorted((b1, b2) -> Integer.compare(b2.getBorrowCount(), b1.getBorrowCount()))
                   .limit(n)
                   .collect(Collectors.toList());
    }

    /**
     * Count books matching a condition using lambda
     */
    public static long countBooks(List<Book> books, Predicate<Book> condition) {
        return books.stream()
                   .filter(condition)
                   .count();
    }

    /**
     * Check if any book matches condition using lambda
     */
    public static boolean anyMatch(List<Book> books, Predicate<Book> condition) {
        return books.stream().anyMatch(condition);
    }

    /**
     * Check if all books match condition using lambda
     */
    public static boolean allMatch(List<Book> books, Predicate<Book> condition) {
        return books.stream().allMatch(condition);
    }

    /**
     * Group books by author using lambda
     */
    public static Map<String, List<Book>> groupByAuthor(List<Book> books) {
        return books.stream()
                   .collect(Collectors.groupingBy(Book::getAuthor));
    }

    /**
     * Get statistics about borrow counts using lambda
     */
    public static IntSummaryStatistics getBorrowStatistics(List<Book> books) {
        return books.stream()
                   .mapToInt(Book::getBorrowCount)
                   .summaryStatistics();
    }

    /**
     * Find book with maximum borrow count using lambda
     */
    public static Optional<Book> getMostBorrowed(List<Book> books) {
        return books.stream()
                   .max(Comparator.comparingInt(Book::getBorrowCount));
    }

    /**
     * Search books by title pattern (case-insensitive) using lambda
     */
    public static List<Book> searchByTitle(List<Book> books, String searchTerm) {
        return books.stream()
                   .filter(book -> book.getTitle().toLowerCase().contains(searchTerm.toLowerCase()))
                   .collect(Collectors.toList());
    }

    /**
     * Custom filter builder using lambda chaining
     */
    public static Predicate<Book> buildFilter(boolean availableOnly, Integer minBorrowCount, String authorContains) {
        Predicate<Book> filter = book -> true; // Start with always true
        
        if (availableOnly) {
            filter = filter.and(IS_AVAILABLE);
        }
        
        if (minBorrowCount != null) {
            filter = filter.and(book -> book.getBorrowCount() >= minBorrowCount);
        }
        
        if (authorContains != null && !authorContains.isEmpty()) {
            filter = filter.and(book -> book.getAuthor().toLowerCase().contains(authorContains.toLowerCase()));
        }
        
        return filter;
    }

    /**
     * Sort books using custom lambda comparator
     */
    public static List<Book> sortBooks(List<Book> books, Comparator<Book> comparator) {
        return books.stream()
                   .sorted(comparator)
                   .collect(Collectors.toList());
    }

    /**
     * Get unique authors using lambda
     */
    public static Set<String> getUniqueAuthors(List<Book> books) {
        return books.stream()
                   .map(Book::getAuthor)
                   .collect(Collectors.toSet());
    }

    /**
     * Calculate total borrow count using lambda
     */
    public static int getTotalBorrowCount(List<Book> books) {
        return books.stream()
                   .mapToInt(Book::getBorrowCount)
                   .sum();
    }

    /**
     * Partition books into available and borrowed using lambda
     */
    public static Map<Boolean, List<Book>> partitionByAvailability(List<Book> books) {
        return books.stream()
                   .collect(Collectors.partitioningBy(Book::isAvailable));
    }
}
