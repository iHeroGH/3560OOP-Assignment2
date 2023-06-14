package src;

import java.util.HashSet;
import java.util.Set;

public class UserGroup implements UserInterface{
    
    private String groupID;
    private Set<UserInterface> userSet;

    public UserGroup(String groupID){
        this.setID(groupID);
        this.userSet = new HashSet<UserInterface>();
    }

    @Override
    public String getID() {
        return this.groupID;
    }

    @Override
    public void setID(String groupID) {
        IDValidator.getInstance().useID(groupID);
        this.groupID = groupID;
    }

}
