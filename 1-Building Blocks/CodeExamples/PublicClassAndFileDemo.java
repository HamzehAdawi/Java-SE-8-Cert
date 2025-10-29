
// Demonstrates the one-public-class-per-file rule and multiple top-level classes
public class PublicClassAndFileDemo {
    public static void main(String[] args) {
        System.out.println("This class is public and matches the file name.");
        Helper helper = new Helper(); // Helper is package-private and allowed in the same file
        helper.sayHi();
    }
}

// package-private (no access modifier) top-level class
class Helper {
    void sayHi() {
        System.out.println("Hello from Helper in the same file! (not public)");
    }
}
