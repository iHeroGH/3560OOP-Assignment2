package src.UserPackage.ObserverPackage;

import java.util.Set;

/**
 * The FollowerInterface defines an object that can follow Posters and be
 * updated when they post a message. This acts as the Observer of our Observer pattern
 * 
 * @author George Matta
 * @version 1.0
 */
public interface FollowerInterface {
    
    /**
     * Follows a Poster
     * @param targetID The ID of the Poster to follow
     */
    public void followUser(String targetID);

    /**
     * The Set of Posters who this Follower follows
     * @return The set of Posters
     */
    public Set<PosterInterface> getFollowing();

    /**
     * Updates this Follower with the post that a Poster made
     * @param posterID The Poster's ID
     * @param message The message posted by the Poster
     */
    public void update(String posterID, String message);

}
