package src.UserPackage.ManagerPackage;

import java.util.Set;

/**
 * The AbstractManager defines an AbstractClass that can Manage any generic type.
 * 
 * The AbstractManager keeps track of a Global Item Set. Items can be added to the set,
 * and Items can be found from the 
 * 
 * @param <T> The Type of Object that this Manager manages
 * 
 * @author George Matta
 * @version 1.0
 */
public abstract class AbstractManager<T> {

    /**
     * The Global Item Set managed by this Manager
     */
    protected Set<T> globalItemSet;

    /**
     * Adds a item to the global items set
     * @param item The item to add to the set
     */
    public void addItem(T item){
        globalItemSet.add(item);
    }
    
    /**
     * Retrieves the global item set
     * 
     * @return The Global Item Set
     */
    public Set<T> getUserItemSet(){
        return this.globalItemSet;
    }
    
    /**
     * Finds a item based on the compareItems criteria
     * 
     * @param targetID The ID of the item to find
     * @return The item found by the ID
     * @throws IllegalArgumentException if the item was not found
     */
    public T findItem(String targetID){
        for(T item : globalItemSet){
            if (compareItems(item, targetID)){
                return item;
            }
        }

        throw new IllegalArgumentException(
            "The requested ID does not belong to the requested Item Type."
        );
    }

    /**
     * An abstract comparison method that should be overriden depending on what
     * type of object this manager should manage
     * 
     * @param item The Item to compare with the other
     * @param other The String to compare this Item with
     * @return Whether the items are equal
     */
    public abstract boolean compareItems(T item, String other);
}
