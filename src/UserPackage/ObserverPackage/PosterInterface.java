package src.UserPackage.ObserverPackage;

import java.util.Set;

/**
 * The PosterInterface defines an object that can make posts. This acts as the
 * Subject of our Observer pattern
 * 
 * One who can make posts can be followed by other Users and can get the list of
 * Users following them (their followers). Of course, a Poster can also post message.
 * 
 * @author George Matta
 * @version 1.0
 */
public interface PosterInterface {
    
    /**
     * Adds a follower to the list of followers for this Poster
     * 
     * @param follower The FollowerInterface to add
     */
    public void addFollower(FollowerInterface follower);

    /**
     * Retrieves the Set of Followers who follow this Poster
     * 
     * @return The Set of Followers following this Poster
     */
    public Set<FollowerInterface> getFollowers();

    /**
     * Posts a message from this Poster
     * 
     * @param message The message to post
     */
    public void post(String message);
}
