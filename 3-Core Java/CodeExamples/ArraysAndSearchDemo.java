// Demonstrates: Arrays utility methods (sort, binarySearch),
// insertion point on not-found, and simple 2D arrays.
import java.util.Arrays;

public class ArraysAndSearchDemo {
    public static void main(String[] args) {
        int[] nums = {3, 1, 4, 1, 5};
        System.out.println("Original: " + Arrays.toString(nums));

        Arrays.sort(nums);
        System.out.println("Sorted:   " + Arrays.toString(nums));

        int idxFound = Arrays.binarySearch(nums, 4);
        int idxNotFound = Arrays.binarySearch(nums, 2);
        System.out.println("binarySearch 4 -> index: " + idxFound);
        System.out.println("binarySearch 2 -> result: " + idxNotFound +
                ", insertionPoint: " + (-idxNotFound - 1));

        // 2D arrays and deepToString
        int[][] grid = new int[2][3];
        grid[0][1] = 42;
        grid[1][2] = 7;
        System.out.println("2D grid:  " + Arrays.deepToString(grid));
    }
}
