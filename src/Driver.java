package src;

public class Driver {
    public static void main(String[] args){

        UserGroup group1 = new UserGroup("Group 1");
        group1.addUser(new User("Billy"));
        group1.addUser(new User("Bob"));
        group1.addUser(new User("Howard"));

        UserGroup group2 = new UserGroup("Group 2");
        group2.addUser(new User("Thomas"));

        
        group1.addUser(group2);
        UserGroup group3 = new UserGroup("Group 3");
        group3.addUser(new User("Cowboy"));
        group2.addUser(group3);

        group1.addUser(new User("Tim"));
        group1.addUser(new User("Smith"));

        group2.addUser(new User("wuhfoiewhfj"));

        UserGroup group4 = new UserGroup("Group 4");

        for(int i = 0; i < 10; i++){
            group4.addUser(new User());
        }
        

        System.out.println(group1.getID());
    }
}
