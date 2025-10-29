// Demonstrates prefix vs postfix ++/-- and valid targets
public class IncrementDecrementDemo {
    static int getValue() { return 5; }

    public static void main(String[] args) {
        int x = 1;
        int a = x++; // a=1, x=2
        int b = ++x; // x=3, b=3
        System.out.println("After x=1; a=x++; b=++x -> a=" + a + ", b=" + b + ", x=" + x);

        int[] arr = { 10, 20 };
        int i = 0;
        arr[i]++; // array element is a variable, valid target
        System.out.println("arr[0] after arr[i]++: " + arr[0]);

        // getValue()++ // does NOT compile: result of a method call is not a variable

        // Subtle expression: avoid using ++ multiple times on the same variable
        int y = 1;
        int tricky = y++ + ++y; // legal but confusing: (1) + (3) => 4, y becomes 3
        System.out.println("tricky result=" + tricky + ", final y=" + y);
    }
}
