// Demonstrates: String immutability, String pool, concatenation rules,
// and basic StringBuilder behavior and equality.
public class StringsAndStringBuilderDemo {
    public static void main(String[] args) {
        // String immutability
        String s = "hello";
        s.concat(" world"); // result ignored
        System.out.println("After concat without reassignment: " + s); // "hello"
        s = s.concat(" world");
        System.out.println("After concat with reassignment:  " + s); // "hello world"

        // String pool (== compares references)
        String a = "pool";
        String b = "pool";
        String c = new String("pool");
        System.out.println("a == b (pooled):            " + (a == b)); // true
        System.out.println("a == c (new String object): " + (a == c)); // false

        // Concatenation rules
        System.out.println("Concatenation order:  " + ("Hello" + 1 + 2)); // Hello12
        System.out.println("Addition then concat: " + (1 + 2 + "Hello")); // 3Hello

        // StringBuilder is mutable and uses reference equality for equals()
        StringBuilder sb1 = new StringBuilder("test");
        StringBuilder sb2 = sb1; // same reference
        sb2.append("!");
        System.out.println("sb1 after sb2.append: " + sb1); // "test!"

        StringBuilder x = new StringBuilder("abc");
        StringBuilder y = new StringBuilder("abc");
        System.out.println("x.equals(y) (reference eq): " + x.equals(y)); // false
        System.out.println("x content equals y content: " + x.toString().equals(y.toString())); // true
    }
}
