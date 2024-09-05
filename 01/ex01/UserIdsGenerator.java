package ex01;

public class UserIdsGenerator {
    private static int id = 0;

    private static UserIdsGenerator instance = null;

    public static synchronized UserIdsGenerator getInstance() {
        if (instance == null) {
            instance = new UserIdsGenerator();
        }
        return instance;
    }

    private UserIdsGenerator() {
    }
    
    public int genereateId() {
        return id++;
    }
}
