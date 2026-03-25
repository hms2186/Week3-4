import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class TransactionLookupSystem {

    // --- LINEAR SEARCH: O(n) ---
    // Finds the first occurrence of an ID
    public static int linearSearchFirst(String[] logs, String target) {
        int comparisons = 0;
        for (int i = 0; i < logs.length; i++) {
            comparisons++;
            if (logs[i].equals(target)) {
                System.out.println("Linear Search Comparisons: " + comparisons);
                return i;
            }
        }
        return -1;
    }

    // --- BINARY SEARCH: O(log n) ---
    // Requires sorted input. Returns any index of the target.
    public static int binarySearchAny(String[] logs, String target) {
        int low = 0;
        int high = logs.length - 1;
        int comparisons = 0;

        while (low <= high) {
            comparisons++;
            int mid = low + (high - low) / 2;
            int res = target.compareTo(logs[mid]);

            if (res == 0) {
                System.out.println("Binary Search (Exact) Comparisons: " + comparisons);
                return mid;
            }
            if (res > 0) low = mid + 1;
            else high = mid - 1;
        }
        return -1;
    }

    // Helper to count occurrences in a sorted array
    public static int countOccurrences(String[] logs, String target) {
        int first = findBound(logs, target, true);
        if (first == -1) return 0;
        int last = findBound(logs, target, false);
        return (last - first) + 1;
    }

    private static int findBound(String[] logs, String target, boolean isFirst) {
        int low = 0, high = logs.length - 1;
        int result = -1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int res = target.compareTo(logs[mid]);
            if (res == 0) {
                result = mid;
                if (isFirst) high = mid - 1; // Keep searching left
                else low = mid + 1;          // Keep searching right
            } else if (res > 0) low = mid + 1;
            else high = mid - 1;
        }
        return result;
    }

    public static void main(String[] args) {
        // Sample Input (Sorted for Binary Search)
        String[] logs = {"accA", "accB", "accB", "accC", "accD", "accE"};

        String target = "accB";
        System.out.println("Searching for: " + target);

        // 1. Linear Search
        int linIdx = linearSearchFirst(logs, target);
        System.out.println("Linear first " + target + ": index " + linIdx);

        // 2. Binary Search
        int binIdx = binarySearchAny(logs, target);
        int count = countOccurrences(logs, target);
        System.out.println("Binary " + target + ": index " + binIdx + ", total count=" + count);
    }
}
