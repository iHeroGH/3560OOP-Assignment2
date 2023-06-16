package src.UIPackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.StyleConstants;
import javax.swing.text.Utilities;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.parser.*;

import src.UserPackage.RootGroup;

public class AdminConsole {
    
    JFrame adminFrame;

    /**
     * The single instance of this Singleton IDValidator
     */
    public static AdminConsole instance = null;
    
    private RootGroup root;

    private JTextPane treeViewTextPane;

    public final static int WIDTH = 481;
    public final static int HEIGHT = 786;
    public final static String DEFAULT_FONT_NAME = "Verdana";
    public final static Color BACKGROUND_COLOR = Color.WHITE;
    public final static Dimension PANEL_DIMENSION = new Dimension(WIDTH, HEIGHT/15);

    /**
     * Retrieves the single instance created of the AdminConsole
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
        adminFrame = new JFrame("Mini-Twitter Admin Console");
        root = RootGroup.getInstance();
        
        JPanel adminContainer = getAdminContainer();
        adminContainer.add(getTreeViewPane());
        adminContainer.add(getUserManagementPanel());
        adminContainer.add(getAnalysisPanel());
        
        adminFrame.add(adminContainer);
        adminFrame.setSize(WIDTH, HEIGHT);
        adminFrame.setVisible(true);
    }

    private JPanel getAdminContainer(){
        JPanel adminContainer = new JPanel();
        adminContainer.setBackground(BACKGROUND_COLOR);
        adminContainer.setLayout(new BoxLayout(adminContainer, BoxLayout.Y_AXIS));
        adminContainer.setBorder(new EmptyBorder(15, 15, 15, 15));

        return adminContainer;
    }

    private JScrollPane getTreeViewPane(){
        JTextPane treeViewTextPane = new JTextPane();
        treeViewTextPane.setContentType("text/html");
        treeViewTextPane.setEditable(false);
        treeViewTextPane.setText(
            "<font size = +1 face = \"" + DEFAULT_FONT_NAME + "\">" + 
            root.getFormattedID().replaceAll("\\*\\*\\*", "</b>")
                                .replaceAll("\\*\\*", "<b>")
                                .replaceAll("\n", "<br>")
                                .replaceAll("    ", "&emsp;&emsp;") + 
            "</font>"
        );
        treeViewTextPane.setBackground(BACKGROUND_COLOR);

        this.treeViewTextPane = treeViewTextPane;

        JScrollPane treeViewPane = new JScrollPane(treeViewTextPane);
        treeViewPane.setBackground(BACKGROUND_COLOR);
        treeViewPane.setBorder(new CompoundBorder(
                                    BorderFactory.createEmptyBorder(
                                        0, 0, 5, 0
                                        ),
                                    createTitledBorder("Tree View", Color.BLUE)
                                )
                            );
        treeViewPane.setPreferredSize(new Dimension(WIDTH, 55*HEIGHT/100));

        return treeViewPane;
    }

    private JPanel getUserManagementPanel(){
        JPanel userManagementPanel = new JPanel();
        userManagementPanel.setBackground(BACKGROUND_COLOR);
        userManagementPanel.setBorder(new CompoundBorder(
                                        BorderFactory.createEmptyBorder(
                                            0, 0, 5, 0
                                        ),
                                        createTitledBorder("User Management", Color.RED)
                                    )
                                );
        
        userManagementPanel.setLayout(new GridLayout(2, 3, 5, 5));        

        userManagementPanel.add(new JLabel());
        
        JButton userViewButton = new JButton("User View");
        userViewButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    int caretPosition = treeViewTextPane.getCaretPosition();
                    int rowNum = 0;
                    try {
                        for (int offset = caretPosition; offset > 0;) {
                            offset = Utilities.getRowStart(treeViewTextPane, offset) - 1;
                            rowNum++;
                        }
                    } catch (BadLocationException ex){
                        ex.printStackTrace();
                    }
                    rowNum--;

                    String[] lines = root.getFormattedID().split("\n\s+- ");

                    if (lines.length != 0 && rowNum != 0 && rowNum < lines.length){
                        System.out.println(lines[rowNum]);
                    }
                }
            }
        );
        userManagementPanel.add(userViewButton);

        userManagementPanel.add(new JLabel());

        JButton addUserButton = new JButton("Add User");
        userManagementPanel.add(addUserButton);
        
        JTextField idTextField = new JTextField();
        idTextField.setFont(new Font(DEFAULT_FONT_NAME, 0, 15));
        userManagementPanel.add(idTextField);

        JButton addUserGroupButton = new JButton("Add User Group");
        userManagementPanel.add(addUserGroupButton);


        userManagementPanel.setPreferredSize(PANEL_DIMENSION);
        return userManagementPanel;
    }

    private JPanel getAnalysisPanel(){
        JPanel analysisPanel = new JPanel();
        analysisPanel.setBorder(createTitledBorder("Analysis", Color.BLACK));
        analysisPanel.setBackground(BACKGROUND_COLOR);
        analysisPanel.setPreferredSize(PANEL_DIMENSION);

        return analysisPanel;
    }

    private static TitledBorder createTitledBorder(String title, Color color){
        return BorderFactory.createTitledBorder(
                                    BorderFactory.createLineBorder(color, 2), 
                                    title, 0, 0,
                                    new Font("Verdana", 0, 13)
                                );
    }

}
