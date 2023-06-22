package src.UserPackage;

import src.AnalysisPackage.AnalyzerInterface;

/**
 * The UserInterface is used to implement both Users and User Groups, acting as a
 * composite's Component.
 * 
 * We introduce methods for getting the ID and setting
 * the ID as well as a method for checking if two UserInterfaces are related.
 * 
 * Relation occurs if two users are equal or if a UserGroup contains another User
 * 
 * (For example, let's define Group 1 which contains Steve and Bob, 
 * therefore Group 1 is related to Steve and Bob. Note: This does not mean Steve and Bob
 * are related).
 * 
 * We also define an acceptance method for accepting a Visitor for analysis purposes.
 * 
 * @author George Matta
 * @version 1.0
 */
public interface UserInterface {
    
    /**
     * A simple getter method for the User's ID
     * 
     * @return A String of the ID
     */
    public String getID();

    /**
     * Retrieve's the user's ID and adds a given amount of indentation before it
     * (can be used to denote a User's belonging to a Group)
     * 
     * @param indentation The indentation to add before the ID
     * @return The fully formatted String
     */
    public String getFormattedID(String indentation);

    /**
     * A simple setter method to set the User's ID. 
     * ID Validation should be done here
     * 
     * @param userID The ID to set for the user
     */
    public void setID(String userID);
    
    /**
     * Checks whether two UserInterfaces are related.
     * 
     * If this is a group, check if the other UserGroup shares the same ID
     * or if the other User is in this group
     * 
     * If this is a user, check if the other User shares the same ID
     * 
     * @param o The other UserInterface to check
     * @return A boolean denothing relation
     */
    public boolean isRelated(UserInterface o);

    /**
     * The acceptnace method for Analysis purposes with a Visitor
     * 
     * @param visitor The AnalysisInterface visitor object
     */
    public void accept(AnalyzerInterface visitor);
}