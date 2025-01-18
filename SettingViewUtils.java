import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JButton;

public class SettingViewUtils {

    public static String getOnOffLabel(boolean state) {
        return state ? "ON" : "OFF";
    }

    public static void setButtonDesign(JButton button, boolean isOn) {
        Color backgroundColor = isOn ? new Color(144, 238, 144) : new Color(240, 128, 128);

        button.setUI(new RoundedButtonUI()); // set the button's UI to the custom UI
        button.setFocusPainted(false); // remove focus border
        button.setOpaque(false); // set to true to allow background color to show
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // remove default border

        button.setBackground(backgroundColor);
        button.setForeground(Color.BLACK); // default text color
    }
}
