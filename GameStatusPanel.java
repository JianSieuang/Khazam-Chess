import javax.swing.*;
import java.awt.*;

public class GameStatusPanel extends JPanel
{
    private JLabel playerLabel;
    private JLabel turnLabel;
    
    public GameStatusPanel()
    {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        playerLabel = new JLabel("Player move: Blue");
        turnLabel = new JLabel("Turn: 0");
        
        add(playerLabel);
        add(turnLabel);
    }
    
    public void setMove(String move) {
        playerLabel.setText("Player move: " + move);
    }

    public void setTurn(int turn) {
        turnLabel.setText("Turn: " + turn);
    }
}
