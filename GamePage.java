import javax.swing.*;
import java.awt.*;

public class GamePage extends JFrame {

    public GamePage(String mode) {
        setTitle("Kwazam Chess");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // add navigation bar to the top
        setJMenuBar(new NavigationBar());

        if(mode.equals("new")) {
            // call new game function
        } else if (mode.equals("load")) {
            // call to laod saved game function
        }

        // remove this when game is ready
        JPanel gameBoardPlaceholder = new JPanel();
        gameBoardPlaceholder.add(new JLabel("Game Board Here"));
        gameBoardPlaceholder.setBackground(Color.LIGHT_GRAY);

        // game board put here, replace "gameBoardPlaceholder" to mode that selected
        add(gameBoardPlaceholder, BorderLayout.CENTER);

        // make the frame visible
        setVisible(true);
    }
}
