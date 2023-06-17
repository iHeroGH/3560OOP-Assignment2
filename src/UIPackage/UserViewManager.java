package src.UIPackage;

import java.util.HashSet;

import src.UserPackage.ManagerPackage.AbstractManager;

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

    @Override
    public boolean compareItems(UserView userView, String other){
        return userView.getUser().getID().equals(other);
    }
}
