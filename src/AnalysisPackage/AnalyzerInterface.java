package src.AnalysisPackage;

import java.util.List;

import src.UserPackage.User;
import src.UserPackage.UserGroup;

public interface AnalyzerInterface {
    
    public void visitUser(User user);
    public void visitUserGroup(UserGroup userGroup);
    public void visitNewsFeed(List<String> newsFeed);

}
