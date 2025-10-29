// Demonstrates constructor chaining with this(...) and super(...)
class AnimalBase {
    final String species;
    AnimalBase() {
        this("Unknown");
        System.out.println("AnimalBase(): no-arg");
    }
    AnimalBase(String species) {
        this.species = species;
        System.out.println("AnimalBase(String): " + species);
    }
}

public class ConstructorsChainingAndVisibilityDemo extends AnimalBase {
    final int age;

    public ConstructorsChainingAndVisibilityDemo() {
        this(0); // chain to another ctor; must be first statement
        System.out.println("Child(): no-arg");
    }

    public ConstructorsChainingAndVisibilityDemo(int age) {
        super("Canine"); // must be first if present; mutually exclusive with this(...)
        this.age = age;
        System.out.println("Child(int): age=" + age);
    }

    public static void main(String[] args) {
        System.out.println("-- Using no-arg child --");
        new ConstructorsChainingAndVisibilityDemo();
        System.out.println("-- Using arg child --");
        new ConstructorsChainingAndVisibilityDemo(5);
    }
}
