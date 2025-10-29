public class MultiCatchDemo {
    public static int parseAndDivide(String text, int divisor) {
        try {
            int value = Integer.parseInt(text); // may throw NumberFormatException
            return value / divisor;             // may throw ArithmeticException
        } catch (NumberFormatException | ArithmeticException e) { // multi-catch (Java 7+)
            System.out.println("Handled: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            return 0;
        }
    }

    public static void main(String[] args) {
        parseAndDivide("not a number", 2); // NumberFormatException
        parseAndDivide("10", 0);           // ArithmeticException

        // Proper ordering: specific before general
        try {
            throw new IllegalArgumentException("bad arg");
        } catch (IllegalArgumentException e) { // specific
            System.out.println("Caught specific first");
        } catch (RuntimeException e) { // general
            System.out.println("Caught general second");
        }

        // Uncompilable example (do NOT uncomment):
        // catch (RuntimeException e) { }
        // catch (IllegalArgumentException e) { } // unreachable
    }
}
