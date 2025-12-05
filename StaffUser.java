package library;

public class StaffUser extends LibraryUser {
    public StaffUser(String name) {
        super(name);
    }
    
    @Override
    public void accessLibrary() {
        System.out.println(name + " manages library");
    }
}
