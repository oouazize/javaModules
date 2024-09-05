package ex02;

import ex01.User;

public class Program {
    public static void main(String[] args) {
        UsersList usersList = new UsersArrayList();
        
        User john = new User("John", 1000);
        User mike = new User("Mike", 500);

        usersList.addUser(john);
        usersList.addUser(mike);

        System.out.println("User by ID 1: " + usersList.getUserById(1).getName());
        System.out.println("User by Index 0: " + usersList.getUserByIndex(0).getName());

        try {
            usersList.getUserById(3); // This should throw UserNotFoundException
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
