package src;

import java.util.Set;

import javax.naming.NameNotFoundException;

import java.util.HashSet;

public class UserManager {
    
    /**
     * The single instance of this Singleton UserManager
     */
    public static UserManager instance = null;

    private Set<User> globalUserSet;

    /**
     * Retrieves the single instance created of the UserManager
     * @return The instance of the UserManager
     */
    public static UserManager getInstance(){
        if (instance == null){
            instance = new UserManager();
        }

        return instance;
    }
    
    /**
     * A private constructor since this class is a Singleton
     */
    private UserManager(){
        this.globalUserSet = new HashSet<User>();
    }

    /**
     * Adds a user to the global users set
     * @param user The user to add to the set
     */
    public void addUser(User user){
        globalUserSet.add(user);
    }

    /**
     * Retrieves the global user set
     */
    public Set<User> getUserSet(){
        return this.globalUserSet;
    }

    /**
     * Finds a user by their ID
     * @param targetID The ID of the user to find
     * @return The user found by the ID
     * @throws IllegalArgumentException if the user was not found
     */
    public User findUserByID(String targetID){
        for(User user : globalUserSet){
            if (user.getID() == targetID){
                return user;
            }
        }

        throw new IllegalArgumentException("The requested ID does not belong to any users.");
    }
}
