package src.UserPackage.ManagerPackage;

import java.util.HashSet;

import src.UserPackage.User;

/**
 * The UserManager keeps track of all the Users created
 * 
 * Since this is a Singleton and extends the AbstractManager class, we define
 * methods for getting the instance and comparing items
 */
public class UserManager extends AbstractManager<User> {
    
    /**
     * The single instance of this Singleton UserManager
     */
    private static AbstractManager<User> instance = null;

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

    /**
     * Checks whether this User's ID is equal to the String
     * 
     * @param userGroup The User to check
     * @param other The ID to check
     * @return Whether the IDs are equal
     */
    @Override
    public boolean compareItems(User user, String other){
        return user.getID().equals(other);
    }
}
