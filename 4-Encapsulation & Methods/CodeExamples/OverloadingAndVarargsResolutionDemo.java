// Demonstrates overload resolution: widening vs boxing vs varargs, and ambiguity
public class OverloadingAndVarargsResolutionDemo {
    static void f(long x) { System.out.println("f(long)"); }
    static void f(Integer x) { System.out.println("f(Integer)"); }

    static void g(Integer x) { System.out.println("g(Integer)"); }
    static void g(int... xs) { System.out.println("g(int...)"); }

    static void h(Long x) { System.out.println("h(Long)"); }
    static void h(Integer x, String... rest) { System.out.println("h(Integer, String...)"); }

    static void m(String s) { System.out.println("m(String)"); }
    static void m(Object... os) { System.out.println("m(Object...)"); }

    public static void main(String[] args) {
        // Widening preferred over boxing
        f(5); // f(long)

        // Boxing preferred over varargs
        g(5); // g(Integer)

        // Varargs chosen when no better match
        g(); // g(int...)

        // Null can be ambiguous; make it specific with a cast
        // h(null); // ambiguous if only h(Long) and h(Integer) existed
        h((Long) null); // h(Long)

        // Varargs vs exact match
        m("hi"); // m(String)
        m();      // m(Object...)
    }
}
