import javax.swing.*;
import java.awt.*;

public class GameBoardPanel extends JPanel 
{
    private GamePiece[][] board;
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

    public GameBoardPanel(GamePiece[][] board) 
    {
        this.board = board;
    }

    public void updateDimensions(int cellSize, int offsetX, int offsetY) 
    {
        this.cellSize = cellSize;
        this.offsetX = offsetX;
        this.offsetY = offsetY;

        margin = cellSize / 10;
        imageSizeX = cellSize - (2 * margin);
        imageSizeY = cellSize - (2 * margin);
    }

    public void setPosibleMove(GamePiece p, int[][] m, int[][] c) 
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
            this.moveableSteps = new int[0][0];
            this.capturableSteps = new int[0][0];
        }
        repaint();
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

        if(capturableSteps != null && selectedPiece != null) 
        {
            for(int i = 0; i < capturableSteps.length; i++) 
            {
                int positionX = offsetX + capturableSteps[i][1] * cellSize;
                int positionY = offsetY + capturableSteps[i][0] * cellSize;

                g.setColor(Color.GRAY);
                g.drawArc(positionX, positionY, cellSize, cellSize, 0, 360);
            }
        }

        if(selectedPiece != null && isDragging) 
        {
            g.drawImage(selectedPiece.getImage(), dragX - imageSizeX / 2, dragY - imageSizeY / 2, imageSizeX, imageSizeY, this);
        }
    }
}
