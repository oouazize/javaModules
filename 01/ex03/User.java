package ex03;

import java.util.UUID;

import ex01.UserIdsGenerator;

public class User {
    private final int id;
    private String name;
    private int balance;
    private TransactionsList transactions;

    public User(String name, int balance) {
        this.id = UserIdsGenerator.getInstance().genereateId();
        this.name = name;
        setBalance(balance);
        this.transactions = new TransactionsLinkedList();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getBalance() {
        return balance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBalance(int balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        this.balance = balance;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public Transaction removeTransaction(UUID transactionId) {
        return transactions.removeById(transactionId);
    }

    public Transaction[] getTransactions() {
        return transactions.toArray();
    }

    @Override
    public String toString() {
        return "User{id=" + id + ", name='" + name + "', balance=" + balance + '}';
    }
}