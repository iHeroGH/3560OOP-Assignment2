package src.UIPackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import src.UserPackage.User;
import src.UserPackage.UserInterface;
import src.UserPackage.ObserverPackage.FollowerInterface;
import src.UserPackage.ObserverPackage.PosterInterface;

/**
 * The UserView sets up buttons for following other users and displaying followers
 * and Users followed by this user. The User is also able to post messages in the
 * User View and can see messages posted by people they follow
 *
 * @author George Matta
 * @version 1.0
 */
public class UserView extends TwitterFrame{

    /**
     * The User we are viewing
     */
    private User user;

    /**
     * The text pane displaying the posts
     */
    private JTextPane postsText;

    /**
     * The text pane displaying the followers
     */
    private JTextPane followersText;

    /**
     * The text pane displaying Users followed by this user
     */
    private JTextPane followingText;

    /**
     * Taking a user, we create a UserView
     *
     * @param user The User to view
     */
    public UserView(User user){
        super("User View: " + user.getID() + " - Created: " + user.getCreationTime());
        this.user = user;

        // Add the user view to the manager
        UserViewManager.getInstance().addItem(this);

        // Initialize the text panes
        postsText = new JTextPane();
        followersText = new JTextPane();
        followingText = new JTextPane();

        // Set them all to use HTML and be non-editable
        followersText.setContentType("text/html");
        followersText.setEditable(false);
        followingText.setContentType("text/html");
        followingText.setEditable(false);
        postsText.setContentType("text/html");
        postsText.setEditable(false);

        /**
         * A main container to hold everything
         */
        JPanel userViewContainer = getUserViewContainer();
        userViewContainer.add(getFollowButtons());
        userViewContainer.add(getFollowView());
        userViewContainer.add(getPostButtons());
        userViewContainer.add(getPostView());

        this.add(userViewContainer);

        // The Frame should be made viewable and resized in Admin console
    }

    /**
     * Retrieves the User being viewed by this UserView
     *
     * @return The User being viewed
    */
    public User getUser(){
        return user;
    }

    /**
     * Retrieves the UserView container that should hold all the panels.
     *
     * We simply set the background color, set the layout (box),
     * and set the border (empty border)
     *
     * @return The UserView JPanel
     */
    private JPanel getUserViewContainer(){
        JPanel userViewContainer = new JPanel();
        userViewContainer.setBackground(this.BACKGROUND_COLOR);
        userViewContainer.setLayout(new BoxLayout(userViewContainer, BoxLayout.Y_AXIS));
        userViewContainer.setBorder(new EmptyBorder(15, 15, 15, 15));

        return userViewContainer;
    }

    /**
     * Set up the panel holding a text entry for the User's ID to follow and
     * a button to follow them
     *
     * @return The created JPanel
     */
    private JPanel getFollowButtons(){
        // Create the final JPanel
        JPanel followButtonsPanel = new JPanel();
        followButtonsPanel.setBackground(this.BACKGROUND_COLOR);
        followButtonsPanel.setLayout(new GridLayout(1, 2, 5, 5));

        // Create the text field for entering the ID
        JTextField idTextField = new JTextField();
        idTextField.setFont(new Font(this.DEFAULT_FONT_NAME, 0, 15));

        // Create the button for following a user
        JButton followUserButton = new JButton("Follow User");
        followUserButton.addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    // Retrieve the typed ID
                    String typedID = getTypedText(idTextField);
                    if  (typedID.length() == 0) return;

                    try{
                        // Try to follow the user
                        user.followUser(typedID);
                        updateFollowing();

                        // Update the follow text for all Users involved
                        for(UserView userView : UserViewManager.getInstance().getUserItemSet()){
                            if (UserViewManager.getInstance().compareItems(userView, typedID)){
                                userView.updateFollowers();
                            }
                        }

                    } catch (IllegalArgumentException ex) { return; }

                }
            }
        );

        // Add the text and button to the panel
        followButtonsPanel.add(idTextField);
        followButtonsPanel.add(followUserButton);

        // Resize the panel and return it
        followButtonsPanel.setPreferredSize(this.SMALL_PANEL_DIMENSION);
        return followButtonsPanel;
    }

    /**
     * Retrieves the JPanel holding the list of Followers and Following
     *
     * @return The FollowView JPanel
     */
    private JPanel getFollowView(){
        // The final panel to return
        JPanel followManagerPanel = new JPanel(new GridLayout(1, 2, 5, 5));

        // Set the followers and following text
        updateFollowers();
        updateFollowing();

        // Create a noWrap panel for the followers and following
        JPanel followersNoWrapPanel = new JPanel(new BorderLayout());
        followersNoWrapPanel.add(followersText);

        JPanel followingNoWrapPanel = new JPanel(new BorderLayout());
        followingNoWrapPanel.add(followingText);

        // Create a scrollable pane out of the nowrap panel for both
        JScrollPane followersViewPane = new JScrollPane(followersNoWrapPanel);
        followersViewPane.setBorder(new CompoundBorder(
                                    BorderFactory.createEmptyBorder(
                                        5, 5, 5, 0
                                        ),
                                    BorderFactory.createMatteBorder(
                                        0, 0, 0, 1, Color.RED
                                    )
                                )
                            );
        followersViewPane.setBackground(this.BACKGROUND_COLOR);

        JScrollPane followingViewPane = new JScrollPane(followingNoWrapPanel);
        followingViewPane.setBorder(BorderFactory.createEmptyBorder(
                                        5, 0, 5, 5
                                    )
                                );
        followingViewPane.setBackground(this.BACKGROUND_COLOR);

        // Set the attributes (background color, border, size) for the main panel
        followManagerPanel.setBackground(this.BACKGROUND_COLOR);
        followManagerPanel.setBorder(new CompoundBorder(
                                    BorderFactory.createEmptyBorder(
                                        5, 0, 10, 0
                                        ),
                                    BorderFactory.createLineBorder(Color.RED)
                                )
                            );
        followManagerPanel.setPreferredSize(this.BIG_PANEL_DIMENSION);

        // Add the ScrollPanes to the panel and return
        followManagerPanel.add(followersViewPane);
        followManagerPanel.add(followingViewPane);
        return followManagerPanel;
    }

    /**
     * Retrieves a String of the Users who follow this User
     *
     * @return The String of followers
     */
    private String getFollowers(){
        String followers = "<b>Followers</b><br>";
        for(FollowerInterface follower : user.getFollowers()){
            followers += ((UserInterface) follower).getID() + "<br>";
        }
        return followers;
    }

    /**
     * Retrieves a String of the Users who are followed by this User
     *
     * @return The String of users being followed by this user
     */
    private String getFollowing(){
        String followers = "<b>Following</b><br>";
        for(PosterInterface follower : user.getFollowing()){
            followers += ((UserInterface) follower).getID() + "<br>";
        }
        return followers;
    }

    /**
     * Set up the panel holding a text entry for the message to post and
     * a button to post it
     *
     * @return The created JPanel
     */
    private JPanel getPostButtons(){
        // Create the main panel to return
        JPanel postButtonsPanel = new JPanel();
        postButtonsPanel.setBackground(this.BACKGROUND_COLOR);
        postButtonsPanel.setLayout(new GridLayout(1, 2, 5, 5));

        // Create a text field for the message
        JTextField messageText = new JTextField();
        messageText.setFont(new Font(this.DEFAULT_FONT_NAME, 0, 15));

        // Create a button to post the message
        JButton postMessageButton = new JButton("Post");
        postMessageButton.addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    // Retrieve the typed message
                    String typedText = getTypedText(messageText);
                    if  (typedText.length() == 0) return;

                    try{
                        // Try posting the message and updating the news feeds
                        user.post(typedText);
                        updatePosts();

                        // Update the news feeds for every visible UserView
                        for(UserView userView : UserViewManager.getInstance().getUserItemSet()){
                            if(user.getFollowers().contains(userView.getUser())){
                                userView.updatePosts();
                            }
                        }

                    } catch (IllegalArgumentException ex) { return; }

                }
            }
        );

        // Add the text field and button to the panel
        postButtonsPanel.add(messageText);
        postButtonsPanel.add(postMessageButton);

        // Resize and return the panel
        postButtonsPanel.setPreferredSize(this.SMALL_PANEL_DIMENSION);
        return postButtonsPanel;
    }

    /**
     * Retrieves a JPanel of the viewable newsFeed
     *
     * @return The Posts JPanel
     */
    private JPanel getPostView(){
        // The final JPanel to return
        JPanel postViewPanel = new JPanel(new GridLayout(1, 1, 5, 5));
        postViewPanel.setBackground(BACKGROUND_COLOR);
        postViewPanel.setBorder(createTitledBorder(
                                    "News Feed", Color.BLUE
                                    )
                                );
        postViewPanel.setPreferredSize(this.BIG_PANEL_DIMENSION);

        // Update the post text
        updatePosts();

        // Set a scrollable pane for the post text
        JScrollPane postsViewPane = new JScrollPane(postsText);
        postsViewPane.setBackground(this.BACKGROUND_COLOR);
        postsViewPane.setBorder(null);

        // Add the TextPane to the Panel
        postViewPanel.add(postsViewPane);

        // Return the panel
        return postViewPanel;
    }

    /**
     * A getter method to retrieve all the viewable posts of a user
     *
     * This is formatted with HTML (break lines for new lines)
     *
     * @return The formatted String of all the viewable posts for this User
     */
    private String getPosts(){
        String posts = "";
        // Add each post separated by a break line since this is HTML
        for(String post : user.getNewsFeed()){
            posts = post + "<br>" + posts;
        }

        return posts;
    }

    /**
     * Updates the Followers text with the User's followers
     */
    private void updateFollowers(){
        setUpdateableText(this.followersText, getFollowers());
    }

    /**
     * Updates the Followers text with the the list of Users followed by this User
     */
    private void updateFollowing(){
        setUpdateableText(this.followingText, getFollowing());
    }

    /**
     * Updates the Post text with the User's news feed
     */
    private void updatePosts(){
        setUpdateableText(this.postsText, getPosts());
    }

    /**
     * Sets the text for a given text pane, formatted with HTMl and using
     * the default font
     *
     * @param textPane THe TextPane to edit
     * @param newText The new text to set
     */
    private void setUpdateableText(JTextPane textPane, String newText){
        textPane.setText(
                    "<font face = \"" + this.DEFAULT_FONT_NAME + "\">" +
                    newText +
                    "</font>"
                );
    }
}
