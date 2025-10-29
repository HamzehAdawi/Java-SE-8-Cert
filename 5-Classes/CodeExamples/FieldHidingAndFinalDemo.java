public class FieldHidingAndFinalDemo {
    static class Base {
        public String name = "Base";
        public final void cannotOverride() {
            System.out.println("Base.cannotOverride()");
        }
    }
    static class Sub extends Base {
        public String name = "Sub"; // hides Base.name
        // Cannot override final method; but can overload with different signature
        public void cannotOverride(String msg) {
            System.out.println("Overloaded in Sub: " + msg);
        }
        public void showNames() {
            System.out.println("this.name=" + this.name + ", super.name=" + super.name);
        }
    }

    public static void main(String[] args) {
        Sub s = new Sub();
        s.showNames();
        s.cannotOverride();
        s.cannotOverride("hello");

        Base b = s;
        System.out.println("Via Base ref, field resolves to Base.name: " + b.name);
        // Fields are not polymorphic; they are hidden, not overridden
    }
}
