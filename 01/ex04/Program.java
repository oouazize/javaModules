package ex04;

import ex03.Transaction;
import ex03.User;

public class Program {
    public static void main(String[] args) {
        User john = new User("John", 500);
        User mike = new User("Mike", 100);

        // Adding users
        TransactionsService transactionsService = new TransactionsService();
        transactionsService.addUser(john);
        transactionsService.addUser(mike);

        // Perform a transaction
        transactionsService.performTransfer(john.getId(), mike.getId(), 500);


        // Display transactions
        Transaction[] johnTransactions = transactionsService.getUserTransactions(john.getId());
        System.out.println("----------------john transactions:--------------------");
        System.out.println("john balance: " + transactionsService.getUserBalance(john.getId()));
        for (Transaction t : johnTransactions) {
            System.out.println(t);
        }
        Transaction[] mikeTransactions = transactionsService.getUserTransactions(mike.getId());
        System.out.println("\n----------------mike transactions:--------------------");
        System.out.println("mike balance: " + transactionsService.getUserBalance(mike.getId()));
        for (Transaction t : mikeTransactions) {
            System.out.println(t);
        }


        // Remove a transaction
        transactionsService.removeTransaction(john.getId(), johnTransactions[0].getId());


        // Display unpaired transactions
        Transaction[] unpairedTransactions = transactionsService.checkTransactionsValidity();
        System.out.println("\n-------------unpair transactions:--------------");
        for (Transaction t : unpairedTransactions) {
            System.out.println(t);
        }

    }
}
