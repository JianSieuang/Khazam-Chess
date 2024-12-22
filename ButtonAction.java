import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ButtonAction {
    public static MouseAdapter createButtonHoverEffect(JButton button, Color defaultBackground) {
        return new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(Color.GRAY);
                button.setForeground(Color.WHITE);
                new BtnSound("hover").actionPerformed(null); // play hover sound
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(defaultBackground);
                button.setForeground(Color.BLACK);
            }
        };
    }

    public static ActionListener createButtonClickAction(Runnable onClickAction) {
        return e -> {
            new BtnSound("click").actionPerformed(null); // Play click sound
            onClickAction.run();
        };
    }

    public static ComponentAdapter createPanelResizeListener(JButton button, JPanel panel) {
        return new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if ("New Game".equals(button.getText()) || "Load Game".equals(button.getText())) {
                    adjustButtonSize(button, panel); // new game and load game button size
                } else if ("Setting".equals(button.getText()) || "Quit".equals(button.getText())) {
                    adjust_Setting_Quit_ButtonSize(button, panel); // setting and quit button size
                }
                //adjustButtonSize(button, panel);
                button.revalidate();
                button.repaint();
            }
        };
    }

    // method to adjust button size
    private static void adjustButtonSize(JButton button, JPanel panel) {
        int panelWidth = panel.getWidth();
        int buttonWidth = (int) (panelWidth * 0.4); // button will be 40% panel width
        int buttonHeight = 50; // fixed height
        button.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        button.setMaximumSize(new Dimension(buttonWidth, buttonHeight));
    }
    // method to adjust button size
    private static void adjust_Setting_Quit_ButtonSize(JButton button, JPanel panel) {
        int panelWidth = panel.getWidth();
        int buttonWidth = Math.min((int) (panelWidth * 0.2), 282); // button will be panelwidth * 20% or 282 max
        int buttonHeight = 50; // fixed height
        button.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        button.setMaximumSize(new Dimension(282, buttonHeight));
    }
}
