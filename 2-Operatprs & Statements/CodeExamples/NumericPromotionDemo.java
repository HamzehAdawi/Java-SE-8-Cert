// Shows numeric promotion, narrowing casts, integer division, overflow, modulo with negatives, and constant expressions
public class NumericPromotionDemo {
    public static void main(String[] args) {
        byte b1 = 10;
        byte b2 = 20;
        // b1 + b2 is an int; explicit cast needed to assign to byte
        byte b3 = (byte) (b1 + b2);
        System.out.println("byte result with cast: " + b3);

        byte x = 3;
        long result = x + 4; // x promoted to int, then widened to long
        System.out.println("x + 4 stored in long: " + result);

        // Constant expression within range can assign to byte without cast
        byte ok = 10 + 20; // compile-time constant
        System.out.println("constant expression to byte: " + ok);
        // int a = 10; byte nope = a + 20; // does not compile (not a constant expression)

        // Integer division truncates toward zero
        System.out.println("7 / 3 = " + (7 / 3));
        System.out.println("-7 / 3 = " + (-7 / 3));

        // Overflow wraps around silently (two's complement)
        int big = Integer.MAX_VALUE;
        System.out.println("overflow example: MAX_VALUE + 1 = " + (big + 1));

        // Modulo with negatives: sign follows the dividend
        System.out.println("-7 % 3 = " + (-7 % 3));

        // Shift promotions: operands promoted to int (or long if left is long)
        char c = 'A'; // 65
        int shifted = c << 1; // c promoted to int before shifting
        System.out.println("'A' << 1 = " + shifted + " (binary shift)");
    }
}
