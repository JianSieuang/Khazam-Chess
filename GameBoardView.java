import javax.swing.*;
import java.awt.*;

public class GameBoardView extends JFrame
{
    private GamePiece[][] board;
    private int width;
    private int height;
    private int cellSize;
    private int margin;
    
    private GamePiece draggedPiece;
    private int dragX;
    private int dragY;
    
    public GameBoardView(GamePiece[][] board, int w, int h) 
    {
        super("Game Board");
        this.board = board;
        this.width = w;
        this.height = h;
        
        setMinimumSize(new Dimension(400, 400));
        setSize(width, height);
        
        GameBoardPanel panel = new GameBoardPanel();
        add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    public void redrawBoard(int w, int h)
    {
        this.width = w;
        this.height = h;
        
        int heightSize = this.height / board.length;
        int widthSize = this.width / board[0].length;
    
        this.cellSize = widthSize > heightSize? heightSize: widthSize;
        this.margin = cellSize / 10;
    
        repaint();
    }
    
    public void setDraggedPiece(GamePiece p, int x, int y) 
    {
        this.draggedPiece = p;
        this.dragX = x;
        this.dragY = y;
        repaint();
    }

    public void clearDraggedPiece() 
    {
        this.draggedPiece = null;
        repaint();
    }
    
    public class GameBoardPanel extends JPanel
    {
        public GameBoardPanel()
        {
            super();
        }
        
        @Override
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            int offsetX = (getWidth() - (board[0].length * cellSize)) / 2;
            int offsetY = (getHeight() - (board.length * cellSize)) / 2;
            
            for(int row = 0; row < board.length; row++)
            {
                for(int col = 0; col < board[0].length; col++)
                {
                    g.setColor(Color.BLUE);
                    int positionX = offsetX + col * cellSize;
                    int positionY = offsetY + row * cellSize;
                    g.drawRect(positionX, positionY, cellSize, cellSize);
                    
                    if(board[row][col] != null)
                    {
                        int imageSizeX = cellSize - (2 * margin);
                        int imageSizeY = cellSize - (2 * margin);
                        g.drawImage(board[row][col].getImage(), positionX + margin, positionY + margin, imageSizeX, imageSizeY, this);
                    }
                }
            }
            
            if (draggedPiece != null) 
            {
                int imageSizeX = cellSize - (2 * margin);
                int imageSizeY = cellSize - (2 * margin);
                g.drawImage(draggedPiece.getImage(), dragX - imageSizeX / 2, dragY - imageSizeY  / 2, imageSizeX, imageSizeY, this);
            }
        }
        
        public int getCellSize() 
        {
            return cellSize;
        }
    
        public int getOffsetX() 
        {
            return (getWidth() - (board[0].length * cellSize)) / 2;
        }
    
        public int getOffsetY() 
        {
            return (getHeight() - (board.length * cellSize)) / 2;
        }
    }
}
