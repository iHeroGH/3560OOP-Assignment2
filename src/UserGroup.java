package src;

import java.util.HashSet;
import java.util.Set;

public class UserGroup implements UserInterface{
    
    private String groupID;

    private Set<UserInterface> userSet;

    /**
     * The default constructor for a UserGroup
     * 
     * Delegates to the UserGroup(String) constructor using a random, valid, ID
     */
    public UserGroup(){
        this.groupID = IDValidator.getInstance().findValidID();
        this.userSet = new HashSet<UserInterface>();
    }

    public UserGroup(String groupID){
        this.setID(groupID);
        this.userSet = new HashSet<UserInterface>();
    }

    @Override
    public String getID() {
        return this.getID("");
    }

    @Override
    public String getID(String indentation){
        String userIDs = indentation + "- " + this.groupID.toUpperCase() + "";
        if (userSet != null){
            for(UserInterface user : userSet){
                userIDs += "\n" + user.getID(indentation + "  ");
            }
        }

        return userIDs;
    }

    @Override
    public void setID(String groupID) {
        IDValidator.getInstance().useID(groupID);
        this.groupID = groupID;
    }

    public void addUser(UserInterface user){
        this.userSet.add(user);
    }

}
