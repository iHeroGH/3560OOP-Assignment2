package src.AnalysisPackage;

import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

import java.util.Set;

import src.UserPackage.User;
import src.UserPackage.UserGroup;

import java.util.HashSet;
import java.util.List;

public class Analyzer implements AnalyzerInterface{

    private int userCount;
    private int userGroupCount;
    private int newsFeedCount;
    private int positiveWordsCount;

    private static Set<String> positiveWords;
    private final String POSITIVE_WORDS_FILE_PATH = "src\\AnalysisPackage\\PostivieWords.txt";

    public Analyzer(){
        userCount = 0;
        userGroupCount = 0;
        newsFeedCount = 0;
        positiveWordsCount = 0;
        initializePositiveWords();
    }

    private void initializePositiveWords(){
        positiveWords = new HashSet<String>();
        
        try {
            FileReader fileInputStream = new FileReader(POSITIVE_WORDS_FILE_PATH);
            BufferedReader reader = new BufferedReader(fileInputStream);
            String currLine = reader.readLine();

            while(currLine != null){
                positiveWords.add(currLine.toLowerCase());

                currLine = reader.readLine();
            }

            reader.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void visitUser(User user) {
        userCount++;
    }

    @Override
    public void visitUserGroup(UserGroup userGroup) {
        userGroupCount++;
    }

    @Override
    public void visitNewsFeed(List<String> newsFeed) {
        newsFeedCount += newsFeed.size();

        for(String message : newsFeed){
            for(String word : message.split(" ")){
                if(positiveWords.contains(word.toLowerCase())){
                    positiveWordsCount++;
                }
            }
        }

    }

    public int getUserCount(){
        return this.userCount;
    }

    public int getUserGroupCount(){
        return this.userGroupCount;
    }

    public int getNewsFeedCount(){
        return this.newsFeedCount;
    }

    public float getSentiment(){
        if (this.newsFeedCount == 0){
            return 0;
        }

        return ((float) this.positiveWordsCount / this.newsFeedCount) * 100;
    }
    
}
