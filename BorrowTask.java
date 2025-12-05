package library;

// Thread class for concurrent borrowing operations
public class BorrowTask implements Runnable {
    private final Student student;
    private final Book book;
    private final int delayMs;

    public BorrowTask(Student student, Book book, int delayMs) {
        this.student = student;
        this.book = book;
        this.delayMs = delayMs;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(delayMs);
            synchronized (book) {
                student.borrowBook(book);
            }
        } catch (InterruptedException e) {
            System.err.println("Borrow task interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
