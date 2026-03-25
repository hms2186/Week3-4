import java.util.Arrays;

class Asset {
    String ticker;
    double returnRate;
    double volatility;

    public Asset(String ticker, double returnRate, double volatility) {
        this.ticker = ticker;
        this.returnRate = returnRate;
        this.volatility = volatility;
    }

    @Override
    public String toString() {
        return String.format("[%s: %.1f%%, Vol: %.1f%%]", ticker, returnRate, volatility);
    }
}

public class PortfolioSorter {

    // --- MERGE SORT: Stable, Ascending by Return Rate ---
    public static void mergeSort(Asset[] assets, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(assets, left, mid);
            mergeSort(assets, mid + 1, right);
            merge(assets, left, mid, right);
        }
    }

    private static void merge(Asset[] assets, int left, int mid, int right) {
        Asset[] temp = new Asset[right - left + 1];
        int i = left, j = mid + 1, k = 0;

        while (i <= mid && j <= right) {
            // Stability: <= ensures original order is preserved for ties
            if (assets[i].returnRate <= assets[j].returnRate) {
                temp[k++] = assets[i++];
            } else {
                temp[k++] = assets[j++];
            }
        }
        while (i <= mid) temp[k++] = assets[i++];
        while (j <= right) temp[k++] = assets[j++];
        System.arraycopy(temp, 0, assets, left, temp.length);
    }

    // --- QUICK SORT: DESC Return + ASC Volatility ---
    public static void quickSort(Asset[] assets, int low, int high) {
        // Hybrid optimization: Use Insertion Sort for small partitions (e.g., < 10)
        if (low + 10 > high) {
            insertionSort(assets, low, high);
            return;
        }

        if (low < high) {
            int pivotIndex = partition(assets, low, high);
            quickSort(assets, low, pivotIndex - 1);
            quickSort(assets, pivotIndex + 1, high);
        }
    }

    private static int partition(Asset[] assets, int low, int high) {
        // Pivot Selection: Median-of-Three (Low, Mid, High)
        int mid = low + (high - low) / 2;
        int pivotIdx = selectMedian(assets, low, mid, high);
        swap(assets, pivotIdx, high); // Move pivot to end
        
        Asset pivot = assets[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            // Logic: DESC Return, then ASC Volatility if returns are tied
            if (assets[j].returnRate > pivot.returnRate || 
               (assets[j].returnRate == pivot.returnRate && assets[j].volatility < pivot.volatility)) {
                i++;
                swap(assets, i, j);
            }
        }
        swap(assets, i + 1, high);
        return i + 1;
    }

    private static int selectMedian(Asset[] a, int l, int m, int h) {
        if ((a[l].returnRate < a[m].returnRate) ^ (a[l].returnRate < a[h].returnRate)) return l;
        else if ((a[m].returnRate < a[l].returnRate) ^ (a[m].returnRate < a[h].returnRate)) return m;
        else return h;
    }

    private static void insertionSort(Asset[] a, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            Asset key = a[i];
            int j = i - 1;
            while (j >= low && a[j].returnRate < key.returnRate) {
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = key;
        }
    }

    private static void swap(Asset[] a, int i, int j) {
        Asset temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static void main(String[] args) {
        Asset[] portfolio = {
            new Asset("AAPL", 12.0, 15.0),
            new Asset("TSLA", 8.0, 25.0),
            new Asset("GOOG", 15.0, 12.0),
            new Asset("MSFT", 12.0, 10.0) // Tie with AAPL for Merge test
        };

        System.out.println("Input: " + Arrays.toString(portfolio));

        // 1. Merge Sort (Ascending)
        mergeSort(portfolio, 0, portfolio.length - 1);
        System.out.println("Merge (asc): " + Arrays.toString(portfolio));

        // 2. Quick Sort (Desc Return + Asc Vol)
        quickSort(portfolio, 0, portfolio.length - 1);
        System.out.println("Quick (desc): " + Arrays.toString(portfolio));
    }
}
