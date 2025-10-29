public class CatchAndFinallyFlowDemo {
    static void doWork() {
        try {
            System.out.println("try");
            throw new IllegalArgumentException("from try");
        } catch (IllegalArgumentException e) {
            System.out.println("catch: " + e.getMessage());
            throw new RuntimeException("rethrow from catch");
        } finally {
            System.out.println("finally runs");
        }
    }

    public static void main(String[] args) {
        try {
            doWork();
        } catch (RuntimeException e) {
            System.out.println("Caught in main: " + e.getMessage());
        }
    }
}
