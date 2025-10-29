public class CheckedAndThrowsDemo {
    static void riskyIo() throws java.io.IOException {
        throw new java.io.IOException("IO failure (checked)");
    }

    public static void main(String[] args) {
        try {
            riskyIo();
        } catch (java.io.IOException e) {
            System.out.println("Caught checked: " + e.getClass().getSimpleName() + " - " + e.getMessage());
        }
    }
}
