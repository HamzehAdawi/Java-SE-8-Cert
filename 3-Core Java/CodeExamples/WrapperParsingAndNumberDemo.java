// Demonstrates: Wrapper parsing/valueOf, Number methods, and handling NumberFormatException.

public class WrapperParsingAndNumberDemo {
    public static void main(String[] args) {
        int p = Integer.parseInt("123"); // primitive
        Integer w = Integer.valueOf("456"); // wrapper
        System.out.println("parseInt(\"123\"): " + p);
        System.out.println("valueOf(\"456\"):  " + w);

        // Number methods from wrappers
        Double d = 12.34; // autoboxed
        System.out.println("double as int:   " + d.intValue());
        System.out.println("double as long:  " + d.longValue());
        System.out.println("double as float: " + d.floatValue());

        // Invalid numeric string -> NumberFormatException
        try {
            Long bad = Long.valueOf("hi");
            System.out.println("Should not print: " + bad);
        } catch (NumberFormatException e) {
            System.out.println("Caught NumberFormatException for input 'hi': " + e.getMessage());
        }
    }
}
