// Demonstrates lambdas, method references, and Predicate composition with removeIf
import java.util.*;
import java.util.function.*;

public class LambdaAndPredicateDemo {
    public static void main(String[] args) {
        List<String> names = new ArrayList<>(Arrays.asList("Alice", "Bob", "Alex", "Max"));
        System.out.println("Start:   " + names);

        Predicate<String> startsWithA = s -> s.startsWith("A");
        Predicate<String> endsWithX = s -> s.endsWith("x");
        Predicate<String> combined = startsWithA.and(endsWithX).negate();

        names.removeIf(combined.negate()); // keep only those matching combined
        System.out.println("Filtered: " + names);

        // Method references examples
        Function<String, Integer> parse = Integer::valueOf; // may throw for non-numeric
        Supplier<String> supplier = () -> "42";
        System.out.println("Parsed from supplier: " + parse.apply(supplier.get()));

        // Calculator via lambda
        BinaryOperator<Integer> add = (a, b) -> a + b;
        System.out.println("1 + 2 = " + add.apply(1, 2));
    }
}
