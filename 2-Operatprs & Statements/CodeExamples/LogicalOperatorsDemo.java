// Demonstrates short-circuit vs non-short-circuit logical operators and XOR, plus NaN equality caveat
public class LogicalOperatorsDemo {
    static boolean left(String label, boolean value) {
        System.out.println("left(" + label + ")");
        return value;
    }
    static boolean right(String label, boolean value) {
        System.out.println("  right(" + label + ")");
        return value;
    }

    public static void main(String[] args) {
        System.out.println("-- Non-short-circuit & and | always evaluate both operands --");
        System.out.println(left("false", false) & right("-", true));
        System.out.println(left("true", true) | right("-", false));

        System.out.println("\n-- Short-circuit && and || may skip RHS --");
        System.out.println(left("false", false) && right("(skipped)", true));
        System.out.println(left("true", true) || right("(skipped)", false));

        System.out.println("\n-- XOR (^) --");
        System.out.println("true ^ false = " + (true ^ false));
        System.out.println("true ^ true  = " + (true ^ true));

        System.out.println("\n-- NaN is not equal to itself --");
        double d = Double.NaN;
        System.out.println("NaN == NaN => " + (d == d));
        System.out.println("NaN != NaN => " + (d != d));
    }
}
