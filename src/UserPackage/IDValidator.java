package src.UserPackage;

import java.util.HashSet;
import java.util.Set;

public class IDValidator {

    /**
     * The single instance of this Singleton IDValidator
     */
    public static IDValidator instance = null;

    /**
     * A static Set of the IDs already in the system
     * 
     * We use a HashSet because of its constant-time access (since we're only
     * checking if an ID is contained in the set) and its unique elements
     */
    private Set<String> usedIDs = new HashSet<String>();

    /**
     * A final static field of the length of an ID.
     * This implementation uses {@value #ID_LENGTH}
     */
    private static final int ID_LENGTH = 7;

    /**
     * Retrieves the single instance created of the IDValidator
     * @return The instance of the IDValidator
     */
    public static IDValidator getInstance(){
        if (instance == null){
            instance = new IDValidator();
        }

        return instance;
    }

    /**
     * A private constructor since this class is a Singleton
     */
    private IDValidator(){} 

    /**
     * A method to check if a provided ID is already in use
     * @param idToCheck The ID to check use for
     * @return A boolean denoting whether or not the ID is in use
     */
    public boolean checkID(String idToCheck){
        return this.usedIDs.contains(idToCheck);
    }

    /**
     * A method to add a provided ID to the set of usedIDs
     * @param idToCheck The ID to add
     * @throws IllegalArgumentException if the ID is already in use
     */
    public void useID(String idToAdd){
        if (!checkID(idToAdd)){
            this.usedIDs.add(idToAdd);
            return;
        }

        throw new IllegalArgumentException("The provided ID is already in use.");
    }

    /**
     * A method to remove a provided ID from the set of usedIDs
     */
    public void dropID(String idToRemove){
        this.usedIDs.remove(idToRemove);
    }

    /**
     * A method to find a valid ID.
     * 
     * This method repeatedly, randomly, generates an ID until it finds one not
     * in use
     * 
     * @return The generated user ID (guaranteed to be unique)
     */
    public String findValidID(){
        
        // Keep generating IDs until one is found
        String generatedID = generateRandomID();
        while (this.checkID(generatedID)){
            generatedID = generateRandomID();
        }
        
        usedIDs.add(generatedID);
        return generatedID;
    }

    /**
     * A static method to generate a random ID
     * 
     * This method generates an alphanumeric, case-sensitive, `ID_LENGTH` character
     * long ID.
     * 
     * @return The generated user ID (not guaranteed to be unique)
     */
    private static String generateRandomID(){
        String possibleCharacters = "0123456789"
                                    +"ABCDEFGHIJKLMNOPQRSTUVWXYZ" 
                                    + "abcdefghijklmnopqrstuvwxyz";
        
        
        // Generate an ID of length ID_LENGTH
        String generatedID = "";
        for (int i = 0; i < ID_LENGTH; i++){
            // Find a random character from the possible characters and add it
            double randomNum = Math.random()*(possibleCharacters.length()-1) + 1;
            int characterIndex = (int) Math.floor(randomNum);
            
            generatedID += possibleCharacters.substring(characterIndex, characterIndex+1);
        }
        
        // Return the ID
        return generatedID;
    }
}
