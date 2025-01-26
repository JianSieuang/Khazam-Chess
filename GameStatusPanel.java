import javax.swing.*;

public class GameStatusPanel extends JPanel {
    private JLabel playerLabel;
    private JLabel turnLabel;

    public GameStatusPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        playerLabel = new JLabel("Player move: Blue");
        turnLabel = new JLabel("Turn: 0");

        add(playerLabel);
        add(turnLabel);
    }

    public void setStatus(String move, int turn) {
        turn++;
        playerLabel.setText("Player move: " + move);
        turnLabel.setText("Turn: " + turn);
    }
}
