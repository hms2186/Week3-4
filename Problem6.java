import java.util.Arrays;

public class RiskThresholdManager {

    // --- LINEAR SEARCH: O(n) ---
    // Simple check to see if a specific threshold exists
    public static int linearSearch(int[] bands, int target) {
        int comparisons = 0;
        for (int i = 0; i < bands.length; i++) {
            comparisons++;
            if (bands[i] == target) {
                System.out.println("Linear Search: Found at index " + i + " in " + comparisons + " comparisons.");
                return i;
            }
        }
        System.out.println("Linear Search: " + target + " not found after " + comparisons + " comparisons.");
        return -1;
    }

    // --- BINARY SEARCH: Floor (Largest value <= target) ---
    public static int findFloor(int[] bands, int target) {
        int low = 0, high = bands.length - 1;
        int floor = -1;
        int comparisons = 0;

        while (low <= high) {
            comparisons++;
            int mid = low + (high - low) / 2;

            if (bands[mid] == target) return bands[mid];
            
            if (bands[mid] < target) {
                floor = bands[mid]; // Potential candidate
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        System.out.println("Binary Floor Search: " + comparisons + " comparisons.");
        return floor;
    }

    // --- BINARY SEARCH: Ceiling (Smallest value >= target) ---
    public static int findCeiling(int[] bands, int target) {
        int low = 0, high = bands.length - 1;
        int ceiling = -1;
        int comparisons = 0;

        while (low <= high) {
            comparisons++;
            int mid = low + (high - low) / 2;

            if (bands[mid] == target) return bands[mid];

            if (bands[mid] > target) {
                ceiling = bands[mid]; // Potential candidate
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        System.out.println("Binary Ceiling Search: " + comparisons + " comparisons.");
        return ceiling;
    }

    public static void main(String[] args) {
        int[] sortedRisks = {10, 25, 50, 100};
        int threshold = 30;

        System.out.println("Sorted Risks: " + Arrays.toString(sortedRisks));
        System.out.println("Target Threshold: " + threshold);

        // 1. Linear Search for exact match
        linearSearch(sortedRisks, threshold);

        // 2. Binary Floor & Ceiling
        int floor = findFloor(sortedRisks, threshold);
        int ceiling = findCeiling(sortedRisks, threshold);

        System.out.println("Result -> Floor: " + floor + ", Ceiling: " + ceiling);
    }
}
