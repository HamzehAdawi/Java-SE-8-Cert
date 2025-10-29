// Demonstrates ternary operator basics, right-associativity, single-branch evaluation, and type compatibility
public class TernaryDemo {
    static boolean trace(String label, boolean value) {
        System.out.println("trace(" + label + ") called");
        return value;
    }

    public static void main(String[] args) {
        int a = 10;
        int b = 5;

        // Right-associative parsing
        int result = a > b ? a : b > 15 ? 20 : 30; // parsed as a > b ? a : (b > 15 ? 20 : 30)
        int result2 = (a > b) ? a : ((b > 15) ? 20 : 30);
        System.out.println("Right-associative result = " + result + ", result2 = " + result2);

        // Only one of the second or third operands is evaluated
        boolean cond = true;
        boolean value = cond ? trace("true-branch", true) : trace("false-branch", false);
        System.out.println("Value from evaluated branch = " + value);

        // Type compatibility: numeric promotion to a common type
        int i = 1;
        long l = 2L;
    boolean cond2 = (args.length == 42); // runtime-dependent, not a compile-time constant
    long v1 = cond2 ? i : l;   // i is promoted to long, result is long
    long v2 = !cond2 ? i : l;  // result is long (l)
        System.out.println("Ternary numeric promotion: v1=" + v1 + ", v2=" + v2);

        // Note: Using ternary as a standalone statement is illegal (it's an expression).
        // For example, the following does NOT compile:
        // (i > 0) ? 1 : 0; // compile error

        // Valid usage: in an expression context, e.g., return, assignment, print
        System.out.println(i > 0 ? "positive" : "non-positive");
    }
}
