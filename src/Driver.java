package src;

public class Driver {
    public static void main(String[] args){
        
        RootGroup root = RootGroup.getInstance();
        User john = new User("john");
        root.addUser(john);
        root.addUser(new User("bob"));
        root.addUser(new User("steve"));

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
    }
}
