package src.UserPackage;

import java.util.ArrayList;
import java.util.List;

import src.AnalysisPackage.AnalyzerInterface;
import src.UserPackage.ManagerPackage.UserGroupManager;

/**
 * The UserGroup class implements the UserInterface Composite Component to allow
 * UserGroups to hold other UserGroups and other Users.
 *
 * @author George Matta
 * @version 1.0
 */
public class UserGroup implements UserInterface {

    /**
     * The unique ID of the User Group
     */
    private String groupID;

    /**
     * The creation time of the UserGroup
     */
    private long creationTime;

    /**
     * The list of UserInterfaces held in this Group
     */
    private List<UserInterface> users;

    /**
     * The default constructor for a UserGroup
     *
     * Initializes a group with a random, valid, ID
     */
    public UserGroup(){
        this.groupID = IDValidator.getInstance().findValidID();
        initializeUserGroup();
    }

    /**
     * Initializes a UserGroup with a set ID
     *
     * The ID is validated.
     *
     * @param groupID The Group ID to set for this Group
     */
    public UserGroup(String groupID){
        this.setID(groupID);
        initializeUserGroup();
    }

    /**
     * Preforms the rest of the initalization for the UserGroup (setting up the
     * user list, adding this group the Manager).
     */
    private void initializeUserGroup(){
        UserGroupManager.getInstance().addItem(this);
        users = new ArrayList<UserInterface>();
        creationTime = System.currentTimeMillis();
    }

    /**
     * A simple getter method to retrieve the UserGroup object's creation time
     *
     * @return The creation time of the UserGroup
     */
    public long getCreationTime(){
        return this.creationTime;
    }

    /**
     * A simple getter method to get the Group's ID
     */
    @Override
    public String getID() {
        return this.groupID;
    }

    /**
     * Retrieves the Group's ID as a formatted String with initially 0 indentation
     * (would be used for a Root group).
     *
     * @return The value of getFormattedID("")
     */
    public String getFormattedID(){
        return getFormattedID("");
    }

    /**
     * Retrieves the Group's ID as a formatted String with a given indentation
     *
     * We format the String like so: indentation- **groupID***, and then
     * an additional level of indentation for UserInterfaces contained in this group
     *
     * @param indentation The initial indentation to use for the Group
     * @return The formatted message of this Group and all its UserInterfaces
     */
    @Override
    public String getFormattedID(String indentation){
        String formattedID = indentation + "- **" + this.groupID+ "***";
        formattedID += getTreeString(indentation);

        return formattedID;
    }

    /**
     * Gets the Tree of all this Group's users (keeping track of identation)
     *
     * @param indentation The intial indentation to use
     * @return The final String of all the UserInterfaces in the list
     */
    protected String getTreeString(String indentation){
        String treeString = "";
        // If there are users, get their IDs and add them to the String
        if (users != null){
            for(UserInterface user : users){
                treeString += "\n" + user.getFormattedID(indentation + "    ");
            }
        }

        return treeString;
    }

    /**
     * A simple setter to replace the Group ID (remove the old one, validate and set
     * the new one)
     *
     * @param groupID The new Group ID to use
     */
    @Override
    public void setID(String groupID) {
        IDValidator.getInstance().useID(groupID);

        // Stop using old ID
        IDValidator.getInstance().dropID(this.groupID);

        this.groupID = groupID;
    }

    /**
     * Adds a UserInterface to the list of UserInterfaces held by this Group
     *
     * @param user The UserInterface to add
     * @throws IllegalArgumentException if the UserInterface is related to this Group
     */
    public void addUser(UserInterface user){
        // Only add it if it isn't already in the group
        if(!this.isRelated(user)){
            this.users.add(user);
        } else {
            throw new IllegalArgumentException("That UserInterface is related to this UserGroup");
        }
    }

    /**
     * Defines relation between a Group and a UserInterface as:
     * if the IDs are the same, or if any Users in the UserList are related to the
     * other UserInterface
     *
     * @param other The other UserInterface to check
     * @return Whether or not the UserInterfaces are related
     */
    @Override
    public boolean isRelated(UserInterface other){
        if (this.getID().equals(other.getID())){
            return true;
        }

        for(UserInterface user : users){
            if(user.isRelated(other) || other.isRelated(user) || user.getID().equals(other.getID())){
                return true;
            }
        }

        return false;
    }

    /**
     * Accepts an AnalyzerInterface Visitor for analysis purposes
     *
     * Counts this group and visits every UserInterface in the Group
     *
     * @param visitor The AnalyzerInterface Visitor to accept
     */
    @Override
    public void accept(AnalyzerInterface visitor){
        visitor.visitUserGroup(this);
        for(UserInterface user : this.users){
            user.accept(visitor);
        }
    }

}
