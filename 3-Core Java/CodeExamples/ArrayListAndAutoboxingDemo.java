// Demonstrates: ArrayList basics, remove(int) vs remove(Object) autoboxing gotcha,
// contains/equals, and sorting with Collections.sort.
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArrayListAndAutoboxingDemo {
    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        System.out.println("Start:        " + numbers); // [1, 2, 3]

        numbers.remove(1); // removes element at index 1 (value 2)
        System.out.println("remove(1):    " + numbers); // [1, 3]

        numbers.remove(Integer.valueOf(1)); // removes the Integer value 1
        System.out.println("remove(val 1): " + numbers); // [3]

        numbers.add(5);
        numbers.add(4);
        System.out.println("Unsorted:      " + numbers); // [3, 5, 4]
        Collections.sort(numbers);
        System.out.println("Sorted:        " + numbers); // [3, 4, 5]

        System.out.println("contains 4?    " + numbers.contains(4));
        System.out.println("equals [3,4,5]? " + numbers.equals(List.of(3,4,5)));
    }
}
