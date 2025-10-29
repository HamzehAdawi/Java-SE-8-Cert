abstract class AbstractAnimal {
    abstract void sound();
    void eat() { System.out.println("Animal is eating"); }
}

interface CanSwim {
    default void swim() { System.out.println("Swimming by default"); }
    static String speciesInfo() { return "Interface static: aquatic-capable"; }
}

public class AbstractAndInterfaceDemo {
    static class Duck extends AbstractAnimal implements CanSwim {
        @Override void sound() { System.out.println("Duck says: quack"); }
        // Optionally override default method
        @Override public void swim() { System.out.println("Duck paddles"); }
    }

    public static void main(String[] args) {
        Duck d = new Duck();
        d.sound();
        d.eat();
        d.swim();
        System.out.println(CanSwim.speciesInfo());
    }
}
