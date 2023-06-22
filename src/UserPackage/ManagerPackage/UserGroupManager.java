package src.UserPackage.ManagerPackage;

import java.util.HashSet;

import src.UserPackage.UserGroup;

/**
 * The UserGroupManager keeps track of all the UserGroups created
 * 
 * Since this is a Singleton and extends the AbstractManager class, we define
 * methods for getting the instance and comparing items
 */
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

    /**
     * Checks whether this User Group's ID is equal to the String
     * 
     * @param userGroup The UserGroup to check
     * @param other The ID to check
     * @return Whether the IDs are equal
     */
    @Override
    public boolean compareItems(UserGroup userGroup, String other) {
        return userGroup.getID().equals(other);
    }
}
