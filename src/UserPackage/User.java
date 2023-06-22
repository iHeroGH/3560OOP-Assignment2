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
 * The User class implements interfaces for the UserInterface composite component, 
 * a PosterInterface to serve as an Subject, and a FollowerInterface to serve as an
 * Observer. 
 * 
 * All these Interfaces require their own list of methods that are identified below.
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
     * The list of Followers following this Poster
     */
    private Set<FollowerInterface> followers;

    /**
     * The list of Posters this follower follows
     */
    private Set<PosterInterface> following;

    /**
     * The news feed available to this user
     */
    private List<String> newsFeed;

    /**
     * The default constructor for a User
     * Finds a random but valid ID and initializes it
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

    /**
     * Preforms the rest of the initalization for the User (setting up the
     * followers, following, and newsFeed attributes, 
     * and adding this user to the Manager).
     */
    private void initializeUser(){
        UserManager.getInstance().addItem(this);
        followers = new HashSet<FollowerInterface>();
        following = new HashSet<PosterInterface>();
        newsFeed = new ArrayList<String>();   
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

        // Stop using old ID if there is one
        IDValidator.getInstance().dropID(userID);

        // Otherwise use it
        this.userID = generatedID;
    }

    /**
     * A simple getter method for the User's unique ID
     * 
     * @return The User's unique ID
     */
    @Override
    public String getID(){
        return this.userID;
    }

    /**
     * Gets the User's ID with indentation at the front and a dash symbol.
     * 
     * ie: \t- ID
     * 
     */
    @Override
    public String getFormattedID(String indentation){
        return indentation + "- " + this.userID;
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
     * 
     * @param targetID The ID of the user to follow
     * @throws IllegalArgumentException if a user tries to follow themselves 
     *                                  or the user wasn't found
     */
    @Override
    public void followUser(String targetID){
        if (targetID.equals(this.userID)){
            throw new IllegalArgumentException("A User cannot follow themselves.");
        }

        User user = UserManager.getInstance().findItem(targetID);
        this.following.add(user);
        user.addFollower(this);
    }   

    /**
     * Adds a user to the list of followers
     * 
     * @param follower The user who followed this user
     */
    @Override
    public void addFollower(FollowerInterface follower){
        this.followers.add(follower);
    }

    /**
     * Retrieves this users followers list
     * 
     * @return The set of users who follow this user
     */
    @Override
    public Set<FollowerInterface> getFollowers(){
        return this.followers;
    }
    
    /** 
     * Retrieves this user's followed users
     * 
     * @return The set of users this user follows
     */
    @Override
    public Set<PosterInterface> getFollowing(){
        return this.following;
    }

    /**
     * Posts a message from this User.
     * 
     * We add the given message to the User's news feed as UserID (you): message.
     * 
     * We also update all the followers with the message
     * 
     * @param message The message String to post
     */
    @Override
    public void post(String message){
        // Add the message to ourselves
        this.newsFeed.add(this.userID + " (you): " + message);

        // Update our followers
        for(FollowerInterface follower : followers){
            follower.update(this.userID, message);
        }
    }

    /**
     * Recieves an update from a User that we follow.
     * 
     * We simply add the message to the news feed as posterID: message.
     * 
     * @param posterID The ID of the user who posted the message
     * @param message The message they typed
     */
    @Override
    public void update(String posterID, String message){
        this.newsFeed.add(posterID + ": " + message);
    }

    /**
     * A simple getter method to retrieve the news feed
     * 
     * @return The List of formatted messages (formatted with the poster's ID)
     */
    public List<String> getNewsFeed(){
        return this.newsFeed;
    }

    /**
     * Accepts an AnalyzerInterface Visitor for analytics like user count and message
     * analysis
     * 
     * @param visitor The AnalyzerInterface visitor to accept
     */
    @Override
    public void accept(AnalyzerInterface visitor){
        visitor.visitUser(this);
        visitor.visitNewsFeed(this.newsFeed);
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

}