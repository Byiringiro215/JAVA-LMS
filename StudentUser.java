package library;

public class StudentUser extends LibraryUser {
    public StudentUser(String name) {
        super(name);
    }
    
    @Override
    public void accessLibrary() {
        System.out.println(name + " borrows books");
    }
}
