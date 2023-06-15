package src;

import src.AnalysisPackage.Analyzer;
import src.UserPackage.RootGroup;
import src.UserPackage.User;
import src.UserPackage.UserGroup;

public class Driver {
    public static void main(String[] args){
        
        RootGroup root = RootGroup.getInstance();
        User john = new User("john");
        User steve = new User("steve");
        root.addUser(john);
        root.addUser(new User("bob"));
        root.addUser(steve);

        UserGroup cs356 = new UserGroup("CS356");
        UserGroup cs3561 = new UserGroup("CS356Session01");

        cs356.addUser(new User("stu1"));
        cs356.addUser(new User("stu2"));
        cs356.addUser(new User("stu3"));

        cs3561.addUser(new User("stu8"));
        cs3561.addUser(new User("stu9"));
        cs3561.addUser(new User("stu10"));

        cs356.addUser(cs3561);

        cs356.addUser(new User("stu4"));

        root.addUser(cs356);

        root.addUser(new User("oostu"));
        root.addUser(new User("ppstu2"));

        System.out.println(root.getFormattedID());
        
        john.followUser("steve");
        steve.followUser("john");
        System.out.println(john.getFollowers());
        System.out.println(john.getFollowing());
        System.out.println(steve.getFollowers());
        System.out.println(steve.getFollowing());
        
        System.out.println(john.getNewsFeed());
        System.out.println(steve.getNewsFeed());
        john.post("I love you!");
        steve.post("Great!");
        System.out.println(john.getNewsFeed());
        System.out.println(steve.getNewsFeed());
        
        
        Analyzer an = new Analyzer();
        root.accept(an);
        System.out.println(an.getUserCount());
        System.out.println(an.getUserGroupCount());
        System.out.println(an.getNewsFeedCount());
        System.out.println(an.getSentiment());
    
    }
}
