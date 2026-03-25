class Client {
    String name;
    int riskScore;
    double accountBalance;

    public Client(String name, int riskScore, double accountBalance) {
        this.name = name;
        this.riskScore = riskScore;
        this.accountBalance = accountBalance;
    }

    @Override
    public String toString() {
        return String.format("%s:%d", name, riskScore);
    }
}

public class RiskRankingSystem {

    // Bubble Sort: Ascending Order (with swap visualization)
    public static void bubbleSortAsc(Client[] clients) {
        int n = clients.size; // Note: Use array.length for Client[]
        int swaps = 0;
        for (int i = 0; i < clients.length - 1; i++) {
            for (int j = 0; j < clients.length - i - 1; j++) {
                if (clients[j].riskScore > clients[j + 1].riskScore) {
                    // Swap
                    Client temp = clients[j];
                    clients[j] = clients[j + 1];
                    clients[j + 1] = temp;
                    swaps++;
                }
            }
        }
        System.out.println("Bubble Sort (ASC) Swaps: " + swaps);
    }

    // Insertion Sort: Descending Order (Risk Score) + Account Balance tie-breaker
    public static void insertionSortDesc(Client[] clients) {
        for (int i = 1; i < clients.length; i++) {
            Client key = clients[i];
            int j = i - 1;

            /*
             * Logic for DESC: Swap if current element is LESS than the key.
             * Tie-breaker: If scores are equal, higher account balance comes first.
             */
            while (j >= 0 && (clients[j].riskScore < key.riskScore || 
                  (clients[j].riskScore == key.riskScore && clients[j].accountBalance < key.accountBalance))) {
                clients[j + 1] = clients[j];
                j = j - 1;
            }
            clients[j + 1] = key;
        }
        System.out.println("Insertion Sort (DESC) completed.");
    }

    public static void displayTopRisks(Client[] clients, int topN) {
        System.out.println("Top " + topN + " Highest Risk Clients:");
        int limit = Math.min(topN, clients.length);
        for (int i = 0; i < limit; i++) {
            System.out.println((i + 1) + ". " + clients[i].name + " (" + clients[i].riskScore + ")");
        }
    }

    public static void main(String[] args) {
        Client[] clients = {
            new Client("clientC", 80, 5000.0),
            new Client("clientA", 20, 1500.0),
            new Client("clientB", 50, 2000.0),
            new Client("clientD", 80, 9000.0) // Same score as C, but higher balance
        };

        System.out.println("Initial Input: " + java.util.Arrays.toString(clients));

        // Part 1: Bubble Sort Ascending
        bubbleSortAsc(clients);
        System.out.println("Bubble (asc): " + java.util.Arrays.toString(clients));

        // Part 2: Insertion Sort Descending (Priority Ranking)
        insertionSortDesc(clients);
        System.out.println("Insertion (desc): " + java.util.Arrays.toString(clients));

        // Part 3: Identify top risks
        displayTopRisks(clients, 3);
    }
}
