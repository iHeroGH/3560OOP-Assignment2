package src.UIPackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

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

    public UserView(User user){
        super("User View: " + user.getID());
        this.user = user;

        JPanel userViewContainer = getAdminContainer();
        userViewContainer.add(getFollowButtons());
        userViewContainer.add(getFollowView());
        userViewContainer.add(getPostButtons());
        userViewContainer.add(getPostView());
        
        this.add(userViewContainer);
        this.setSize(UIConstants.SCREEN_WIDTH, UIConstants.SCREEN_HEIGHT);
        this.setVisible(true);
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

        followButtonsPanel.add(idTextField);
        followButtonsPanel.add(followUserButton);

        followButtonsPanel.setPreferredSize(UIConstants.SMALL_PANEL_DIMENSION);

        return followButtonsPanel;
    }

    private JPanel getFollowView(){
        JPanel followManagerPanel = new JPanel(new GridLayout(1, 2, 5, 5));

        JTextPane followers = new JTextPane();
        JTextPane following = new JTextPane();
        
        followers.setContentType("text/html");
        followers.setEditable(false);

        following.setContentType("text/html");
        following.setEditable(false);
        
        followers.setText(
            "<font face = \"" + UIConstants.DEFAULT_FONT_NAME + "\">" + 
            getFollowers() + 
            "</font>"
        );

        following.setText(
            "<font face = \"" + UIConstants.DEFAULT_FONT_NAME + "\">" + 
            getFollowing() + 
            "</font>"
        );
        
        JPanel followersNoWrapPanel = new JPanel(new BorderLayout());
        followersNoWrapPanel.add(followers);

        JPanel followingNoWrapPanel = new JPanel(new BorderLayout());
        followingNoWrapPanel.add(following);
        
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

        JTextField message = new JTextField();
        message.setFont(new Font(UIConstants.DEFAULT_FONT_NAME, 0, 15));
        JButton postMessageButton = new JButton("Post");

        postButtonsPanel.add(message);
        postButtonsPanel.add(postMessageButton);

        postButtonsPanel.setPreferredSize(UIConstants.SMALL_PANEL_DIMENSION);

        return postButtonsPanel;
    }

    private JPanel getPostView(){
        JPanel postViewPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        postViewPanel.setBackground(UIConstants.BACKGROUND_COLOR);
        postViewPanel.setBorder(UIConstants.createTitledBorder(
                                    "News Feed", Color.BLUE
                                    )
                                );
        postViewPanel.setPreferredSize(UIConstants.BIG_PANEL_DIMENSION);

        JTextPane postsText = new JTextPane();
        postsText.setContentType("text/html");
        postsText.setEditable(false);
        postsText.setText(
                    "<font face = \"" + UIConstants.DEFAULT_FONT_NAME + "\">" + 
                    getPosts() + 
                    "</font>"
                );

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

}
