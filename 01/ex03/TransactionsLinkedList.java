package ex03;

import java.util.Collection;
import java.util.UUID;

public class TransactionsLinkedList implements TransactionsList {

    static class Node {
        Transaction data;
        Node next;

        Node(Transaction d) {
            data = d;
        }

        Node(Transaction newNode, Node newLast) {
            data = newNode;
            next = newLast;
        }
    }

    transient int size;
    transient Node first;
    transient Node last;

    public TransactionsLinkedList() {
        this.size = 0;
    }

    public TransactionsLinkedList(Collection<? extends Transaction> c) {
        this();
        this.addAll(c);
    }
    
    private void addAll(Collection<? extends Transaction> c) {
        Transaction[] a = (Transaction[]) c.toArray();
        int numNew = a.length;
        for (int i = 0; i < numNew; i++) {
            add(a[i]);
        }
    }

    public void add(Transaction transaction) {
        Node l = this.last;
        Node newNode = new Node(transaction, (Node) null);
        this.last = newNode;
        if (l == null) {
           this.first = newNode;
        } else {
           l.next = newNode;
        }
        ++this.size;
    }

    public Transaction removeById(UUID transactionId) {
        Transaction removedTransaction = this.first.data;
        if (this.first.data.getId().equals(transactionId)) {
            this.first = first.next;
            --this.size;
            return removedTransaction;
        }
        Node prev = this.first;
        for (Node b = this.first.next; b != this.last.next; b = b.next) {
            if (b.data.getId().equals(transactionId)) {
                removedTransaction = b.data;
                prev.next = b.next;
                --this.size;
                return removedTransaction;
            }
            prev = prev.next;
        }
        throw new TransactionNotFoundException("Transaction not found");
    }

    public Transaction[] toArray() {
        Transaction[] array = new Transaction[this.size];
        int i = 0;
    
        for (Node b = this.first; last != null && b != this.last.next; b = b.next) {
            array[i++] = b.data;
        }
        return array;
    }
}
