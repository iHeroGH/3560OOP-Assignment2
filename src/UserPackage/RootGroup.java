package src.UserPackage;

public class RootGroup extends UserGroup {

    /**
     * The single instance of this Singleton RootGroup
     */
    public static RootGroup instance = null;
    
    /**
     * Retrieves the single instance created of the RootGroup
     * @return The instance of the RootGroup
     */
    public static RootGroup getInstance(){
        if (instance == null){
            instance = new RootGroup();
        }

        return instance;
    }

    private RootGroup(){
        super("Root");
    }

    public void addUser(UserInterface userToAdd){
        if(!this.isRelated(userToAdd)){
            this.users.add(userToAdd);
        } else {
            throw new IllegalArgumentException();
        }

    }

    @Override
    public String getFormattedID(String indentation){
        String formattedID = indentation + "<b>" + this.groupID + "</b>";
        formattedID += super.getTreeString(indentation);

        return formattedID;
    }

}