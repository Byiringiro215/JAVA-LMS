package library;

import java.io.Serializable;
import java.time.LocalDate;

public class Book implements Comparable<Book>, Serializable {
    private String title;
    private String author;
    private boolean isAvailable = true;
    private String isbn;
    private LocalDate publishDate;
    private int borrowCount = 0;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public Book(String title, String author, String isbn, LocalDate publishDate) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publishDate = publishDate;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public boolean isAvailable() { return isAvailable; }
    public String getIsbn() { return isbn; }
    public LocalDate getPublishDate() { return publishDate; }
    public int getBorrowCount() { return borrowCount; }

    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public synchronized void setAvailable(boolean available) { 
        this.isAvailable = available; 
    }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public void setPublishDate(LocalDate publishDate) { this.publishDate = publishDate; }
    public synchronized void incrementBorrowCount() { this.borrowCount++; }

    // Comparable implementation - natural ordering by title
    @Override
    public int compareTo(Book other) {
        return this.title.compareToIgnoreCase(other.title);
    }

    @Override
    public String toString() {
        return String.format("Book{title='%s', author='%s', available=%s, borrowCount=%d}", 
                           title, author, isAvailable, borrowCount);
    }
}
