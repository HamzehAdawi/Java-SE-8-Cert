

// Demonstrates primitive vs reference variables and assignment behavior
import java.util.Arrays;

public class PrimitivesVsReferencesDemo {
    public static void main(String[] args) {
        int a = 10;
        int b = a; // copy value
        b++;
        System.out.println("Primitive copy: a=" + a + ", b=" + b);

        int[] arr1 = {1, 2, 3};
        int[] arr2 = arr1; // copy reference, both refer to the same array
        arr2[0] = 99;
        System.out.println("Reference copy: arr1=" + Arrays.toString(arr1) + ", arr2=" + Arrays.toString(arr2));

        String s = null; // references can be null
        System.out.println("s is null? " + (s == null));
        s = "hello";
        System.out.println("s length = " + s.length());
    }
}
