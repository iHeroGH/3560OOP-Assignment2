package src.UserPackage;

public class UserFactory {
    
    public static User createUser(){
        return new User();
    }

    public static User createUser(String userID){
        return new User(userID);
    }

    public static UserGroup createUserGroup(){
        return new UserGroup();
    }

    public static UserGroup createUserGroup(String groupID){
        return new UserGroup(groupID);
    }

}
