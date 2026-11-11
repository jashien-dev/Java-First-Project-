
import java.util.Scanner;

public class CustomerTransactionManager {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of customer transactions: ");
        int count = scanner.nextInt();
        scanner.nextLine();

        String[] transactionIds = new String[count];

        double[] transactionAmounts = new double[count];

        double[][] itemDetails = new double[count][3];

        for (int i = 0; i < count; i++) {
            System.out.println("\n--- Customer " + (i + 1) + " ---");
            System.out.print("Enter Transaction ID (e.g., TXN-101): ");
            transactionIds[i] = scanner.nextLine();

            System.out.print("Enter Total Transaction Amount: $");
            transactionAmounts[i] = scanner.nextDouble();
            scanner.nextLine();

            System.out.println("Enter breakdown for 3 item categories (e.g., Electronics, Clothing, Groceries):");
            for (int j = 0; j < 3; j++) {
                System.out.print("  Category " + (j + 1) + ": $");
                itemDetails[i][j] = scanner.nextDouble();
            }
            scanner.nextLine();
        }

        System.out.println("\n==================================================");
        System.out.println("       SELECT A SORTING ALGORITHM                 ");
        System.out.println("==================================================");
        System.out.println("Option A: Bubble Sort");
        System.out.println("Option B: Selection Sort");
        System.out.println("Option C: Insertion Sort");
        System.out.println("Option D: Merge Sort");
        System.out.print("Enter your choice (A, B, C, or D): ");
        char sortChoice = scanner.next().toUpperCase().charAt(0);

        System.out.print("Choose order - Enter 'ASC' for Ascending or 'DESC' for Descending: ");
        String orderChoice = scanner.next().toUpperCase();
        boolean ascending = orderChoice.equals("ASC");

        switch (sortChoice) {
            case 'A':
                bubbleSort(transactionAmounts, transactionIds, itemDetails, ascending);
                System.out.println("\n[Sorted using Bubble Sort]");
                break;
            case 'B':
                selectionSort(transactionAmounts, transactionIds, itemDetails, ascending);
                System.out.println("\n[Sorted using Selection Sort]");
                break;
            case 'C':
                insertionSort(transactionAmounts, transactionIds, itemDetails, ascending);
                System.out.println("\n[Sorted using Insertion Sort]");
                break;
            case 'D':
                mergeSortWrapper(transactionAmounts, transactionIds, itemDetails, ascending);
                System.out.println("\n[Sorted using Merge Sort]");
                break;
            default:
                System.out.println("\nInvalid choice. Defaulting to Bubble Sort (Ascending).");
                bubbleSort(transactionAmounts, transactionIds, itemDetails, true);
                break;
        }

        System.out.println("\n======================================================================");
        System.out.println("                SORTED CUSTOMER TRANSACTIONS REPORT                   ");
        System.out.println("======================================================================");
        System.out.printf("%-15s | %-18s | %-28s\n", "Transaction ID", "Total Amount ($)", "Item Breakdown (1, 2, 3)");
        System.out.println("----------------------------------------------------------------------");

        for (int i = 0; i < count; i++) {
            System.out.printf("%-15s | $%-17.2f | $%.2f, $%.2f, $%.2f\n",
                transactionIds[i],
                transactionAmounts[i],
                itemDetails[i][0],
                itemDetails[i][1],
                itemDetails[i][2]);
        }
        System.out.println("======================================================================");

        scanner.close();
    }

    public static void bubbleSort(double[] amounts, String[] ids, double[][] details, boolean ascending) {
        int n = amounts.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                boolean condition = ascending ? (amounts[j] > amounts[j + 1]) : (amounts[j] < amounts[j + 1]);
                if (condition) {
                    swap(amounts, ids, details, j, j + 1);
                }
            }
        }
    }

    public static void selectionSort(double[] amounts, String[] ids, double[][] details, boolean ascending) {
        int n = amounts.length;
        for (int i = 0; i < n - 1; i++) {
            int targetIndex = i;
            for (int j = i + 1; j < n; j++) {
                boolean condition = ascending ? (amounts[j] < amounts[targetIndex]) : (amounts[j] > amounts[targetIndex]);
                if (condition) {
                    targetIndex = j;
                }
            }
            if (targetIndex != i) {
                swap(amounts, ids, details, i, targetIndex);
            }
        }
    }

    public static void insertionSort(double[] amounts, String[] ids, double[][] details, boolean ascending) {
        int n = amounts.length;
        for (int i = 1; i < n; i++) {
            double keyAmount = amounts[i];
            String keyId = ids[i];
            double[] keyRow = details[i];
            int j = i - 1;

            while (j >= 0 && (ascending ? amounts[j] > keyAmount : amounts[j] < keyAmount)) {
                amounts[j + 1] = amounts[j];
                ids[j + 1] = ids[j];
                details[j + 1] = details[j];
                j = j - 1;
            }
            amounts[j + 1] = keyAmount;
            ids[j + 1] = keyId;
            details[j + 1] = keyRow;
        }
    }

    public static void mergeSortWrapper(double[] amounts, String[] ids, double[][] details, boolean ascending) {
        mergeSort(amounts, ids, details, 0, amounts.length - 1, ascending);
    }

    private static void mergeSort(double[] amounts, String[] ids, double[][] details, int left, int right, boolean ascending) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(amounts, ids, details, left, mid, ascending);
            mergeSort(amounts, ids, details, mid + 1, right, ascending);
            merge(amounts, ids, details, left, mid, right, ascending);
        }
    }

    private static void merge(double[] amounts, String[] ids, double[][] details, int left, int mid, int right, boolean ascending) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        double[] LAmounts = new double[n1];
        double[] RAmounts = new double[n2];
        String[] LIds = new String[n1];
        String[] RIds = new String[n2];
        double[][] LDetails = new double[n1][3];
        double[][] RDetails = new double[n2][3];

        for (int i = 0; i < n1; i++) {
            LAmounts[i] = amounts[left + i];
            LIds[i] = ids[left + i];
            LDetails[i] = details[left + i];
        }
        for (int j = 0; j < n2; j++) {
            RAmounts[j] = amounts[mid + 1 + j];
            RIds[j] = ids[mid + 1 + j];
            RDetails[j] = details[mid + 1 + j];
        }

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            boolean condition = ascending ? (LAmounts[i] <= RAmounts[j]) : (LAmounts[i] >= RAmounts[j]);
            if (condition) {
                amounts[k] = LAmounts[i];
                ids[k] = LIds[i];
                details[k] = LDetails[i];
                i++;
            } else {
                amounts[k] = RAmounts[j];
                ids[k] = RIds[j];
                details[k] = RDetails[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            amounts[k] = LAmounts[i];
            ids[k] = LIds[i];
            details[k] = LDetails[i];
            i++;
            k++;
        }

        while (j < n2) {
            amounts[k] = RAmounts[j];
            ids[k] = RIds[j];
            details[k] = RDetails[j];
            j++;
            k++;
        }
    }

    private static void swap(double[] amounts, String[] ids, double[][] details, int i, int j) {
        double tempAmount = amounts[i];
        amounts[i] = amounts[j];
        amounts[j] = tempAmount;

        String tempId = ids[i];
        ids[i] = ids[j];
        ids[j] = tempId;

        double[] tempRow = details[i];
        details[i] = details[j];
        details[j] = tempRow;
    }
}
