package src.UserPackage;

/**
 * The RootGroup class (extends UserGroup) is a Singleton that maintains
 * existence of only one Root Group.
 * 
 * The Root Group contains all other UserGroups.
 * 
 * @author George Matta
 * @version 1.0
 */
public class RootGroup extends UserGroup {

    /**
     * The single instance of this Singleton RootGroup
     */
    private static RootGroup instance = null;
    
    /**
     * Retrieves the single instance created of the RootGroup
     * 
     * @return The instance of the RootGroup
     */
    public static RootGroup getInstance(){
        if (instance == null){
            instance = new RootGroup();
        }

        return instance;
    }

    /**
     * Initializes the Root Group with the "Root" ID
     */
    private RootGroup(){
        super("Root");
    }

    /**
     * Retrieves the Root Group's formatted ID with an indentation at the start.
     * 
     * Since this is the Root Group, we don't add the "-" character
     * 
     * @param indentation The indentation to use at the start of the ID
     * @return The fully formatted ID (contains the rest of the tree view)
     */
    @Override
    public String getFormattedID(String indentation){
        String formattedID = indentation + "**" + this.getID() + "***";
        formattedID += super.getTreeString(indentation);

        return formattedID;
    }

}
