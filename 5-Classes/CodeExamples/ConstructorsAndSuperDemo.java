public class ConstructorsAndSuperDemo {
    static class Mammal {
        public Mammal() {
            System.out.println("Mammal(): no-arg constructor");
        }
        public Mammal(int age) {
            System.out.println("Mammal(int): age=" + age);
        }
    }

    static class Dog extends Mammal {
        public Dog() {
            super(5); // explicitly call parent
            System.out.println("Dog(): default constructor");
        }
        public Dog(int age) {
            super(age);
            System.out.println("Dog(int): age=" + age);
        }
    }

    public static void main(String[] args) {
        System.out.println("-- new Dog() --");
        new Dog();

        System.out.println("-- new Dog(2) --");
        new Dog(2);
    }
}
