package ex00;

import java.util.UUID;

public class Transaction {
    public enum Category {
        DEBITS,
        CREDITS
    }

    private final UUID id;
    private final User recipient;
    private final User sender;
    private final Category category;
    private final int amount;

    public Transaction(User sender, User recipient, Category category, int amount) {
        this.id = UUID.randomUUID();
        this.sender = sender;
        this.recipient = recipient;
        this.category = category;
        this.amount = amount;

        if (category == Category.DEBITS && sender.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance for transaction");
        }

        if (category == Category.DEBITS) {
            sender.setBalance(sender.getBalance() - amount);
        } else {
            sender.setBalance(sender.getBalance() + amount);
        }
    }

    public UUID getId() {
        return id;
    }

    public User getRecipient() {
        return recipient;
    }

    public User getSender() {
        return sender;
    }

    public Category getCategory() {
        return category;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Transaction{id='" + id + "', sender=" + sender.getName() + ", recipient=" + recipient.getName() +
                ", category=" + category + ", amount=" + amount + '}';
    }
}
