package src.UserPackage.ManagerPackage;

import java.util.HashSet;

import src.UserPackage.User;

public class UserManager extends AbstractManager<User> {
    
    /**
     * The single instance of this Singleton UserManager
     */
    public static AbstractManager<User> instance = null;

    /**
     * Retrieves the single instance created of the UserManager
     * @return The instance of the UserManager
     */
    public static AbstractManager<User> getInstance(){
        if (instance == null){
            instance = new UserManager();
        }

        return instance;
    }
    
    /**
     * A private constructor since this class is a Singleton
     */
    private UserManager(){
        this.globalItemSet = new HashSet<User>();
    }

    @Override
    public boolean compareItems(User user, String other){
        return user.getID().equals(other);
    }
}
