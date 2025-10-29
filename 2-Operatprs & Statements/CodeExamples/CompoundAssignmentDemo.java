import java.util.Arrays;

// Shows how compound assignments include an implicit cast and evaluate the LHS only once
public class CompoundAssignmentDemo {
    public static void main(String[] args) {
        int x = 1;
        long L = 2L;

        // x = x * L; // does NOT compile: x*L is long; narrowing to int requires an explicit cast
        x *= L; // OK: equivalent to x = (int)(x * L)
        System.out.println("x after x *= 2L -> " + x);

        // Overflow still wraps in implicit cast
        short s = 30_000;
        s += 30_000; // wraps within short range
        System.out.println("short wrap after s += 30000 -> " + (int)s);

        // LHS evaluated once
        int[] arr = {1, 2, 3};
        int i = 0;

        int[] copy1 = Arrays.copyOf(arr, arr.length);
        i = 0;
        copy1[i] = copy1[i++] * 2; // i used twice on the LHS/RHS in separate evaluations
        System.out.println("arr after arr[i] = arr[i++] * 2 -> " + Arrays.toString(copy1) + ", i=" + i);

        int[] copy2 = Arrays.copyOf(arr, arr.length);
        i = 0;
        copy2[i++] *= 2; // i is evaluated once for the LHS location
        System.out.println("arr after arr[i++] *= 2 -> " + Arrays.toString(copy2) + ", i=" + i);
    }
}
