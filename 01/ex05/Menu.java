package ex05;

import java.util.Scanner;
import java.util.UUID;

import ex03.Transaction;
import ex03.User;
import ex04.TransactionsService;

public class Menu {
    private TransactionsService transactionsService;

    public Menu(TransactionsService service) {
        this.transactionsService = service;
    }

    public void displayMenu(boolean isDevMode) {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        
        while (choice != 7) {
            System.out.println("1. Add a user");
            System.out.println("2. View user balances");
            System.out.println("3. Perform a transfer");
            System.out.println("4. View all transactions for a specific user");
            if (isDevMode) {
                System.out.println("5. DEV - remove a transfer by ID");
                System.out.println("6. DEV - check transfer validity");
            }
            System.out.println("7. Finish execution");
            
            System.out.print("-> ");
            choice = scanner.nextInt();

            try {
                switch (choice) {
                    case 1:
                        addUser(scanner);
                        break;
                    case 2:
                        viewUserBalance(scanner);
                        break;
                    case 3:
                        performTransfer(scanner);
                        break;
                    case 4:
                        viewUserTransactions(scanner);
                        break;
                    case 5:
                        if (isDevMode)
                            removeTransferById(scanner);
                        break;
                    case 6:
                        if (isDevMode)
                            checkTransferValidity();
                        break;
                    case 7:
                        System.out.println("Finishing execution");
                        return;
                    default:
                        System.out.println("Invalid option. Try again.");
                }
                System.out.println("----------------------------------");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        
        scanner.close();
    }

    private void addUser(Scanner scanner) {
        System.out.println("Enter a user name and a balance");
        String name = scanner.next();
        int balance = scanner.nextInt();
        User user = new User(name, balance);
        transactionsService.addUser(user);
        System.out.println("User with id = " + user.getId() + " added");
    }

    private void viewUserBalance(Scanner scanner) {
        boolean invalidData = true;
        while (invalidData) {
            invalidData = false;
            System.out.println("Enter a user ID");
            int userId = scanner.nextInt();
            try {
                System.out.println(transactionsService.getUserName(userId) + " = " + transactionsService.getUserBalance(userId));
            }
            catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                invalidData = true;
            }
        }
    }

    private void performTransfer(Scanner scanner) {
        boolean invalidData = true;
        while (invalidData) {
            invalidData = false;
            System.out.println("Enter a sender ID, a recipient ID, and a transfer amount");
            int senderId = scanner.nextInt();
            int recipientId = scanner.nextInt();
            int amount = scanner.nextInt();
            try {
                transactionsService.performTransfer(senderId, recipientId, amount);
                System.out.println("The transfer is completed");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                invalidData = true;
            }
        }
    }
    
    private void displayTransaction(Transaction t, int userId) {
        User recipientUser = t.getRecipient().getId() == userId ? t.getSender() : t.getRecipient();
        System.out.print("To " + recipientUser.getName());
        System.out.print("(id = " + recipientUser.getId());
        System.out.print(") =" + t.getAmount());
        System.out.println(" with id = " + t.getId());
    }

    private void viewUserTransactions(Scanner scanner) {
        boolean invalidData = true;
        while (invalidData) {
            invalidData = false;
            System.out.println("Enter a user ID");
            int userId = scanner.nextInt();
            try {
                Transaction[] arr = transactionsService.getUserTransactions(userId);
                for (Transaction t : arr) {
                    displayTransaction(t, userId);
                }
            }
            catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                invalidData = true;
            }
        }
    }

    private void removeTransferById(Scanner scanner) {
        boolean invalidData = true;
        while (invalidData) {
            invalidData = false;
            System.out.println("Enter a user ID and a transfer ID");
            int userId = scanner.nextInt();
            String transferIdString = scanner.next();
            UUID transferId = UUID.fromString(transferIdString);
            try {
                Transaction removedTransaction = transactionsService.removeTransaction(userId, transferId);
                User displayedUser = removedTransaction.getRecipient().getId() == userId ? removedTransaction.getSender() : removedTransaction.getRecipient();
                String Transfertype = removedTransaction.getRecipient().getId() == userId ? "From " : "To ";
                System.out.println("Transfer " + Transfertype + displayedUser.getName() + "(id = " + displayedUser.getId() + ") " + removedTransaction.getAmount() + " removed");
            }
            catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                invalidData = true;
            }
        }
    }

    private void checkTransferValidity() {
        Transaction[] unpairedTransactions = transactionsService.checkTransactionsValidity();
        System.out.println("Check results:");
        for (Transaction t : unpairedTransactions) {
            User sendUser = t.getSender();
            User recipientUser = t.getRecipient();
            Transaction[] senderTransactions = sendUser.getTransactions();
            // Check if the transaction is unacknowledged by the recipient
            boolean isUnacknowledged = true;
            for (Transaction senderTransaction : senderTransactions) {
                if (senderTransaction.getId() == t.getId()) {
                    isUnacknowledged = false;
                    break;
                }
            }
            if (isUnacknowledged) {
                sendUser = t.getRecipient();
                recipientUser = t.getSender();
            }
            System.out.println(sendUser.getName() + "(id = " + sendUser.getId() + ") has an unacknowledged transfer id = " + t.getId() + " from " + recipientUser.getName() + "(id = " + recipientUser.getId() + ") for " + t.getAmount());
        }
    }
}