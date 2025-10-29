// Demonstrates encapsulation and defensive copying for immutability
import java.util.Arrays;
import java.util.Date;

final class Person {
    private final String name;
    private final Date dob; // mutable type
    private final int[] scores; // mutable array

    public Person(String name, Date dob, int[] scores) {
        this.name = name;
        this.dob = new Date(dob.getTime()); // defensive copy
        this.scores = scores.clone();        // defensive copy
    }

    public String getName() { return name; }
    public Date getDob() { return new Date(dob.getTime()); } // return copy
    public int[] getScores() { return scores.clone(); }       // return copy

    @Override public String toString() {
        return name + ", " + dob + ", " + Arrays.toString(scores);
    }
}

public class EncapsulationImmutableDefensiveCopyDemo {
    public static void main(String[] args) {
        Date dob = new Date(0L);
        int[] scores = { 90, 80, 70 };
        Person p = new Person("Alice", dob, scores);
        System.out.println("Original:   " + p);

        // Try mutating inputs after construction
        dob.setTime(1000L);
        scores[0] = 0;
        System.out.println("After mutate originals: " + p); // unchanged

        // Try mutating returned references
        p.getDob().setTime(2000L);
        int[] copy = p.getScores();
        copy[1] = 0;
        System.out.println("After mutate accessors: " + p); // unchanged
    }
}
