import javax.swing.*;

/*
 * View, for game status panel.
 * displays the current player's turn and the total number of turns taken in the game
 */
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
