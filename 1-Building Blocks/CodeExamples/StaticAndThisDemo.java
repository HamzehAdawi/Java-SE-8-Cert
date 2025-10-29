
// Demonstrates static methods vs instance methods and the 'this' reference
public class StaticAndThisDemo {
    private int instanceCounter = 42;

    public static void main(String[] args) {
        // System.out.println(this.instanceCounter); // Won't compile: 'this' not available in static context
        StaticAndThisDemo demo = new StaticAndThisDemo();
        demo.instanceMethod();
        StaticAndThisDemo.staticMethod();
    }

    void instanceMethod() {
        System.out.println("Instance method: instanceCounter = " + this.instanceCounter);
        // Static members can be accessed from instance methods
        staticMethod();
    }

    static void staticMethod() {
        System.out.println("Static method: called without an instance");
        // Can't directly access instance fields here; need an instance
        // System.out.println(instanceCounter); // Won't compile
    }
}
