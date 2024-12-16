import javax.swing.*;
import java.awt.*;

public class ButtonDesign {

    public static JButton createMenuButton(String text, Color backgroundColor, JFrame frame, JPanel panel, Runnable onClickAction) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(backgroundColor);
        button.setFont(new Font("Arial", Font.BOLD, 17));

         // Apply hover effect
         button.addMouseListener(ButtonAction.createButtonHoverEffect(button, backgroundColor));

         // Apply click action
         button.addActionListener(ButtonAction.createButtonClickAction(onClickAction));
 
         // Add resize listener
         panel.addComponentListener(ButtonAction.createPanelResizeListener(button, panel));
 
         return button;
    }

    
}
