public class FinallyOverrideExceptionDemo {
    static void doWork() {
        try {
            throw new IllegalStateException("from try");
        } finally {
            // The exception thrown here replaces the one from try
            throw new RuntimeException("from finally");
        }
    }

    public static void main(String[] args) {
        try {
            doWork();
        } catch (RuntimeException e) {
            System.out.println("Final thrown: " + e.getMessage()); // prints "from finally"
        }
    }
}
