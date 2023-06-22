package src.UIPackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/**
 * The TwitterFrame class extends the JFrame class and implements helper methods
 * and constant attributes for any JFrame created for the Mini-Twitter application
 * (admin or userview)
 * 
 * @author George Matta
 * @version 1.0
 */
public class TwitterFrame extends JFrame {
    
    /**
     * The width of the screen
     */
    public final int SCREEN_WIDTH = 481;
    
    /**
     * The height of the screen
     */
    public final int SCREEN_HEIGHT = 786;

    /**
     * The default font used throughout the program
     */
    public final String DEFAULT_FONT_NAME = "Verdana";

    /**
     * The background color used throughout the program
     */
    public final Color BACKGROUND_COLOR = Color.WHITE;

    /**
     * The size of a big panel
     */
    public final Dimension BIG_PANEL_DIMENSION = new Dimension(
                                                            SCREEN_WIDTH/100, 
                                                            55*SCREEN_HEIGHT/100
                                                        );
    
                                                        /**
     * The size of a small panel
     */
    public final Dimension SMALL_PANEL_DIMENSION = new Dimension(
                                                                SCREEN_WIDTH, 
                                                                SCREEN_HEIGHT/15
                                                            );

    /**
     * This constructor takes a title and creates a JFrame out of it
     * @param title
     */
    public TwitterFrame(String title){
        super(title);
    }

    /**
     * Creates a titled border with the default font
     * @param title The title to use in the border
     * @param color The color of the border
     * @return The created border
     */
    public TitledBorder createTitledBorder(String title, Color color){
        return BorderFactory.createTitledBorder(
                                    BorderFactory.createLineBorder(color, 2), 
                                    title, 0, 0,
                                    new Font(DEFAULT_FONT_NAME, 0, 13)
                                );
    }

    /**
     * Retrieves the text typed in a text field and empties the text field
     * @param idTextField The text field to retrieve text for
     * @return The text typed in the field
     */
    public String getTypedText(JTextField idTextField){
        String typedText = idTextField.getText();
        idTextField.setText("");

        return typedText;
    }     
}
