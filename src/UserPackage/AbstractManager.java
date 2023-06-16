package src.UserPackage;

import java.util.Set;

public abstract class AbstractManager<T> {

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
     */
    public Set<T> getUserItemSet(){
        return this.globalItemSet;
    }
    
    /**
     * Finds a item by their ID
     * @param targetID The ID of the item to find
     * @return The item found by the ID
     * @throws IllegalArgumentException if the item was not found
     */
    public T findItemByID(String targetID){
        for(T item : globalItemSet){
            if (compareItems(item, targetID)){
                return item;
            }
        }

        throw new IllegalArgumentException(
            "The requested ID does not belong to the requested Item Type."
        );
    }

    public abstract boolean compareItems(T item, String other);
}
