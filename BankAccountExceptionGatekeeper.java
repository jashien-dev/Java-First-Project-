import java.util.Scanner;
import java.util.InputMismatchException;

class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}

class BankAccount {
    private String accountNumber;
    private String accountHolder;
    private double balance;

    public BankAccount(String accountNumber, String accountHolder, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Invalid Amount! Deposit must be greater than 0.");
        }

        balance += amount;
        System.out.printf("Deposit Successful! New Balance: $%.2f%n", balance);
    }

    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Invalid Amount! Withdrawal must be greater than 0.");
        }

        if (amount > balance) {
            throw new InsufficientFundsException(
                "Insufficient Funds! Required: $" + String.format("%.2f", amount) +
                ", Available: $" + String.format("%.2f", balance)
            );
        }

        balance -= amount;
        System.out.printf("Withdrawal Successful! New Balance: $%.2f%n", balance);
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        BankAccount account = new BankAccount("ACC-1001", "Alex Cruz", 500.00);

        System.out.println("=== BANK ACCOUNT EXCEPTION GATEKEEPER ===");
        System.out.println("Account Created: " + account.getAccountNumber());
        System.out.println("Holder: " + account.getAccountHolder());
        System.out.printf("Balance: $%.2f%n", account.getBalance());

        try {
            System.out.print("Enter withdrawal amount: ");

            try {
                double withdrawalAmount = scanner.nextDouble();
                account.withdraw(withdrawalAmount);

            } catch (InsufficientFundsException e) {
                System.out.println("[EXCEPTION CAUGHT] " + e.getMessage());

            } catch (IllegalArgumentException e) {
                System.out.println("[EXCEPTION CAUGHT] " + e.getMessage());

            } catch (InputMismatchException e) {
                System.out.println("[EXCEPTION CAUGHT] Invalid input! Please enter a number.");
                scanner.nextLine();
            }

            System.out.print("Enter deposit amount: ");

            try {
                double depositAmount = scanner.nextDouble();
                account.deposit(depositAmount);

            } catch (IllegalArgumentException e) {
                System.out.println("[EXCEPTION CAUGHT] " + e.getMessage());

            } catch (InputMismatchException e) {
                System.out.println("[EXCEPTION CAUGHT] Invalid input! Please enter a number.");
                scanner.nextLine();
            }

        } finally {
            System.out.println("[SESSION COMPLETE]: Transaction log updated.");
            System.out.printf("Current Balance: $%.2f%n", account.getBalance());
            scanner.close();
        }
    }
}
