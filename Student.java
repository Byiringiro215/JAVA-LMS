package library;

import java.io.Serializable;

public class Student extends Person implements Serializable {
    private String department;

    public Student(String name, int id, String department) {
        super(name, id);
        this.department = department;
    }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public void borrowBook(Book b) {
        if (b.isAvailable()) {
            b.setAvailable(false);
            b.incrementBorrowCount();
            System.out.println(getName() + " successfully borrowed \"" + b.getTitle() + "\".");
        } else {
            System.out.println("Sorry, \"" + b.getTitle() + "\" is not available.");
        }
    }

    @Override
    public String toString() {
        return String.format("Student{name='%s', id=%d, department='%s'}", 
                           getName(), getId(), department);
    }
}
