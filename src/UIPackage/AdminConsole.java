package src.UIPackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Utilities;

import src.AnalysisPackage.Analyzer;
import src.UserPackage.IDValidator;
import src.UserPackage.RootGroup;
import src.UserPackage.User;
import src.UserPackage.UserFactory;
import src.UserPackage.UserGroup;
import src.UserPackage.ManagerPackage.UserGroupManager;
import src.UserPackage.ManagerPackage.UserManager;

/**
 * The AdminConsole sets up the main screen Users will be greeted to
 *
 * We are able to add Users and Groups to the Tree View, execute Analysis methods,
 * and enter the User View for selected Users.
 *
 * @author George Matta
 * @version 1.0
 */
public class AdminConsole extends TwitterFrame{

    /**
     * The single instance of this Singleton IDValidator
     */
    private static AdminConsole instance = null;

    /**
     * The RootGroup that holds all other UserInterfaces
     */
    private RootGroup root;

    /**
     * The Analyzer we use to calculate statistics
     */
    private Analyzer analyzer;

    /**
     * The text pane displaying the Tree View
     */
    private JTextPane treeViewTextPane;

    /**
     * The UserGroup last selected (so we don't have to keep selecting a group to
     * add users to it)
     */
    private UserGroup lastSelected;

    /**
     * Retrieves the single instance created of the AdminConsole
     *
     * @return The instance of the AdminConsole
     */
    public static AdminConsole getInstance(){
        if (instance == null){
            instance = new AdminConsole();
        }

        return instance;
    }

    /**
     * A private constructor since this class is a Singleton
     */
    private AdminConsole(){
        super("Mini-Twitter Admin Console");

        // Get the Root Group instance
        root = RootGroup.getInstance();
        lastSelected = root;

        // Set up the analyzer
        analyzer = new Analyzer();

        // Set up the main container to hold all the panels
        JPanel adminContainer = getAdminContainer();
        adminContainer.add(getTreeViewPane());
        adminContainer.add(getUserManagementPanel());
        adminContainer.add(getAnalysisPanel());

        // Add the container to the frame and display to the screen
        this.add(adminContainer);
        this.setSize(this.SCREEN_WIDTH, this.SCREEN_HEIGHT);
        this.setVisible(true);
    }

    /**
     * Retrieves the Admin container that should hold all the panels.
     *
     * We simply set the background color, set the layout (box),
     * and set the border (empty border)
     *
     * @return The JPanel of the AdminContainer
     */
    private JPanel getAdminContainer(){
        JPanel adminContainer = new JPanel();
        adminContainer.setBackground(this.BACKGROUND_COLOR);
        adminContainer.setLayout(new BoxLayout(adminContainer, BoxLayout.Y_AXIS));
        adminContainer.setBorder(new EmptyBorder(15, 15, 15, 15));

        return adminContainer;
    }

    /**
     * Retrieves the Pane holding the TreeView text
     *
     * @return The JScrollPane holding the text
     */
    private JScrollPane getTreeViewPane(){
        // Set up the text
        this.treeViewTextPane = new JTextPane();

        // We will format the text as html, and set the text
        treeViewTextPane.setContentType("text/html");
        treeViewTextPane.setEditable(false);
        treeViewTextPane.setBackground(this.BACKGROUND_COLOR);
        setTreeViewText();

        // We want to be able to select users from the tree view
        // We do this with a mouse listener - if the user clicks, we reset the lastSelected
        // so it can be set later by caret position
        treeViewTextPane.addMouseListener(
            new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e){
                    lastSelected = null;
                }

                @Override
                public void mousePressed(MouseEvent e){}
                @Override
                public void mouseReleased(MouseEvent e){}
                @Override
                public void mouseEntered(MouseEvent e){}
                @Override
                public void mouseExited(MouseEvent e){}
            }
        );

        // Set a panel with a border layout to prevent Wrapping in the text pane
        JPanel noWrapPanel = new JPanel(new BorderLayout());
        noWrapPanel.add(treeViewTextPane);

        // Add this noWrapPanel to a Scroll pane to allow us to scroll through the
        // text if it gets too long
        JScrollPane treeViewPane = new JScrollPane(noWrapPanel);
        treeViewPane.setBackground(this.BACKGROUND_COLOR);
        treeViewPane.setBorder(new CompoundBorder(
                                    BorderFactory.createEmptyBorder(
                                        0, 0, 5, 0
                                        ),
                                    this.createTitledBorder("Tree View", Color.BLUE)
                                )
                            );
        treeViewPane.setPreferredSize(this.BIG_PANEL_DIMENSION);

        // Return the final treeViewPane
        return treeViewPane;
    }

    /**
     * Sets the treeViewText to the formatted String of the RootGroup's formatted ID.
     *
     * We take this String and translate it to HTML:
     * - We replace ** with a starting bold tag
     * - We replace *** with an ending bold tag
     * - We replace new lines with a line break
     * - We replace indentation with an EM space
     *
     * We also define the font to use
     */
    private void setTreeViewText(){
        if(this.treeViewTextPane == null){
            return;
        }

        this.treeViewTextPane.setText(
            "<font size = +1 face = \"" + this.DEFAULT_FONT_NAME + "\">" +
            root.getFormattedID().replaceAll("\\*\\*\\*", "</b>")
                                .replaceAll("\\*\\*", "<b>")
                                .replaceAll("\n", "<br>")
                                .replaceAll("    ", "&emsp;") +
            "</font>"
        );
    }

    /**
     * Retrieves the UserManagementPanel which has buttons to add Users or Groups
     * and enter UserViews
     *
     * @return The UserManagement JPanel
     */
    private JPanel getUserManagementPanel(){
        // The text field of the requested ID
        JTextField idTextField = new JTextField();
        idTextField.setFont(new Font(this.DEFAULT_FONT_NAME, 0, 15));

        // The final panel to return
        JPanel userManagementPanel = new JPanel();
        userManagementPanel.setBackground(this.BACKGROUND_COLOR);
        userManagementPanel.setBorder(new CompoundBorder(
                                        BorderFactory.createEmptyBorder(
                                            0, 0, 5, 0
                                        ),
                                        this.createTitledBorder("User Management", Color.RED)
                                    )
                                );
        userManagementPanel.setLayout(new GridLayout(2, 3, 5, 5));

        // The button to verify the User IDs
        JButton userVerificationButton = new JButton("Verify Users");
        userVerificationButton.addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    String displayMessage = "All the User and Group IDs are valid!";
                    if (!verifyUsers()){
                        displayMessage = "One or more Users or Groups have invalid IDs!";
                    }

                    showMessageDialog(displayMessage);
                }
            }
        );
        // Add the button to grid spot 0,0
        userManagementPanel.add(userVerificationButton);

        // The button for entering the UserView
        JButton userViewButton = new JButton("User View");
        userViewButton.addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    // Retrieve the selected user and initialize a UserView based off
                    // of it
                    User selectedUser = getSelectedUser();
                    if (selectedUser == null) return;

                    UserView uv = new UserView(selectedUser);
                    uv.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
                    uv.setVisible(true);
                }
            }
        );
        // Add the button to grid spot 0,1
        userManagementPanel.add(userViewButton);

        // The button for getting the latest updated user
        JButton getLastUpdatedButton = new JButton("Last Updated");
        getLastUpdatedButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    showMessageDialog("Last Updated User: " + findLastUpdatedUser());
                }
            }
        );
        // Add the button to grid spot 0,2
        userManagementPanel.add(getLastUpdatedButton);

        // The button for adding a User
        JButton addUserButton = new JButton("Add User");
        addUserButton.addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    // Retrieve the selected UserGroup
                    UserGroup selectedGroup = getSelectedGroup();
                    if (selectedGroup == null) return;

                    // Retrieve the ID typed by the user
                    String typedID = getTypedText(idTextField);
                    if  (typedID.length() == 0) return;

                    try{
                        // Try adding the created User to the selected group
                        selectedGroup.addUser(UserFactory.createUser(typedID));
                    } catch (IllegalArgumentException ex) { return; }

                    // Update the tree view's text to reflect changes
                    setTreeViewText();
                }
            }
        );
        // Add the user button to grid spot 1,0
        userManagementPanel.add(addUserButton);

        // Add the ID text field to grid spot 1,1
        userManagementPanel.add(idTextField);

        // The button for adding a Group
        JButton addUserGroupButton = new JButton("Add User Group");
        addUserGroupButton.addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    // Retrieve the selected UserGroup
                    UserGroup selectedGroup = getSelectedGroup();
                    if (selectedGroup == null) return;

                    // Retrieve the ID typed by the user
                    String typedID = getTypedText(idTextField);
                    if  (typedID.length() == 0) return;

                    try{
                        // Try adding the created user to the selected group
                        UserGroup createdUser = UserFactory.createUserGroup(typedID);
                        selectedGroup.addUser(createdUser);
                        lastSelected = createdUser;
                    } catch (IllegalArgumentException ex) { return; }

                    // Update the tree view's text to reflect changes
                    setTreeViewText();
                }
            }
        );
        // Add the user group button to grid spot 1,2
        userManagementPanel.add(addUserGroupButton);

        // Return the final user management panel
        userManagementPanel.setPreferredSize(this.SMALL_PANEL_DIMENSION);
        return userManagementPanel;
    }

    /**
     * Verifies the IDs of all the Users and Groups in the System.
     *
     * Uses the IDValidator to check if there are any IDs with a space character
     *
     * The IDValidator ensures that IDs are unique
     *
     * @return True if all the Users and Groups in the system are valid
     */
    private boolean verifyUsers(){
        IDValidator idValidator = IDValidator.getInstance();

        String currID = "";
        // Check User ID validity
        for(User user : UserManager.getInstance().getUserItemSet()){
            currID = user.getID();
            if (idValidator.containsSpace(currID)) { return false; }
        }

        // Check Group ID validity
        for(UserGroup userGroup : UserGroupManager.getInstance().getUserItemSet()){
            currID = userGroup.getID();
            if (idValidator.containsSpace(currID)) { return false; }
        }

        // Everything is valid
        return true;
    }

    private String findLastUpdatedUser(){
        long latestTime = 0;
        User latestUpdatedUser = null;
        for(User user : UserManager.getInstance().getUserItemSet()){
            if (user.getUpdatedTime() > latestTime){
                latestUpdatedUser = user;
                latestTime = user.getUpdatedTime();
            }
        }

        if (latestUpdatedUser == null) { return "No Users Found!"; }

        return latestUpdatedUser.getID();
    }

    /**
     * Retrieves the selected group based on the selected ID
     *
     * If lastSelected is populated, we just return that.
     *
     * @return The selected UserGroup
     */
    private UserGroup getSelectedGroup(){
        // The last selected group if one exists
        if (lastSelected != null){
            return lastSelected;
        }

        // The ID to search for
        String selectedID = getSelectedID();
        try{
            // Try finding a UserGroup with that ID and set it to be the latest selected
            lastSelected = UserGroupManager.getInstance()
                                    .findItem(selectedID);
            return lastSelected;
        } catch (IllegalArgumentException ex){ return root; }
        // if something goes wrong, return the root user
    }

    /**
     * Retrieves the selected user based on the selected ID
     *
     * @return The selected UserGroup
     */
    private User getSelectedUser(){
        // The ID to search for
        String selectedID = getSelectedID();
        try{
            // Try finding a User with that ID
            return UserManager.getInstance()
                                    .findItem(selectedID);
        } catch (IllegalArgumentException ex){ return null; }
    }

    /**
     * Retrieves the ID selected from the tree view text based on the caret position
     *
     * @return The selected ID
     */
    private String getSelectedID(){
        // The selected Row based on the caret position
        int rowNum = getSelectedRow();

        // The root user's formatted ID split into each individual item
        String[] lines = root.getFormattedID().split("\n\s+- ");

        // Iterate through the lines and find the selected ID
        if (lines.length != 0 && rowNum >= 0 && rowNum < lines.length){
            return lines[rowNum].replaceAll("\\*", "");
        }

        // If nothing is found, return the root group
        return root.getID();
    }

    /**
     * Retrieves the selected row in the tree view text based on the caret position
     *
     * @return The selected row index
     */
    public int getSelectedRow(){
        // Initialize the row number and caret position
        int rowNum = 0;
        int caretPosition = treeViewTextPane.getCaretPosition();

        try {
            for (int offset = caretPosition; offset > 0;) {
                // Count rows until we reach the selected text
                offset = Utilities.getRowStart(treeViewTextPane, offset) - 1;
                rowNum++;
            }
        } catch (BadLocationException ex){
            ex.printStackTrace();
        }
        // Indexing stats at 0
        rowNum--;

        // Return the found index
        return rowNum;
    }

    /**
     * Retrieves the AnalysisPanel which has buttons to count statistics
     *
     * @return The Analysis JPanel
     */
    private JPanel getAnalysisPanel(){

        // Set up the panel
        JPanel analysisPanel = new JPanel();
        analysisPanel.setBorder(this.createTitledBorder("Analysis", Color.BLACK));
        analysisPanel.setBackground(this.BACKGROUND_COLOR);
        analysisPanel.setPreferredSize(this.SMALL_PANEL_DIMENSION);
        analysisPanel.setLayout(new GridLayout(2, 2, 5, 5));

        // Set up buttons for each statistic
        JButton updateUserCount = new JButton("Calculate User Count");
        JButton updateGroupCount = new JButton("Calculate Group Count");
        JButton updateMessageCount = new JButton("Calculate Message Count");
        JButton updateSentiment = new JButton("Calculate Sentiment Value");

        // Display a message of the user count if the count users button is clicked
        updateUserCount.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    prepareAnalyzer();
                    showMessageDialog("User Count: " + analyzer.getUserCount());
                }
            }
        );

        // Display a message of the group count if the count groups button is clicked
        updateGroupCount.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    prepareAnalyzer();
                    showMessageDialog("Group Count: " + analyzer.getUserGroupCount());
                }
            }
        );

        // Display a message of the message count if the count messages button is clicked
        updateMessageCount.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    prepareAnalyzer();
                    showMessageDialog("Message Count: " + analyzer.getNewsFeedCount());
                }
            }
        );

        // Display a message of the sentiment value if the calculate sentiment button is clicked
        updateSentiment.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    prepareAnalyzer();
                    showMessageDialog("Sentiment Value: " + analyzer.getSentiment() + "%");
                }
            }
        );

        // Add all the buttons to the analysis panel
        analysisPanel.add(updateUserCount);
        analysisPanel.add(updateGroupCount);
        analysisPanel.add(updateMessageCount);
        analysisPanel.add(updateSentiment);

        // Return the panel
        return analysisPanel;
    }

    /**
     * Prepares the analyzer by resetting the counts and passing the analyzer to the
     * root group
     */
    private void prepareAnalyzer(){
        analyzer.resetCounts();
        root.accept(analyzer);
    }

    /**
     * Shows a popup message dialog of a given message
     *
     * @param message The message to display
     */
    private void showMessageDialog(String message){
        JOptionPane.showMessageDialog(this, message);
    }

}
