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
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import src.UserPackage.User;
import src.UserPackage.ObserverPackage.FollowerInterface;

public class UserView extends JFrame{
    
    private User user;
    private JTextPane postsText;
    private JTextPane followersText;
    private JTextPane followingText;

    public UserView(User user){
        super("User View: " + user.getID());
        this.user = user;

        UserViewManager.getInstance().addItem(this);

        postsText = new JTextPane();
        followersText = new JTextPane();
        followingText = new JTextPane();

        followersText.setContentType("text/html");
        followersText.setEditable(false);
        followingText.setContentType("text/html");
        followingText.setEditable(false);
        postsText.setContentType("text/html");
        postsText.setEditable(false);

        JPanel userViewContainer = getAdminContainer();
        userViewContainer.add(getFollowButtons());
        userViewContainer.add(getFollowView());
        userViewContainer.add(getPostButtons());
        userViewContainer.add(getPostView());
        
        this.add(userViewContainer);
        this.setSize(UIConstants.SCREEN_WIDTH, UIConstants.SCREEN_HEIGHT);
        this.setVisible(true);
    }

    public User getUser(){
        return user;
    }

    private JPanel getAdminContainer(){
        JPanel userViewContainer = new JPanel();
        userViewContainer.setBackground(UIConstants.BACKGROUND_COLOR);
        userViewContainer.setLayout(new BoxLayout(userViewContainer, BoxLayout.Y_AXIS));
        userViewContainer.setBorder(new EmptyBorder(15, 15, 15, 15));

        return userViewContainer;
    }

    private JPanel getFollowButtons(){
        JPanel followButtonsPanel = new JPanel();
        followButtonsPanel.setBackground(UIConstants.BACKGROUND_COLOR);
        followButtonsPanel.setLayout(new GridLayout(1, 2, 5, 5));

        JTextField idTextField = new JTextField();
        idTextField.setFont(new Font(UIConstants.DEFAULT_FONT_NAME, 0, 15));
        JButton followUserButton = new JButton("Follow User");
        followUserButton.addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    String typedID = getTypedText(idTextField);
                    if  (typedID.length() == 0) return;
                    
                    try{
                        user.followUser(typedID);
                        updateFollowing();
                        
                        for(UserView userView : UserViewManager.getInstance().getUserItemSet()){
                            if (UserViewManager.getInstance().compareItems(userView, typedID)){
                                userView.updateFollowers();
                            }
                        }

                    } catch (IllegalArgumentException ex) { return; }
                    
                }
            }
        );

        followButtonsPanel.add(idTextField);
        followButtonsPanel.add(followUserButton);

        followButtonsPanel.setPreferredSize(UIConstants.SMALL_PANEL_DIMENSION);

        return followButtonsPanel;
    }

    private JPanel getFollowView(){
        JPanel followManagerPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        
        updateFollowers();
        updateFollowing();
        
        JPanel followersNoWrapPanel = new JPanel(new BorderLayout());
        followersNoWrapPanel.add(followersText);

        JPanel followingNoWrapPanel = new JPanel(new BorderLayout());
        followingNoWrapPanel.add(followingText);
        
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
        followersViewPane.setBackground(UIConstants.BACKGROUND_COLOR);
        
        JScrollPane followingViewPane = new JScrollPane(followingNoWrapPanel);
        followingViewPane.setBorder(BorderFactory.createEmptyBorder(
                                        5, 0, 5, 5
                                    )
                                );
        followingViewPane.setBackground(UIConstants.BACKGROUND_COLOR);

        followManagerPanel.setBackground(UIConstants.BACKGROUND_COLOR);
        followManagerPanel.setBorder(new CompoundBorder(
                                    BorderFactory.createEmptyBorder(
                                        5, 0, 10, 0
                                        ),
                                    BorderFactory.createLineBorder(Color.RED)
                                )
                            );
        followManagerPanel.setPreferredSize(UIConstants.BIG_PANEL_DIMENSION);

        followManagerPanel.add(followersViewPane);
        followManagerPanel.add(followingViewPane);

        return followManagerPanel;
    }

    private String getFollowers(){
        String followers = "<b>Followers</b><br>";
        for(FollowerInterface follower : user.getFollowers()){
            followers += follower.getID() + "<br>";
        }
        return followers;
    }

    private String getFollowing(){
        String followers = "<b>Following</b><br>";
        for(FollowerInterface follower : user.getFollowing()){
            followers += follower.getID() + "<br>";
        }
        return followers;
    }

    private JPanel getPostButtons(){
        JPanel postButtonsPanel = new JPanel();
        postButtonsPanel.setBackground(UIConstants.BACKGROUND_COLOR);
        postButtonsPanel.setLayout(new GridLayout(1, 2, 5, 5));

        JTextField messageText = new JTextField();
        messageText.setFont(new Font(UIConstants.DEFAULT_FONT_NAME, 0, 15));
        JButton postMessageButton = new JButton("Post");

        postMessageButton.addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    String typedText = getTypedText(messageText);
                    if  (typedText.length() == 0) return;
                    
                    try{
                        user.post(typedText);
                        updatePosts();
                        
                        for(UserView userView : UserViewManager.getInstance().getUserItemSet()){
                            if(user.getFollowers().contains(userView.getUser())){
                                userView.updatePosts();
                            }
                        }

                    } catch (IllegalArgumentException ex) { return; }
                    
                }
            }
        );

        postButtonsPanel.add(messageText);
        postButtonsPanel.add(postMessageButton);

        postButtonsPanel.setPreferredSize(UIConstants.SMALL_PANEL_DIMENSION);

        return postButtonsPanel;
    }

    private JPanel getPostView(){
        JPanel postViewPanel = new JPanel(new GridLayout(1, 1, 5, 5));
        postViewPanel.setBackground(UIConstants.BACKGROUND_COLOR);
        postViewPanel.setBorder(UIConstants.createTitledBorder(
                                    "News Feed", Color.BLUE
                                    )
                                );
        postViewPanel.setPreferredSize(UIConstants.BIG_PANEL_DIMENSION);

        updatePosts();

        JScrollPane postsViewPane = new JScrollPane(postsText);
        postsViewPane.setBackground(UIConstants.BACKGROUND_COLOR);
        postsViewPane.setBorder(null);

        postViewPanel.add(postsViewPane);

        return postViewPanel;
    }

    private String getPosts(){
        String posts = "";
        for(String post : user.getNewsFeed()){
            posts = post + "<br>" + posts;
        }

        return posts;
    }

    private void updateFollowers(){
        setUpdateableText(this.followersText, getFollowers());
    }

    private void updateFollowing(){
        setUpdateableText(this.followingText, getFollowing());
    }

    private void updatePosts(){
        setUpdateableText(this.postsText, getPosts());
    }

    private static void setUpdateableText(JTextPane textPane, String newText){
        textPane.setText(
                    "<font face = \"" + UIConstants.DEFAULT_FONT_NAME + "\">" + 
                    newText + 
                    "</font>"
                );
    }

    public static String getTypedText(JTextField idTextField){
        String typedID = idTextField.getText();
        idTextField.setText("");

        return typedID;
    }
}
