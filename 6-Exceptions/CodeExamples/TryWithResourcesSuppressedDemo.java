class NoisyResource implements AutoCloseable {
    @Override
    public void close() throws Exception {
        throw new Exception("close() failure");
    }
}

public class TryWithResourcesSuppressedDemo {
    public static void main(String[] args) {
        try (NoisyResource r = new NoisyResource()) {
            // Use the resource so it's not flagged as unused
            System.out.println("Using resource: " + r);
            throw new Exception("primary failure");
        } catch (Exception e) {
            System.out.println("Caught: " + e.getMessage());
            for (Throwable t : e.getSuppressed()) {
                System.out.println("Suppressed: " + t.getMessage());
            }
        }
    }
}
