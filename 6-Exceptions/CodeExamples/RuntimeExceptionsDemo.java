public class RuntimeExceptionsDemo {
    static int lengthOf(String s) {
        // May throw NullPointerException at runtime (unchecked)
        return s.length();
    }

    static void validatePositive(int n) {
        if (n < 0) throw new IllegalArgumentException("n must be >= 0");
    }

    public static void main(String[] args) {
        try {
            System.out.println("length: " + lengthOf(null));
        } catch (NullPointerException e) {
            System.out.println("Caught unchecked: " + e.getClass().getSimpleName());
        }

        try {
            validatePositive(-1);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught unchecked: " + e.getClass().getSimpleName() + " - " + e.getMessage());
        }
    }
}
