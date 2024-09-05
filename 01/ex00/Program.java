package ex00;

public class Program {
    public static void main(String[] args) {
        User john = new User(1, "John", 1000);
        User mike = new User(2, "Mike", 500);

        System.out.println("Before transaction:");
        System.out.println(john);
        System.out.println(mike);

        Transaction transaction1 = new Transaction(john, mike, Transaction.Category.DEBITS, 200);

        System.out.println("\nAfter transaction:");
        System.out.println(john);
        System.out.println(mike);

        System.out.println("\nTransaction details:");
        System.out.println(transaction1);
    }
}
