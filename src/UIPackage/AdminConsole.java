package src.UIPackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import src.UserPackage.RootGroup;

public class AdminConsole {
    
    JFrame adminFrame;

    /**
     * The single instance of this Singleton IDValidator
     */
    public static AdminConsole instance = null;
    
    private RootGroup root;

    public final static int WIDTH = 481;
    public final static int HEIGHT = 786;
    public final static String DEFAULT_FONT_NAME = "Verdana";
    public final static Color BACKGROUND_COLOR = Color.WHITE;

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
        JTextPane treeViewText = new JTextPane();
        treeViewText.setContentType("text/html");
        treeViewText.setEditable(false);
        treeViewText.setText(
            "<font size = +1 face = \"" + DEFAULT_FONT_NAME + "\">" + 
            root.getFormattedID() + 
            "</font>"
        );
        treeViewText.setBackground(BACKGROUND_COLOR);

        JScrollPane treeViewPane = new JScrollPane(treeViewText);
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
        
        return userManagementPanel;
    }

    private JPanel getAnalysisPanel(){
        JPanel analysisPanel = new JPanel();
        analysisPanel.setBorder(createTitledBorder("Analysis", Color.BLACK));
        analysisPanel.setBackground(BACKGROUND_COLOR);

        return analysisPanel;
    }

    private static TitledBorder createTitledBorder(String title, Color color){
        return BorderFactory.createTitledBorder(
                                    BorderFactory.createLineBorder(color, 2), 
                                    title, 0, 0,
                                    new Font("Verdana", 0, 15)
                                );
    }

}
