package src;

import java.util.Set;
import java.util.HashSet;

/**
 * The User class implements various methods for unique ID storage, manipulation,
 * and validation. 
 * 
 * 
 * @author George Matta
 * @version 1.0
 */
public class User implements UserInterface{
    
    /**
     * The unique ID of the User
     */
    private String userID;

    /**
     * The list of users following this user
     */
    private Set<User> followers;

    /**
     * The list of users this user follows
     */
    private Set<User> following;

    /**
     * The news feed available to this user
     */
    private Set<String> newsFeed;
    
    /**
     * The default constructor for a User
     * 
     * Delegates to the User(String) constructor using a random, valid, ID
     */
    public User(){
        this.userID = IDValidator.getInstance().findValidID();
        initializeUser();
    }

    /**
     * Creates a User given a User ID. The ID is validated in the setID(String)
     * method
     * 
     * @param userID The unique ID of the User
     */
    public User(String userID){
        this.setID(userID);
        initializeUser();
    }

    private void initializeUser(){
        UserManager.getInstance().addUser(this);
        followers = new HashSet<User>();
        following = new HashSet<User>();
        newsFeed = new HashSet<String>();   
    }

    /**
     * A simple getter method for the User's unique ID
     * @return The User's unique ID
     */
    @Override
    public String getID(){
        return this.userID;
    }

    @Override
    public String getFormattedID(String indentation){
        return indentation + "- " + this.userID;
    }

    /**
     * A simple setter method for the User's unique ID
     * 
     * This setter method makes sure the ID is not already in the usedIDs set
     * 
     * @param generatedID A randomly or manually selected ID to cross-reference
     */
    @Override
    public void setID(String generatedID){
        // If the ID is already in use
        IDValidator.getInstance().useID(generatedID);

        // Stop using old ID
        IDValidator.getInstance().dropID(userID);

        // Otherwise use it
        this.userID = generatedID;
    }

    /**
     * Follows a given user
     * @param targetID The ID of the user to follow
     */
    public void followUser(String targetID){
        User user = UserManager.getInstance().findUserByID(targetID);
        this.followers.add(user);
        user.addFollower(this);
    }   

    /**
     * Adds a user to the list of followers
     * @param user The user who followed this user
     */
    public void addFollower(User user){
        this.following.add(user);
    }

    /**
     * Checks if this UserInterface object is equal to another UserInterface object
     * 
     * @param other The other User object to check equality for
     * @return Whether or not the objects are equal
     */
    @Override
    public boolean isRelated(UserInterface other){
        return this.getID().equals(other.getID());
    }
}