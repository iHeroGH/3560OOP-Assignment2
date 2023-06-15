package src;

import java.util.List;

public interface AnalyzerInterface {
    
    public void visitUser(User user);
    public void visitUserGroup(UserGroup userGroup);
    public void visitNewsFeed(List<String> newsFeed);

}
