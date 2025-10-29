// Demonstrates how operator precedence and parentheses affect evaluation
public class OperatorPrecedenceDemo {
    public static void main(String[] args) {
        int a = 2 + 3 * 4;      // * before + -> 14
        int b = (2 + 3) * 4;    // parentheses override -> 20
        System.out.println("2 + 3 * 4 = " + a);
        System.out.println("(2 + 3) * 4 = " + b);

        boolean p = true || false && false;      // && before || -> true || (false && false) => true
        boolean q = (true || false) && false;    // parentheses -> (true) && false => false
        System.out.println("true || false && false = " + p);
        System.out.println("(true || false) && false = " + q);

    // Bitwise vs equality precedence
    int x = 1; // 01
    int y = 2; // 10
    // Caution: without parentheses, an expression like `x & y == 0` is a compile error
    // because it's parsed as `x & (y == 0)` -> int & boolean (invalid).
    // Always parenthesize the bitwise part explicitly:
        boolean r2 = (x & y) == 0;       // 01 & 10 = 00 => 0 == 0 -> true
        System.out.println("(x & y) == 0 -> " + r2);

        // String concatenation associates left-to-right
        System.out.println("\"1+2=\" + 1 + 2 -> " + ("1+2=" + 1 + 2));
        System.out.println("\"1+2=\" + (1 + 2) -> " + ("1+2=" + (1 + 2)));
    }
}
