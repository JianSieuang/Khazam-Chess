import javax.swing.*;
import java.awt.*;

/* 
 * View, represents the main user interface for the game, including the game board,
 * navigation bar, and game status panel.
 */
public class GamePageView extends JFrame {
    private GamePiece[][] board;
    private int width;
    private int height;
    private GameBoardPanel GamePanel;
    private GameStatusPanel statusPanel;
    private NavigationBar navbar;
    public CoordinateAdapter adapter;

    public GamePageView(GamePiece[][] board, int w, int h, boolean initialSoundState, boolean initialMusicState) {
        super("Game Board");
        this.board = board;
        this.width = w;
        this.height = h;

        BackgroundPanel panel = new BackgroundPanel("setting_page");
        adapter = new CoordinateAdapter();
        navbar = new NavigationBar(initialSoundState, initialMusicState);
        GamePanel = new GameBoardPanel(board);
        statusPanel = new GameStatusPanel();

        setJMenuBar(navbar);
        add(panel);

        GamePanel.setOpaque(false);
        statusPanel.setOpaque(false);

        panel.setLayout(new BorderLayout());
        panel.add(GamePanel, BorderLayout.CENTER);
        panel.add(statusPanel, BorderLayout.WEST);

        setMinimumSize(new Dimension(450, 450));
        setSize(width, height);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // set application icon
        setIconImage(Toolkit.getDefaultToolkit().getImage("Picture/application_icon.png"));

        setLocationRelativeTo(null);
        redrawBoard();
    }

    public void redrawBoard() {
        width = GamePanel.getWidth();
        height = GamePanel.getHeight();

        int widthSize = width / board[0].length;
        int heightSize = height / board.length;

        int cellSize = Math.min(widthSize, heightSize);

        int offsetX = (width - (board[0].length * cellSize)) / 2;
        int offsetY = (height - (board.length * cellSize)) / 2;

        adapter.update(cellSize, offsetX, offsetY);
        GamePanel.updateDimensions(cellSize, offsetX, offsetY);
        GamePanel.repaint();
    }

    public GameBoardPanel getGameBoardPanel() {
        return GamePanel;
    }

    public NavigationBar getNavigationBar() {
        return navbar;
    }

    public GameStatusPanel getGameStatusPanel() {
        return statusPanel;
    }

    // dialog confirmation for exit game
    public int showConfirmExitDialog() {
        // temporary frame as a parent for the dialog
        JFrame tempFrame = new JFrame();
        tempFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // no UI for this frame
        tempFrame.setUndecorated(true);

        // keep it invisible
        tempFrame.setVisible(false);

        int result = JOptionPane.showConfirmDialog(
                tempFrame,
                "Save game before exiting?",
                "Save Game Before Exit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        // dispose the temporary frame
        tempFrame.dispose();

        return result;
    }
}