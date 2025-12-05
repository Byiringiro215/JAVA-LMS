package library;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

// Utility class for validation using Regular Expressions
public class ValidationUtils {
    
    // ISBN-10 pattern: 10 digits with optional hyphens
    private static final Pattern ISBN_10_PATTERN = Pattern.compile("^(?:\\d{1}-?)?\\d{3}-?\\d{5}-?\\d{1}$");
    
    // ISBN-13 pattern: 13 digits with optional hyphens
    private static final Pattern ISBN_13_PATTERN = Pattern.compile("^978-?\\d{1}-?\\d{3}-?\\d{5}-?\\d{1}$|^979-?\\d{1}-?\\d{3}-?\\d{5}-?\\d{1}$");
    
    // Email pattern
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    
    // Phone pattern (various formats)
    private static final Pattern PHONE_PATTERN = Pattern.compile("^[+]?[(]?[0-9]{1,4}[)]?[-\\s.]?[(]?[0-9]{1,4}[)]?[-\\s.]?[0-9]{1,9}$");
    
    // Name pattern (letters, spaces, hyphens, apostrophes)
    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z][A-Za-z\\s'-]{1,49}$");
    
    // Student ID pattern (alphanumeric, 4-10 characters)
    private static final Pattern STUDENT_ID_PATTERN = Pattern.compile("^[A-Z0-9]{4,10}$");

    /**
     * Validate ISBN (supports both ISBN-10 and ISBN-13)
     */
    public static boolean isValidISBN(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) {
            return false;
        }
        
        String cleanIsbn = isbn.replaceAll("-", "").trim();
        
        // Check ISBN-13
        if (cleanIsbn.length() == 13) {
            return ISBN_13_PATTERN.matcher(isbn).matches();
        }
        
        // Check ISBN-10
        if (cleanIsbn.length() == 10) {
            return ISBN_10_PATTERN.matcher(isbn).matches();
        }
        
        return false;
    }

    /**
     * Validate email address
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    /**
     * Validate phone number
     */
    public static boolean isValidPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }
        return PHONE_PATTERN.matcher(phone.trim()).matches();
    }

    /**
     * Validate person name
     */
    public static boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        return NAME_PATTERN.matcher(name.trim()).matches();
    }

    /**
     * Validate student ID
     */
    public static boolean isValidStudentId(String id) {
        if (id == null || id.trim().isEmpty()) {
            return false;
        }
        return STUDENT_ID_PATTERN.matcher(id.trim().toUpperCase()).matches();
    }

    /**
     * Extract ISBN from text
     */
    public static String extractISBN(String text) {
        if (text == null) return null;
        
        // Try to find ISBN-13 first
        Pattern isbnExtractor = Pattern.compile("(?:ISBN[-]?13?:?\\s*)?(?:978|979)[-\\s]?\\d{1}[-\\s]?\\d{3}[-\\s]?\\d{5}[-\\s]?\\d{1}");
        Matcher matcher = isbnExtractor.matcher(text);
        
        if (matcher.find()) {
            return matcher.group().replaceAll("[^0-9]", "");
        }
        
        // Try ISBN-10
        isbnExtractor = Pattern.compile("(?:ISBN[-]?10?:?\\s*)?\\d{1}[-\\s]?\\d{3}[-\\s]?\\d{5}[-\\s]?\\d{1}");
        matcher = isbnExtractor.matcher(text);
        
        if (matcher.find()) {
            return matcher.group().replaceAll("[^0-9]", "");
        }
        
        return null;
    }

    /**
     * Sanitize book title (remove special characters except basic punctuation)
     */
    public static String sanitizeTitle(String title) {
        if (title == null) return "";
        return title.replaceAll("[^A-Za-z0-9\\s:,.'!?-]", "").trim();
    }

    /**
     * Format ISBN with hyphens (ISBN-13 format)
     */
    public static String formatISBN13(String isbn) {
        if (isbn == null) return null;
        
        String clean = isbn.replaceAll("[^0-9]", "");
        if (clean.length() != 13) return isbn;
        
        return String.format("%s-%s-%s-%s-%s",
            clean.substring(0, 3),
            clean.substring(3, 4),
            clean.substring(4, 7),
            clean.substring(7, 12),
            clean.substring(12, 13)
        );
    }

    /**
     * Check if string contains only alphanumeric characters
     */
    public static boolean isAlphanumeric(String str) {
        if (str == null || str.isEmpty()) return false;
        return str.matches("^[A-Za-z0-9]+$");
    }

    /**
     * Validate and format phone number
     */
    public static String formatPhoneNumber(String phone) {
        if (phone == null) return null;
        
        String digits = phone.replaceAll("[^0-9]", "");
        
        if (digits.length() == 10) {
            return String.format("(%s) %s-%s",
                digits.substring(0, 3),
                digits.substring(3, 6),
                digits.substring(6, 10)
            );
        }
        
        return phone;
    }
}
