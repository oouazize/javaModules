package ex03;

import java.util.UUID;

interface TransactionsList {
    
    void add(Transaction transaction);
    Transaction removeById(UUID transactionId);
    Transaction[] toArray();
}
