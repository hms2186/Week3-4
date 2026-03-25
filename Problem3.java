import java.util.Arrays;

class Trade {
    String id;
    int volume;

    public Trade(String id, int volume) {
        this.id = id;
        this.volume = volume;
    }

    @Override
    public String toString() {
        return id + ":" + volume;
    }
}

public class TradeVolumeAnalyzer {

    // --- MERGE SORT (Stable, O(n log n)) ---
    public static void mergeSort(Trade[] trades, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(trades, left, mid);
            mergeSort(trades, mid + 1, right);
            merge(trades, left, mid, right);
        }
    }

    private static void merge(Trade[] trades, int left, int mid, int right) {
        Trade[] temp = new Trade[right - left + 1];
        int i = left, j = mid + 1, k = 0;

        while (i <= mid && j <= right) {
            // Volume ASC (Stable comparison)
            if (trades[i].volume <= trades[j].volume) {
                temp[k++] = trades[i++];
            } else {
                temp[k++] = trades[j++];
            }
        }
        while (i <= mid) temp[k++] = trades[i++];
        while (j <= right) temp[k++] = trades[j++];

        System.arraycopy(temp, 0, trades, left, temp.length);
    }

    // --- QUICK SORT (In-place, Avg O(n log n)) ---
    public static void quickSortDesc(Trade[] trades, int low, int high) {
        if (low < high) {
            int pivotIndex = partitionDesc(trades, low, high);
            quickSortDesc(trades, low, pivotIndex - 1);
            quickSortDesc(trades, pivotIndex + 1, high);
        }
    }

    private static int partitionDesc(Trade[] trades, int low, int high) {
        // Using Lomuto Partition with the last element as pivot
        int pivot = trades[high].volume;
        int i = low - 1;
        for (int j = low; j < high; j++) {
            // Volume DESC
            if (trades[j].volume >= pivot) {
                i++;
                swap(trades, i, j);
            }
        }
        swap(trades, i + 1, high);
        return i + 1;
    }

    private static void swap(Trade[] trades, int i, int j) {
        Trade temp = trades[i];
        trades[i] = trades[j];
        trades[j] = temp;
    }

    // --- UTILITIES ---
    public static long computeTotalVolume(Trade[] trades) {
        long total = 0;
        for (Trade t : trades) total += t.volume;
        return total;
    }

    public static void main(String[] args) {
        Trade[] morningSession = {
            new Trade("trade3", 500),
            new Trade("trade1", 100),
            new Trade("trade2", 300)
        };

        // 1. Merge Sort (Ascending)
        mergeSort(morningSession, 0, morningSession.length - 1);
        System.out.println("MergeSort (ASC): " + Arrays.toString(morningSession));

        // 2. Quick Sort (Descending)
        quickSortDesc(morningSession, 0, morningSession.length - 1);
        System.out.println("QuickSort (DESC): " + Arrays.toString(morningSession));

        // 3. Total Volume Calculation
        System.out.println("Merged morning+afternoon total: " + computeTotalVolume(morningSession));
    }
}
