package src.UIPackage;

import java.awt.Font;

import javax.swing.JLabel;

import src.AnalysisPackage.Analyzer;
import src.UserPackage.RootGroup;

public class AnalysisLabels {
    
    private Font DEFAULT_FONT = new Font(UIConstants.DEFAULT_FONT_NAME, 1, 15);

    private JLabel userCountLabel;
    private JLabel groupCountLabel;
    private JLabel messageCountLabel;
    private JLabel sentimentValueLabel;

    private JLabel[] allLabels;

    public AnalysisLabels(){
        this.userCountLabel = new JLabel("", JLabel.CENTER);
        this.userCountLabel.setFont(DEFAULT_FONT);

        this.groupCountLabel = new JLabel("", JLabel.CENTER);
        this.groupCountLabel.setFont(DEFAULT_FONT);
        
        this.messageCountLabel = new JLabel("", JLabel.CENTER);
        this.messageCountLabel.setFont(DEFAULT_FONT);
        
        this.sentimentValueLabel = new JLabel("", JLabel.CENTER);
        this.sentimentValueLabel.setFont(DEFAULT_FONT);

        allLabels = new JLabel[]{
                    userCountLabel, 
                    groupCountLabel, 
                    messageCountLabel, 
                    sentimentValueLabel
                };
    }

    public void updateLabels(RootGroup root){
        Analyzer analyzer = new Analyzer();
        
        root.accept(analyzer);

        userCountLabel.setText("User Count: " + analyzer.getUserCount());
        groupCountLabel.setText("Group Count: " + analyzer.getUserGroupCount());
        messageCountLabel.setText("Msg Count: " + analyzer.getNewsFeedCount());
        sentimentValueLabel.setText("Sentiment: " + analyzer.getSentiment());
    }

    public JLabel[] getLabels(){
        return allLabels;
    }

}
