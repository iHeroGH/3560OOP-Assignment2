package src.UserPackage;

import java.util.Set;

public interface PosterInterface {
    
    public void followUser(String targetID);
    public void addFollower(FollowerInterface follower);

    public Set<FollowerInterface> getFollowers();
    public Set<FollowerInterface> getFollowing();

    public void post(String message);
}
