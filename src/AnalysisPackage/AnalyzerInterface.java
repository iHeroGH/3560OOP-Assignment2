package src.AnalysisPackage;

import java.util.List;

import src.UserPackage.User;
import src.UserPackage.UserGroup;

/**
 * The AnalyzerInterface serves as the Visitor for our program and keeps track of
 * analytical statistics of the Twitter console
 * 
 * We keep track of the user count, the group count, the message count, and the 
 * message sentiment
 * 
 * @author George Matta
 * @version 1.0
 */
public interface AnalyzerInterface {
    
    /**
     * Visits a User
     * @param user The User to visit
     */
    public void visitUser(User user);

    /**
     * Visits a User Group and all its Users
     * @param userGroup The UserGroup to visit
     */
    public void visitUserGroup(UserGroup userGroup);

    /**
     * Visits a newsFeed and keeps track of message count and sentiment
     * @param newsFeed The newsFeed to visit
     */
    public void visitNewsFeed(List<String> newsFeed);

    /**
     * Retrieves the User Count
     * @return The number of Users created
     */
    public int getUserCount();

    /**
     * Retrieves the Group count
     * @return The number of Groups created
     */
    public int getUserGroupCount();

    /**
     * Retrieves the newsFeed count
     * @return The number of messages posted
     */
    public int getNewsFeedCount();

    /**
     * Retrieves the sentiment of the posts
     * @return The ratio of messages containing a positive word / all messages
     */
    public float getSentiment();
    
}
