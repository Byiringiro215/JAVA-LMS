package library;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.text.NumberFormat;
import java.text.DecimalFormat;

/**
 * Demonstrates various formatting techniques in Java
 */
public class FormattingDemo {
    
    /**
     * Demonstrate String formatting
     */
    public static void demonstrateStringFormatting() {
        System.out.println("\n=== STRING FORMATTING ===");
        
        // 1. Basic String.format()
        String bookTitle = "Clean Code";
        String author = "Robert Martin";
        int year = 2008;
        String formatted = String.format("Book: '%s' by %s (%d)", bookTitle, author, year);
        System.out.println("1. Basic format: " + formatted);
        
        // 2. Formatting numbers
        double price = 45.99;
        String priceStr = String.format("Price: $%.2f", price);
        System.out.println("2. Number format: " + priceStr);
        
        // 3. Padding and alignment
        System.out.println("\n3. Table formatting:");
        System.out.printf("%-20s | %-15s | %10s%n", "Title", "Author", "Borrows");
        System.out.println("-".repeat(50));
        System.out.printf("%-20s | %-15s | %10d%n", "Clean Code", "R. Martin", 25);
        System.out.printf("%-20s | %-15s | %10d%n", "Effective Java", "J. Bloch", 18);
        
        // 4. Hexadecimal and octal
        int bookId = 255;
        System.out.println("\n4. Number bases:");
        System.out.printf("   Decimal: %d, Hex: %x, Octal: %o%n", bookId, bookId, bookId);
    }
    
    /**
     * Demonstrate Date/Time formatting
     */
    public static void demonstrateDateTimeFormatting() {
        System.out.println("\n=== DATE/TIME FORMATTING ===");
        
        LocalDate publishDate = LocalDate.of(2008, 8, 1);
        
        // 1. ISO format
        System.out.println("1. ISO format: " + publishDate.toString());
        
        // 2. Custom formats
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("2. Custom (dd/MM/yyyy): " + publishDate.format(formatter1));
        
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        System.out.println("3. Custom (MMMM dd, yyyy): " + publishDate.format(formatter2));
        
        DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.println("4. Custom (yyyy-MM-dd): " + publishDate.format(formatter3));
        
        // 5. Parsing dates
        String dateStr = "2023-12-25";
        LocalDate parsed = LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE);
        System.out.println("5. Parsed date: " + parsed);
    }
    
    /**
     * Demonstrate Number formatting
     */
    public static void demonstrateNumberFormatting() {
        System.out.println("\n=== NUMBER FORMATTING ===");
        
        // 1. Currency formatting
        double amount = 1234.56;
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        System.out.println("1. Currency: " + currencyFormatter.format(amount));
        
        // 2. Percentage formatting
        double percentage = 0.856;
        NumberFormat percentFormatter = NumberFormat.getPercentInstance();
        percentFormatter.setMaximumFractionDigits(2);
        System.out.println("2. Percentage: " + percentFormatter.format(percentage));
        
        // 3. Decimal formatting
        DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
        System.out.println("3. Decimal: " + decimalFormat.format(amount));
        
        // 4. Scientific notation
        double largeNumber = 123456789.123;
        System.out.printf("4. Scientific: %e%n", largeNumber);
    }
    
    /**
     * Demonstrate Collection formatting
     */
    public static void demonstrateCollectionFormatting(List<Book> books) {
        System.out.println("\n=== COLLECTION FORMATTING ===");
        
        // 1. Simple list
        System.out.println("1. Simple list:");
        books.forEach(book -> System.out.println("   - " + book.getTitle()));
        
        // 2. Numbered list
        System.out.println("\n2. Numbered list:");
        for (int i = 0; i < books.size(); i++) {
            System.out.printf("   %d. %s%n", i + 1, books.get(i).getTitle());
        }
        
        // 3. Formatted table
        System.out.println("\n3. Formatted table:");
        System.out.printf("%-5s | %-30s | %-20s | %8s%n", "No.", "Title", "Author", "Borrows");
        System.out.println("-".repeat(70));
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            System.out.printf("%-5d | %-30s | %-20s | %8d%n", 
                i + 1, 
                truncate(book.getTitle(), 30),
                truncate(book.getAuthor(), 20),
                book.getBorrowCount()
            );
        }
        
        // 4. Summary statistics
        System.out.println("\n4. Summary statistics:");
        IntSummaryStatistics stats = books.stream()
            .mapToInt(Book::getBorrowCount)
            .summaryStatistics();
        System.out.printf("   Total books: %d%n", stats.getCount());
        System.out.printf("   Total borrows: %d%n", stats.getSum());
        System.out.printf("   Average: %.2f%n", stats.getAverage());
        System.out.printf("   Min: %d, Max: %d%n", stats.getMin(), stats.getMax());
    }
    
    /**
     * Demonstrate custom object formatting
     */
    public static void demonstrateCustomFormatting(Book book) {
        System.out.println("\n=== CUSTOM OBJECT FORMATTING ===");
        
        // 1. Short format
        System.out.println("1. Short: " + formatBookShort(book));
        
        // 2. Detailed format
        System.out.println("2. Detailed: " + formatBookDetailed(book));
        
        // 3. CSV format
        System.out.println("3. CSV: " + formatBookCSV(book));
        
        // 4. JSON-like format
        System.out.println("4. JSON-like: " + formatBookJSON(book));
    }
    
    // Helper methods
    private static String truncate(String str, int maxLength) {
        if (str == null) return "";
        return str.length() > maxLength ? str.substring(0, maxLength - 3) + "..." : str;
    }
    
    private static String formatBookShort(Book book) {
        return String.format("%s by %s", book.getTitle(), book.getAuthor());
    }
    
    private static String formatBookDetailed(Book book) {
        return String.format("'%s' by %s [ISBN: %s, Published: %s, Borrowed: %d times, %s]",
            book.getTitle(),
            book.getAuthor(),
            book.getIsbn() != null ? book.getIsbn() : "N/A",
            book.getPublishDate() != null ? book.getPublishDate() : "Unknown",
            book.getBorrowCount(),
            book.isAvailable() ? "Available" : "Borrowed"
        );
    }
    
    private static String formatBookCSV(Book book) {
        return String.format("\"%s\",\"%s\",\"%s\",%s,%b,%d",
            book.getTitle(),
            book.getAuthor(),
            book.getIsbn() != null ? book.getIsbn() : "",
            book.getPublishDate() != null ? book.getPublishDate() : "",
            book.isAvailable(),
            book.getBorrowCount()
        );
    }
    
    private static String formatBookJSON(Book book) {
        return String.format(
            "{ \"title\": \"%s\", \"author\": \"%s\", \"isbn\": \"%s\", \"available\": %b, \"borrows\": %d }",
            book.getTitle(),
            book.getAuthor(),
            book.getIsbn() != null ? book.getIsbn() : "",
            book.isAvailable(),
            book.getBorrowCount()
        );
    }
    
    /**
     * Run all formatting demonstrations
     */
    public static void runAllDemos(List<Book> books) {
        System.out.println("\n╔═══════════════════════════════════════════════════════╗");
        System.out.println("║           FORMATTING DEMONSTRATIONS                   ║");
        System.out.println("╚═══════════════════════════════════════════════════════╝");
        
        demonstrateStringFormatting();
        demonstrateDateTimeFormatting();
        demonstrateNumberFormatting();
        
        if (!books.isEmpty()) {
            demonstrateCollectionFormatting(books);
            demonstrateCustomFormatting(books.get(0));
        }
        
        System.out.println("\n✓ All formatting demonstrations complete!");
    }
}
