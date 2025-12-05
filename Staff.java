package library;

public class Staff extends Person {
    public Staff(String name, int id) {
        super(name, id);
    }

    public void manageBook(Book b, boolean add) {
        if (add) {
            b.setAvailable(true);
            System.out.println(getName() + " added the book \"" + b.getTitle() + "\" back to library.");
        } else {
            b.setAvailable(false);
            System.out.println(getName() + " removed the book \"" + b.getTitle() + "\" from library.");
        }
    }

    @Override
    public String toString() {
        return String.format("Staff{name='%s', id=%d}", getName(), getId());
    }
}
