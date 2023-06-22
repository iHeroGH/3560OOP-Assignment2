package src.UserPackage;

/**
 * A simple Factory class to create Users and UserGroups.
 * 
 * Users and Groups can be created with or without an ID (if given an ID, it will
 * be validated. If ID is ommited, a random one will be generated)
 * 
 * @author George Matta
 * @version 1.0
 */
public class UserFactory {
    
    /**
     * Creates a User with a random ID
     * 
     * @return The User object created
     */
    public static User createUser(){
        return new User();
    }

    /**
     * Creates a User with a set ID
     * 
     * @param userID The ID to use for the constructor
     * @return The User object created
     */
    public static User createUser(String userID){
        return new User(userID);
    }

    /**
     * Creates a UserGroup with a random ID
     * 
     * @return The UserGroup object created
     */
    public static UserGroup createUserGroup(){
        return new UserGroup();
    }

    /**
     * Creates a UserGroup with a set ID
     * 
     * @param groupID The ID to use for the constructor
     * @return The UserGroup object created
     */
    public static UserGroup createUserGroup(String groupID){
        return new UserGroup(groupID);
    }

}
