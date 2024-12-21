import javax.swing.*;
import java.awt.*;

public class GameBoardView extends JFrame
{
    private GamePiece[][] board;
    private int width;
    private int height;
    private int cellSize;
    private int offsetX;
    private int offsetY;
    
    private int margin;
    private int imageSizeX;
    private int imageSizeY;
    
    private GamePiece selectedPiece;
    
    private boolean isDragging = false;
    private int dragX;
    private int dragY;
    
    private int[][] moveableSteps;
    private int[][] capturableSteps;
    
    public CoordinateAdapter adapter;
    
    public GameBoardView(GamePiece[][] board, int w, int h) 
    {
        super("Game Board");
        this.board = board;
        this.width = w;
        this.height = h;
        
        adapter = new CoordinateAdapter();
        
        setMinimumSize(new Dimension(400, 400));
        setSize(width, height);
        
        GameBoardPanel panel = new GameBoardPanel();
        add(panel);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        
        redrawBoard();
    }
    
    public void redrawBoard()
    {
        width = getWidth()- getInsets().left - getInsets().right;;
        height = getHeight() - getInsets().top - getInsets().bottom;
        
        int widthSize = width / board[0].length;
        int heightSize = height / board.length;
    
        cellSize = widthSize > heightSize? heightSize: widthSize;
        
        offsetX = (width - (board[0].length * cellSize)) / 2;
        offsetY = (height - (board.length * cellSize)) / 2;
        
        margin = cellSize / 10;
        imageSizeX = cellSize - (2 * margin);
        imageSizeY = cellSize - (2 * margin);
        
        adapter.update(cellSize, offsetX, offsetY);
        repaint();
    }
    
    public void setPosibleMove (GamePiece p, int [][] m, int[][] c)
    {
        this.selectedPiece = p;
        this.moveableSteps = m;
        this.capturableSteps = c;
        repaint();
    }
    
    public void setDraggedPiece(int x, int y) 
    {
        this.isDragging = true;
        this.dragX = x;
        this.dragY = y;
        repaint();
    }

    public void clear(boolean isMoved) 
    {
        this.isDragging = false;
        if(isMoved)
        {
            this.selectedPiece = null;
            this.moveableSteps = new int [0][0];
            this.capturableSteps = new int [0][0];
        }
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

            for(int row = 0; row < board.length; row++)
            {
                for(int col = 0; col < board[0].length; col++)
                {
                    int positionX = offsetX + col * cellSize;
                    int positionY = offsetY + row * cellSize;
                    
                    g.setColor(Color.BLUE);
                    g.drawRect(positionX, positionY, cellSize, cellSize);
                    
                    if(board[row][col] != null)
                    {
                        g.drawImage(board[row][col].getImage(), positionX + margin, positionY + margin, imageSizeX, imageSizeY, this);
                    }
                }
            }
            
            if(moveableSteps != null && selectedPiece != null)
            {
                for(int i = 0; i < moveableSteps.length; i++)
                {
                    int size = cellSize / 4;
                    int positionX = offsetX + moveableSteps[i][1] * cellSize + (cellSize - size) / 2;
                    int positionY = offsetY + moveableSteps[i][0] * cellSize + (cellSize - size) / 2;
                    
                    g.setColor(Color.GRAY);
                    g.fillArc(positionX, positionY, size, size, 0, 360);
                }
            }
            
            if (selectedPiece != null && isDragging) 
            {
                g.drawImage(selectedPiece.getImage(), dragX - imageSizeX / 2, dragY - imageSizeY  / 2, imageSizeX, imageSizeY, this);
            }
        }
    }
}
