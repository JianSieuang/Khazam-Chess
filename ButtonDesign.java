import javax.swing.*;
import java.awt.*;

public class ButtonDesign {

    public static JButton createMenuButton(String text, Color backgroundColor, JFrame frame, JPanel panel,
            Runnable onClickAction) {
        RoundedButton button = new RoundedButton(text, 50); // 20 is the corner radius
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(backgroundColor);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.BOLD, 17));

        // remove default border
        button.setBorder(BorderFactory.createEmptyBorder());

        // apply hover effect
        button.addMouseListener(ButtonAction.createButtonHoverEffect(button, backgroundColor));

        // apply click action
        button.addActionListener(ButtonAction.createButtonClickAction(onClickAction));

        // add resize listener
        panel.addComponentListener(ButtonAction.createPanelResizeListener(button, panel));

        return button;
    }
}
