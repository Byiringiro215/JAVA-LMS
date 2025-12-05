package library;

public abstract class LibraryUser {
    public String name;
    
    public LibraryUser(String name) {
        this.name = name;
    }
    
    public abstract void accessLibrary();
}
