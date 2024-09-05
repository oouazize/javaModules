package ex04;

import java.util.UUID;
import ex03.UsersList;
import ex03.Transaction;
import ex03.TransactionsLinkedList;
import ex03.User;
import ex03.UsersArrayList;

public class TransactionsService {
    private UsersList usersList;

    public TransactionsService() {
        usersList = new UsersArrayList();
    }

    public TransactionsService(UsersList usersList) {
        this.usersList = usersList;
    }

    public void addUser(User user) {
        usersList.addUser(user);
    }

    public int getUserBalance(int id) {
        return usersList.getUserById(id).getBalance();
    }

    public String getUserName(int id) {
        return usersList.getUserById(id).getName();
    }

    public void performTransfer(int senderId, int receiverId, int amount) {
        User sender = usersList.getUserById(senderId);
        User receiver = usersList.getUserById(receiverId);

        if (sender.getBalance() < amount) {
            throw new IllegalTransactionException("Insufficient balance for the transaction.");
        }

        Transaction debitTransaction = new Transaction(sender, receiver, Transaction.Category.DEBITS, amount);
        Transaction creditTransaction = new Transaction(debitTransaction.getId(), receiver, sender, Transaction.Category.CREDITS, amount);

        sender.addTransaction(debitTransaction);
        receiver.addTransaction(creditTransaction);
    }

    public Transaction[] getUserTransactions(int userId) {
        return usersList.getUserById(userId).getTransactions();
    }

    public Transaction removeTransaction(int userId, UUID transactionId) {
        return usersList.getUserById(userId).removeTransaction(transactionId);
    }

    public Transaction[] checkTransactionsValidity() {
        TransactionsLinkedList unpairedTransactions = new TransactionsLinkedList();
        // For each user
        for (int i = 0; i < usersList.getNumberOfUsers(); i++) {
            User user = usersList.getUserByIndex(i);
            Transaction[] transactions = user.getTransactions();

            // For each transaction of the user
            for (Transaction transaction : transactions) {
                User recipient = transaction.getRecipient();
                Transaction[] recipientTransactions = recipient.getTransactions();
                boolean paired = false;
                for (Transaction recipientTransaction : recipientTransactions) {
                    if (transaction.getSender().equals(recipientTransaction.getRecipient())
                            && transaction.getRecipient().equals(recipientTransaction.getSender())
                            && transaction.getAmount() == recipientTransaction.getAmount()) {
                        paired = true;
                        break;
                    }
                }
                if (!paired) {
                    unpairedTransactions.add(transaction);
                }
            }
        }
        return unpairedTransactions.toArray();
    }

}
