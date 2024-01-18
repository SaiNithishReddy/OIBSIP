// TASK 3
// ATM INTERFACE
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ATMSystem {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            // Simulated user data (in a real scenario, you'd use a database)
            User user = new User("12345", "6789");
            Account account = new Account(user, 1000.0);

            System.out.print("Enter User ID: ");
            String userId = scanner.nextLine();

            System.out.print("Enter PIN: ");
            String pin = scanner.nextLine();

            if (authenticateUser(user, userId, pin)) {
                System.out.println("Authentication Successful. Welcome!");

                TransactionManager transactionManager = new TransactionManager(account);
                transactionManager.start();
            } else {
                System.out.println("Authentication Failed. Exiting...");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter valid data.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred. Exiting...");
        }
    }

    private static boolean authenticateUser(User user, String enteredUserId, String enteredPin) {
        return user.authenticate(enteredUserId, enteredPin);
    }
}

class User {
    private final String userId;
    private final String pin;

    public User(String userId, String pin) {
        this.userId = userId;
        this.pin = pin;
    }

    public String getUserId() {
        return userId;
    }

    public boolean authenticate(String enteredUserId, String enteredPin) {
        return userId.equals(enteredUserId) && pin.equals(enteredPin);
    }
}

class Account {
    private final User user;
    private double balance;

    public Account(User user, double initialBalance) {
        this.user = user;
        this.balance = initialBalance;
    }

    public User getUser() {
        return user;
    }

    public double getBalance() {
        return balance;
    }

    public void updateBalance(double amount) {
        this.balance += amount;
    }
}

class TransactionManager {
    private final Account account;
    private final List<String> transactionHistory;

    public TransactionManager(Account account) {
        this.account = account;
        this.transactionHistory = new ArrayList<>();
    }

    public void start() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("\nATM Menu:");
                System.out.println("1. View Transactions History");
                System.out.println("2. Withdraw");
                System.out.println("3. Deposit");
                System.out.println("4. Transfer");
                System.out.println("5. Quit");

                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        viewTransactionHistory();
                        break;
                    case 2:
                        withdraw(scanner);
                        break;
                    case 3:
                        deposit(scanner);
                        break;
                    case 4:
                        transfer(scanner);
                        break;
                    case 5:
                        System.out.println("Exiting ATM. Thank you!");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid choice.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred. Exiting...");
        }
    }

    private void viewTransactionHistory() {
        System.out.println("\nTransaction History:");
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            for (String transaction : transactionHistory) {
                System.out.println(transaction);
            }
        }
    }

    private void withdraw(Scanner scanner) {
        try {
            System.out.print("Enter withdrawal amount: ");
            double amount = scanner.nextDouble();

            if (amount > 0 && amount <= account.getBalance()) {
                account.updateBalance(-amount);
                String transaction = "Withdrawal: $" + amount + ", New Balance: $" + account.getBalance();
                transactionHistory.add(transaction);
                System.out.println("Withdrawal successful. Updated balance: " + account.getBalance());
            } else if(amount < 0) {
                System.out.println("Invalid withdrawal amount.");
            } else {
                System.out.println("Insufficient funds.");
            }   
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid amount.");
        }
    }

    private void deposit(Scanner scanner) {
        try {
            System.out.print("Enter deposit amount: ");
            double amount = scanner.nextDouble();

            if (amount > 0) {
                account.updateBalance(amount);
                String transaction = "Deposit: $" + amount + ", New Balance: $" + account.getBalance();
                transactionHistory.add(transaction);
                System.out.println("Deposit successful. Updated balance: " + account.getBalance());
            } else {
                System.out.println("Invalid deposit amount.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid amount.");
        }
    }

    private void transfer(Scanner scanner) {
        try {
            // No need to store recipientUserId if not used
            System.out.print("Enter recipient's User ID: ");
            scanner.next(); // Consume the input (recipientUserId)
            
            System.out.print("Enter transfer amount: ");
            double amount = scanner.nextDouble();

            // Check if recipient exists and the amount is valid
            if (isValidTransfer(amount)) {
                // Deduct amount from the current user's account
                account.updateBalance(-amount);

                // Update recipient's account balance
                // Note: In a real scenario, you'd need to fetch the recipient's account from a database.
                // For simplicity, let's assume the recipient has a predefined account in this example.
                Account recipientAccount = new Account(new User("", ""), 0.0);
                recipientAccount.updateBalance(amount);

                // Record transactions in the history
                String senderTransaction = "Transfer to " + account.getUser().getUserId() + ": $" + amount + ", New Balance: $" + account.getBalance();
                String recipientTransaction = "Transfer from " + account.getUser().getUserId() + ": $" + amount + ", New Balance: $" + recipientAccount.getBalance();

                transactionHistory.add(senderTransaction);
                transactionHistory.add(recipientTransaction);

                System.out.println("Transfer successful. Updated balance: " + account.getBalance());
            } else {
                System.out.println("Invalid transfer. Please check recipient's User ID and amount.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter valid data.");
        }
    }

    private boolean isValidTransfer(double amount) {
        // In a real scenario, you might want to check if the recipient's account exists in a database
        // and perform additional validations. For simplicity, we're assuming a valid transfer in this example.
        return amount > 0;
    }
}
