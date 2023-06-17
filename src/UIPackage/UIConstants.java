package src.UIPackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;

public interface UIConstants {
    
    public final static int SCREEN_WIDTH = 481;
    public final static int SCREEN_HEIGHT = 786;

    public final static String DEFAULT_FONT_NAME = "Verdana";
    public final static Color BACKGROUND_COLOR = Color.WHITE;

    public final static Dimension BIG_PANEL_DIMENSION = new Dimension(
                                                            UIConstants.SCREEN_WIDTH/100, 
                                                            55*UIConstants.SCREEN_HEIGHT/100
                                                        );
    public final static Dimension SMALL_PANEL_DIMENSION = new Dimension(
                                                                UIConstants.SCREEN_WIDTH, 
                                                                UIConstants.SCREEN_HEIGHT/15
                                                            );

    public static TitledBorder createTitledBorder(String title, Color color){
        return BorderFactory.createTitledBorder(
                                    BorderFactory.createLineBorder(color, 2), 
                                    title, 0, 0,
                                    new Font(UIConstants.DEFAULT_FONT_NAME, 0, 13)
                                );
    }
    
}
