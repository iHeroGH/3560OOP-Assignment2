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
    public String getID(String indentation){
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
     * Retrieves the User ID's hashcode
     * 
     * @return The User ID's hashcode (retrieved by calling hashcode() on it).
     */
    @Override
    public int hashCode(){
        return this.userID.hashCode();
    }

    /**
     * Checks if this User object is equal to another object
     * 
     * This method checks if the other object is an User object, then delegates
     * to the equals(User) method
     * 
     * @param other The other object to check equality for
     * @return Whether or not the objects are equal
     */
    @Override
    public boolean equals(Object other){
        if (!(other instanceof User)){
            return false;
        }

        return this.equals((User) other);
    }

    /**
     * Checks if this User object is equal to another User object
     * 
     * This method delegates to the equals(String) method to check if the User
     * object equals the other's User ID
     * 
     * @param other The other User object to check equality for
     * @return Whether or not the objects are equal
     */
    public boolean equals(User other){
        return this.equals(other.userID);
    }

    /**
     * Checks if this User object is equal to a String. They are equal if
     * this User ID is equal to the given string
     * 
     * Delegates to the String.equals(String) method
     * 
     * @param userID The String to compare userIDs with
     * @return Whether or not the userIDs are equal
     */
    public boolean equals(String userID){
        return this.userID.equals(userID);
    }
}