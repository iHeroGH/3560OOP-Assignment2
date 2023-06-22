package src.AnalysisPackage;

import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

import java.util.Set;

import src.UserPackage.User;
import src.UserPackage.UserGroup;

import java.util.HashSet;
import java.util.List;

/**
 * The Analyzer implements the AnalyzerInterface and implements methods for
 * calculating the program's statistics
 * 
 * @author George Matta
 * @version 1.0
 */
public class Analyzer implements AnalyzerInterface{

    /**
     * The number of Users created
     */
    private int userCount;

    /** 
     * The number of Groups created
     */
    private int userGroupCount;

    /**
     * The total number of posts made
     */
    private int newsFeedCount;

    /**
     * The number of messages containing a positive word
     */
    private int positiveMessagesCount;

    /**
     * A static set of the positive words extracted from the positive words textfile path
     */
    private static Set<String> positiveWords;
    
    /**
     * A static final String of the path containing the positive words to initialize
     */
    private final String POSITIVE_WORDS_FILE_PATH = "src\\AnalysisPackage\\PostivieWords.txt";

    /**
     * The default constructor for the Analyzer
     * 
     * Sets the counts for all the attributes and initializes the positive words set
     */
    public Analyzer(){
        resetCounts();
        initializePositiveWords();
    }

    /**
     * Sets the counts of all the statistics to 0
     */
    public void resetCounts(){
        userCount = 0;
        userGroupCount = 0;
        newsFeedCount = 0;
        positiveMessagesCount = 0;
    }

    /**
     * Reads the Positive Words text file and adds each word to the set
     */
    private void initializePositiveWords(){
        // Define the positive words set
        positiveWords = new HashSet<String>();
        
        try {
            // Initialize the reader
            FileReader fileInputStream = new FileReader(POSITIVE_WORDS_FILE_PATH);
            BufferedReader reader = new BufferedReader(fileInputStream);
            
            // While there is a line to read
            String currLine = reader.readLine();
            while(currLine != null){
                // Add the word to the set
                positiveWords.add(currLine.toLowerCase());
                
                // Read the next line
                currLine = reader.readLine();
            }

            // Close the reader
            reader.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Increments the User count
     * 
     * @param user The User to analzye
     */
    @Override
    public void visitUser(User user) {
        userCount++;
    }

    /**
     * Increments the Group count
     * 
     * @param userGroup The UserGroup to analyze
     */
    @Override
    public void visitUserGroup(UserGroup userGroup) {
        userGroupCount++;
    }

    /**
     * Increments the message count and reads each word in the message to check
     * if a positive word was found
     * 
     * @param newsFeed the newsFeed to analyze
     */
    @Override
    public void visitNewsFeed(List<String> newsFeed) {
        newsFeedCount += newsFeed.size();

        for(String message : newsFeed){
            for(String word : message.split(" ")){
                if(positiveWords.contains(word.toLowerCase())){
                    positiveMessagesCount++;
                    break;
                }
            }
        }

    }

    /**
     * {@inheritDoc}}
     */
    public int getUserCount(){
        return this.userCount;
    }

    /**
     * {@inheritDoc}}
     */
    public int getUserGroupCount(){
        return this.userGroupCount;
    }

    /**
     * {@inheritDoc}}
     */
    public int getNewsFeedCount(){
        return this.newsFeedCount;
    }

    /**
     * {@inheritDoc}}
     */
    public float getSentiment(){
        if (this.newsFeedCount == 0){
            return 0;
        }

        return ((float) this.positiveMessagesCount / this.newsFeedCount) * 100;
    }
    
}
