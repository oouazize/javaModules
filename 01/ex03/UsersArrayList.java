package ex03;

import ex02.UserNotFoundException;

public class UsersArrayList implements UsersList {
    transient User[] users;
    private int size = 0;
    private static final User[] DEFAULTCAPACITY_USERS = new User[10];

    UsersArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            this.users = new User[initialCapacity];
         } else {
            if (initialCapacity != 0) {
               throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
            }
            this.users = DEFAULTCAPACITY_USERS;
         }
    }

    public UsersArrayList() {
        this.users = DEFAULTCAPACITY_USERS;
    }

    public void addUser(User user) {
        if (size == users.length) {
            resizeArray();
        }
        users[size++] = user;
    }

    public User getUserById(int id) {
        for (int i = 0; i < size; i++) {
            if (users[i].getId() == id) {
                return users[i];
            }
        }
        throw new UserNotFoundException("User with ID " + id + " not found.");
    }

    public User getUserByIndex(int index) {
        if (index < 0 || index >= users.length) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds.");
        }
        return users[index];
    }

    public int getNumberOfUsers() {
        return size;
    }

    private void resizeArray() {
        User[] temp = new User[users.length + users.length / 2];
        System.arraycopy(users, 0, temp, 0, users.length);
        users = temp;
    }
}