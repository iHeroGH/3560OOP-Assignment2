package src;

public interface UserInterface {
    
    public String getID();
    public String getFormattedID(String indentation);
    public void setID(String userID);
    public boolean isRelated(UserInterface o);

}
