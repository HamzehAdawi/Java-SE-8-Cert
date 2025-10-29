public class OverridingVsHidingDemo {
    static class Parent {
        static void who() { System.out.println("Parent.who (static)"); }
        void sayHello() { System.out.println("Parent.sayHello (instance)"); }
        void parentMethodCallsStatic() { who(); }
    }

    static class Child extends Parent {
        static void who() { System.out.println("Child.who (static)"); } // hides
        @Override void sayHello() { System.out.println("Child.sayHello (instance)"); }
        void childMethodCallsStatic() { who(); }
    }

    public static void main(String[] args) {
        Parent p = new Child();
        System.out.println("-- Parent reference to Child object --");
    Parent.who();         // static: resolved by reference/class type, here explicitly by class
        p.sayHello();         // overridden -> Child.sayHello

        Child c = new Child();
        System.out.println("-- Calls inside methods --");
        c.parentMethodCallsStatic(); // resolves where defined -> Parent.who
        c.childMethodCallsStatic();  // resolves where defined -> Child.who

        System.out.println("-- Qualifying by class --");
        Parent.who();
        Child.who();
    }
}
