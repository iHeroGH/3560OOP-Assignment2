package src.UserPackage;

public class UserFactory {
    
    public static UserInterface createUser(){
        return new User();
    }

    public static UserInterface createUser(String userID){
        return new User(userID);
    }

    public static UserInterface createUserGroup(){
        return new UserGroup();
    }

    public static UserInterface createUserGroup(String groupID){
        return new UserGroup(groupID);
    }

}
