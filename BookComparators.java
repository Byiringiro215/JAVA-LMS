package library;

import java.util.Comparator;

public class BookComparators {
    
    // Comparator for sorting by author
    public static class AuthorComparator implements Comparator<Book> {
        @Override
        public int compare(Book b1, Book b2) {
            return b1.getAuthor().compareToIgnoreCase(b2.getAuthor());
        }
    }

    // Comparator for sorting by borrow count (most borrowed first)
    public static class BorrowCountComparator implements Comparator<Book> {
        @Override
        public int compare(Book b1, Book b2) {
            return Integer.compare(b2.getBorrowCount(), b1.getBorrowCount());
        }
    }

    // Comparator for sorting by publish date
    public static class PublishDateComparator implements Comparator<Book> {
        @Override
        public int compare(Book b1, Book b2) {
            if (b1.getPublishDate() == null && b2.getPublishDate() == null) return 0;
            if (b1.getPublishDate() == null) return 1;
            if (b2.getPublishDate() == null) return -1;
            return b1.getPublishDate().compareTo(b2.getPublishDate());
        }
    }
}
