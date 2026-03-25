import java.util.ArrayList;
import java.util.List;

class Transaction {
    String id;
    double fee;
    String timestamp; // Format HH:mm for simplicity

    public Transaction(String id, double fee, String timestamp) {
        this.id = id;
        this.fee = fee;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return String.format("[%s: $%.2f @ %s]", id, fee, timestamp);
    }
}

public class FeeSorter {

    // Bubble Sort: Best for small batches (<= 100)
    // Optimization: Early termination if no swaps occur
    public static void bubbleSort(List<Transaction> list) {
        int n = list.size();
        int swaps = 0;
        int passes = 0;
        
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            passes++;
            for (int j = 0; j < n - i - 1; j++) {
                if (list.get(j).fee > list.get(j + 1).fee) {
                    // Swap
                    Transaction temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                    swapped = true;
                    swaps++;
                }
            }
            if (!swapped) break; // Early termination
        }
        System.out.println("BubbleSort completed: " + passes + " passes, " + swaps + " swaps.");
    }

    // Insertion Sort: For medium batches (100–1,000)
    // Logic: Sort by Fee, then by Timestamp if Fees are equal
    public static void insertionSort(List<Transaction> list) {
        for (int i = 1; i < list.size(); i++) {
            Transaction key = list.get(i);
            int j = i - 1;

            while (j >= 0 && shouldSwap(list.get(j), key)) {
                list.set(j + 1, list.get(j));
                j = j - 1;
            }
            list.set(j + 1, key);
        }
        System.out.println("InsertionSort (Fee + Timestamp) completed.");
    }

    // Custom comparator logic for Insertion Sort
    private static boolean shouldSwap(Transaction current, Transaction key) {
        if (current.fee > key.fee) return true;
        if (current.fee == key.fee) {
            // Compare timestamps if fees are tied (Stable)
            return current.timestamp.compareTo(key.timestamp) > 0;
        }
        return false;
    }

    public static void checkOutliers(List<Transaction> list) {
        System.out.print("High-fee outliers (> $50): ");
        boolean found = false;
        for (Transaction t : list) {
            if (t.fee > 50.0) {
                System.out.print(t.id + " ");
                found = true;
            }
        }
        if (!found) System.out.print("none");
        System.out.println();
    }

    public static void main(String[] args) {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction("id1", 10.5, "10:00"));
        transactions.add(new Transaction("id2", 25.0, "09:30"));
        transactions.add(new Transaction("id3", 5.0, "10:15"));

        System.out.println("Initial: " + transactions);

        // Scenario: Small Batch
        if (transactions.size() <= 100) {
            bubbleSort(transactions);
        } else {
            insertionSort(transactions);
        }

        System.out.println("Sorted: " + transactions);
        checkOutliers(transactions);
    }
}
