package src.UserPackage;

import java.util.ArrayList;
import java.util.List;

import src.AnalysisPackage.AnalyzerInterface;

public class UserGroup implements UserInterface {
    
    protected String groupID;

    protected List<UserInterface> users;

    /**
     * The default constructor for a UserGroup
     * 
     * Delegates to the UserGroup(String) constructor using a random, valid, ID
     */
    public UserGroup(){
        this.groupID = IDValidator.getInstance().findValidID();
        this.users = new ArrayList<UserInterface>();
    }

    public UserGroup(String groupID){
        this.setID(groupID);
        this.users = new ArrayList<UserInterface>();
    }

    @Override
    public String getID() {
        return this.groupID;
    }

    public String getFormattedID(){
        return getFormattedID("");
    }

    @Override
    public String getFormattedID(String indentation){
        String formattedID = indentation + "- <b>" + this.groupID+ "</b>";
        formattedID += getTreeString(indentation);

        return formattedID;
    }

    public String getTreeString(String indentation){
        String treeString = "";
        if (users != null){
            for(UserInterface user : users){
                treeString += "<br>" + user.getFormattedID(indentation + "&emsp;&emsp;");
            }
        }

        return treeString;
    }

    @Override
    public void setID(String groupID) {
        IDValidator.getInstance().useID(groupID);

        // Stop using old ID
        IDValidator.getInstance().dropID(this.groupID);

        this.groupID = groupID;
    }

    public void addUser(UserInterface user){
        if(!this.isRelated(user)){
            this.users.add(user);
        } else {
            throw new IllegalArgumentException();
        }
    }

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

    @Override
    public void accept(AnalyzerInterface visitor){
        visitor.visitUserGroup(this);
        for(UserInterface user : this.users){
            user.accept(visitor);
        }
    }

}
