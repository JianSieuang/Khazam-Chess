import javax.swing.*;
import java.awt.*;

public class GamePageView extends JFrame 
{
    private GamePiece[][] board;
    private int width;
    private int height;
    private GameBoardPanel GamePanel;
    private NavigationBar navbar;
    public CoordinateAdapter adapter;

    public GamePageView(GamePiece[][] board, int w, int h,boolean initialSoundState, boolean initialMusicState) 
    {
        super("Game Board");
        this.board = board;
        this.width = w;
        this.height = h;

        adapter = new CoordinateAdapter();
        navbar = new NavigationBar(initialSoundState, initialMusicState);
        GamePanel = new GameBoardPanel(board);
        setLayout(new BorderLayout());
        setJMenuBar(navbar);
        add(GamePanel, BorderLayout.CENTER);
        
        setMinimumSize(new Dimension(450, 450));
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
        redrawBoard();
    }

    public void redrawBoard()
    {
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
    
    public GameBoardPanel getGameBoardPanel()
    {
        return GamePanel;
    }
    
    public NavigationBar getNavigationBar()
    {
        return navbar;
    }
}