import java.util.Arrays;
import java.util.Objects;

// Shows == vs .equals() for primitives, Strings, arrays, StringBuilder, and null-safety
public class EqualityDemo {
    public static void main(String[] args) {
        int a = 5, b = 5;
        System.out.println("primitives: 5 == 5 -> " + (a == b));

        String s1 = "hello";
        String s2 = "hello";
        String s3 = new String("hello");
        System.out.println("s1 == s2 (interned literals) -> " + (s1 == s2));
        System.out.println("s1 == s3 (different objects) -> " + (s1 == s3));
        System.out.println("s1.equals(s3) (same contents) -> " + s1.equals(s3));

        String x = "Hello";
        String y = "He";
        y += "llo"; // concatenation creates a new object
        System.out.println("y == x (after concat) -> " + (y == x));
        System.out.println("y.equals(x) -> " + y.equals(x));

        int[] arr1 = {1, 2, 3};
        int[] arr2 = {1, 2, 3};
        System.out.println("arr1 == arr2 -> " + (arr1 == arr2));
        System.out.println("Arrays.equals(arr1, arr2) -> " + Arrays.equals(arr1, arr2));

        StringBuilder sb1 = new StringBuilder("hi");
        StringBuilder sb2 = new StringBuilder("hi");
        System.out.println("sb1.equals(sb2) (reference equality) -> " + sb1.equals(sb2));
        System.out.println("sb1.toString().equals(sb2.toString()) -> " + sb1.toString().equals(sb2.toString()));

        String n = null;
        System.out.println("Objects.equals(null, \"x\") -> " + Objects.equals(n, "x"));
        System.out.println("Objects.equals(null, null) -> " + Objects.equals(n, null));
    }
}
