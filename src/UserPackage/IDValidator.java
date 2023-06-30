package src.UserPackage;

import java.util.HashSet;
import java.util.Set;

/**
 * The IDValidator Singleton class provides a hub of validation for User IDs
 *
 * In short, User IDs are unique (throughout both Groups and Users), and
 * other validations could be done here as well (length, illegal characters, etc)
 *
 * Since this is a Singleton, we only define one instance that is shared amongst
 * any classes that need IDs in the program
 *
 * @author George Matta
 * @version 1.0
 */
public class IDValidator {

    /**
     * The single instance of this Singleton IDValidator
     */
    private static IDValidator instance = null;

    /**
     * A static Set of the IDs already in the system
     *
     * We use a HashSet because of its constant-time access (since we're only
     * checking if an ID is contained in the set) and its unique elements
     */
    private Set<String> usedIDs = new HashSet<String>();

    /**
     * A final static field of the length of a random ID.
     * This implementation uses {@value #ID_LENGTH}
     */
    private static final int ID_LENGTH = 7;

    /**
     * Retrieves the single instance created of the IDValidator
     *
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
     *
     * @param idToCheck The ID to check use for
     * @return A boolean denoting whether or not the ID is in use
     */
    public boolean checkExists(String idToCheck){
        return this.usedIDs.contains(idToCheck);
    }

    /**
     * A method to check if a provided ID is valid
     *
     * @param idToCheck The ID to check use for
     * @return A boolean denoting whether or not the ID is valid
     */
    public boolean checkValid(String idToCheck){
        return (!containsStar(idToCheck) ||
                 !containsSpace(idToCheck) ||
                 idToCheck != null);
    }

    /**
     * Checks if the given String contains a * character
     *
     * @param idToCheck The String to check
     * @return True if * is in the String
     */
    public boolean containsStar(String idToCheck){
        return idToCheck.contains("*");
    }

    /**
     * Checks if the given String contains a space
     *
     * @param idToCheck The String to check
     * @return True if a space is in the String
     */
    public boolean containsSpace(String idToCheck){
        return idToCheck.contains(" ");
    }

    /**
     * A method to add a provided ID to the set of usedIDs
     *
     * @param idToAdd The ID to add
     * @throws IllegalArgumentException if the ID is already in use
     */
    public void useID(String idToAdd){
        boolean exists = checkExists(idToAdd);
        boolean valid = checkValid(idToAdd);

        // // If the ID is valid
        // if (!valid){
        //     throw new IllegalArgumentException("IDs cannot contain the * or space characters.");
        // }

        // If the ID already exists
        if(exists){
            throw new IllegalArgumentException("The provided ID is already in use.");
        }

        // Add it to the set
        this.usedIDs.add(idToAdd);
    }

    /**
     * A method to remove a provided ID from the set of usedIDs
     *
     * @param idToRemove The ID to remove from the set
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
        while (checkExists(generatedID) || !checkValid(generatedID)){
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
