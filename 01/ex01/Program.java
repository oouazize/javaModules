package ex01;

public class Program {
    public static void main(String[] args) {

        final String SingletonImplemented = "Three objects point to the same memory location on the heap";
        final String SingletonNotImplemented = "Three objects DO NOT point to the same memory location on the heap";
        User john = new User("John", 1000);
        User mike = new User("Mike", 500);

        System.out.println(john);
        System.out.println(mike);

        UserIdsGenerator instance1 = UserIdsGenerator.getInstance();
        UserIdsGenerator instance2 = UserIdsGenerator.getInstance();
        UserIdsGenerator instance3 = UserIdsGenerator.getInstance();
        System.out.println(instance1.hashCode());
        System.out.println(instance2.hashCode());
        System.out.println(instance3.hashCode());

        boolean isSingleton = instance1 == instance2 && instance2 == instance3;
        System.out.println(isSingleton ? SingletonImplemented : SingletonNotImplemented);
    }
}
