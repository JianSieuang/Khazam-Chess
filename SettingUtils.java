import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

public class SettingUtils {
    // get the label based on the setting state
    public static String getOnOffLabel(boolean state) {
        return state ? "ON" : "OFF";
    }

    // set button color based on the setting state
    public static void setButtonDesign(JButton button, boolean isOn) {
        Color backgroundColor;

        // remove border
        button.setBorderPainted(false);

        // remove btn focus
        button.setFocusPainted(false);

        if (isOn) {
            backgroundColor = new Color(144, 238, 144);
        } else {
            backgroundColor = new Color(240, 128, 128);
        }

        button.setBackground(backgroundColor);
        button.setForeground(Color.BLACK);

        // add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(backgroundColor.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(backgroundColor);
            }
        });
    }
}