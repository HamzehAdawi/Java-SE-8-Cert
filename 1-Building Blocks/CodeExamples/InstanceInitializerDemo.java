

// Demonstrates instance initializer blocks vs. regular blocks inside methods
public class InstanceInitializerDemo {
    { // Instance initializer: runs every time a new object is constructed
        System.out.println("[instance initializer] An instance is being created");
    }

    public static void main(String[] args) {
        System.out.println("Creating two instances...");
        new InstanceInitializerDemo().saySomething();
        new InstanceInitializerDemo().saySomething();
    }

    void saySomething() {
        { // Regular block inside a method: just scopes local variables
            String msg = "[method block] Hello from inside a method block";
            System.out.println(msg);
        }
        // System.out.println(msg); // Won't compile: msg is out of scope here
    }
}
