package src.UserPackage.ManagerPackage;

import java.util.HashSet;

import src.UserPackage.UserGroup;

public class UserGroupManager extends AbstractManager<UserGroup> {
    
    /**
     * The single instance of this Singleton UserGroupManager
     */
    private static AbstractManager<UserGroup> instance = null;

    /**
     * Retrieves the single instance created of the UserGroupManager
     * @return The instance of the UserGroupManager
     */
    public static AbstractManager<UserGroup> getInstance(){
        if (instance == null){
            instance = new UserGroupManager();
        }

        return instance;
    }
    
    /**
     * A private constructor since this class is a Singleton
     */
    private UserGroupManager(){
        this.globalItemSet = new HashSet<UserGroup>();
    }

    @Override
    public boolean compareItems(UserGroup userGroup, String other) {
        return userGroup.getID().equals(other);
    }
}
