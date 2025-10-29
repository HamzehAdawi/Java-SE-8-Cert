public class PolymorphismAndCastingDemo {
    static class Primate {
        public boolean hasHair() { return true; }
    }
    interface HasTail { boolean isTailStriped(); }
    static class Lemur extends Primate implements HasTail {
        public int age = 10;
        public boolean isTailStriped() { return true; }
    }

    public static void main(String[] args) {
        Lemur lemur = new Lemur();
        System.out.println("lemur.age=" + lemur.age);

        HasTail tailRef = lemur;           // upcast to interface
        System.out.println("tailRef.isTailStriped()=" + tailRef.isTailStriped());

        Primate primateRef = lemur;        // upcast to parent
        System.out.println("primateRef.hasHair()=" + primateRef.hasHair());

        // Safe downcast with instanceof
        if (primateRef instanceof Lemur) {
            Lemur back = (Lemur) primateRef;
            System.out.println("downcast age=" + back.age);
        }

        // Unsafe downcast example (DO NOT DO THIS):
        Object obj = new Primate();
        if (obj instanceof Lemur) {
            Lemur bad = (Lemur) obj; // won't execute
            System.out.println(bad.age);
        } else {
            System.out.println("obj is not a Lemur; skipping unsafe cast");
        }
    }
}
