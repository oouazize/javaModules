package ex03;

public class Program {
    public static void main(String[] args) {
        User john = new User("John", 1000);
        User mike = new User("Mike", 500);

        Transaction transaction1 = new Transaction(john, mike, Transaction.Category.DEBITS, 100);
        Transaction transaction2 = new Transaction(john, mike, Transaction.Category.CREDITS, 200);
        Transaction transaction3 = new Transaction(john, mike, Transaction.Category.DEBITS, 300);

        john.addTransaction(transaction1);
        john.addTransaction(transaction2);
        john.addTransaction(transaction3);

        john.removeTransaction(transaction2.getId());

        Transaction[] arr = john.getTransactions();
        for (Transaction t : arr) {
            System.out.println(t);
        }

        try {
            john.removeTransaction(transaction2.getId()); // This should throw TransactionNotFoundException
        } catch (TransactionNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
