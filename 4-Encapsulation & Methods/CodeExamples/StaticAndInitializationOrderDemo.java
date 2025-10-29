// Demonstrates static initializer order and instance initialization order
public class StaticAndInitializationOrderDemo {
    static {
        System.out.println("[Static block] class is initializing");
    }

    static int staticField = print("[Static field] initialized");

    int instanceField = print("  [Instance field] initialized");

    {
        print("  [Instance block] runs before constructor");
    }

    public StaticAndInitializationOrderDemo() {
        print("  [Constructor] runs last for this instance");
    }

    static int print(String msg) {
        System.out.println(msg);
        return 0;
    }

    public static void main(String[] args) {
        System.out.println("-- first instance --");
        new StaticAndInitializationOrderDemo();
        System.out.println("-- second instance --");
        new StaticAndInitializationOrderDemo();
    }
}
