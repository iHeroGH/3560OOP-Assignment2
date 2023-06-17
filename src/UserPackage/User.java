package src.UserPackage;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;

import src.AnalysisPackage.AnalyzerInterface;
import src.UserPackage.ManagerPackage.UserManager;
import src.UserPackage.ObserverPackage.FollowerInterface;
import src.UserPackage.ObserverPackage.PosterInterface;

import java.util.HashSet;

/**
 * The User class implements various methods for unique ID storage, manipulation,
 * and validation. 
 * 
 * 
 * @author George Matta
 * @version 1.0
 */
public class User implements UserInterface, PosterInterface, FollowerInterface {
    
    /**
     * The unique ID of the User
     */
    private String userID;
    
    /**
     * The list of users following this Poster
     */
    private Set<FollowerInterface> followers;

    /**
     * The list of users this Poster follows
     */
    private Set<FollowerInterface> following;

    /**
     * The news feed available to this user
     */
    private List<String> newsFeed;

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
        UserManager.getInstance().addItem(this);
        followers = new HashSet<FollowerInterface>();
        following = new HashSet<FollowerInterface>();
        newsFeed = new ArrayList<String>();   
    }

    /**
     * A simple getter method for the User's unique ID
     * @return The User's unique ID
     */
    @Override
    public String getID(){
        return this.userID;
    }

    /**
     * A String representation of the User object
     * 
     * @return The User formatted as User(ID)
     */
    @Override
    public String toString(){
        return "User(" + getID() + ")";
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
     * Checks if this UserInterface object is equal to another UserInterface object
     * 
     * @param other The other User object to check equality for
     * @return Whether or not the objects are equal
     */
    @Override
    public boolean isRelated(UserInterface other){
        return this.getID().equals(other.getID());
    }

    /**
     * Follows a given user
     * @param targetID The ID of the user to follow
     * @throws IllegalArgumentException if a user tries to follow themselves 
     *                                  or the user wasn't found
     */
    public void followUser(String targetID){
        if (targetID.equals(this.userID)){
            throw new IllegalArgumentException("A User cannot follow themselves.");
        }

        User user = UserManager.getInstance().findItemByID(targetID);
        this.following.add(user);
        user.addFollower(this);
    }   

    /**
     * Adds a user to the list of followers
     * @param user The user who followed this user
     */
    @Override
    public void addFollower(FollowerInterface follower){
        this.followers.add(follower);
    }

    /**
     * Retrieves this users followers list
     * @return The set of users who follow this user
     */
    @Override
    public Set<FollowerInterface> getFollowers(){
        return this.followers;
    }
    
    /** 
     * Retrieves this user's followed users
     * @return The set of users this user follows
     */
    @Override
    public Set<FollowerInterface> getFollowing(){
        return this.following;
    }

    @Override
    public void post(String message){
        this.newsFeed.add(this.userID + " (You): " + message);

        for(FollowerInterface follower : followers){
            follower.update(this.userID, message);
        }
    }

    @Override
    public void update(String posterID, String message){
        this.newsFeed.add(posterID + ": " + message);
    }

    public List<String> getNewsFeed(){
        return this.newsFeed;
    }

    @Override
    public void accept(AnalyzerInterface visitor){
        visitor.visitUser(this);
        visitor.visitNewsFeed(this.newsFeed);
    }
}