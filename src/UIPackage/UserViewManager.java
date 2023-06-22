package src.UIPackage;

import java.util.HashSet;

import src.UserPackage.ManagerPackage.AbstractManager;

/**
 * The UserViewManager keeps track of all the UserViews created
 * 
 * Since this is a Singleton and extends the AbstractManager class, we define
 * methods for getting the instance and comparing items
 */
public class UserViewManager extends AbstractManager<UserView> {
    
    /**
     * The single instance of this Singleton UserManager
     */
    private static AbstractManager<UserView> instance = null;

    /**
     * Retrieves the single instance created of the UserManager
     * @return The instance of the UserManager
     */
    public static AbstractManager<UserView> getInstance(){
        if (instance == null){
            instance = new UserViewManager();
        }

        return instance;
    }
    
    /**
     * A private constructor since this class is a Singleton
     */
    private UserViewManager(){
        this.globalItemSet = new HashSet<UserView>();
    }

    /**
     * Checks whether this UserView's user's ID is equal to the String
     * 
     * @param userGroup The UserView to check
     * @param other The ID to check
     * @return Whether the IDs are equal
     */
    @Override
    public boolean compareItems(UserView userView, String other){
        return userView.getUser().getID().equals(other);
    }
}
