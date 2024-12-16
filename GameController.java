import javax.swing.*;
import java.awt.event.*;

public class GameController implements ComponentListener, MouseListener , MouseMotionListener
{
    private GameBoard model;
    private GameBoardView view;
    private int width = 600;
    private int height = 600;
    
    private int selectedRow = -1;
    private int selectedCol = -1;
    
    private GamePiece draggedPiece = null;
    
    private int turn = 0;
    
    public GameController() 
    {
        model = new GameBoard();
        view = new GameBoardView(model.getBoard(), width, height);
        view.addComponentListener(this);
        view.getContentPane().addMouseListener(this);
        view.getContentPane().addMouseMotionListener(this);
    }
    
    //ComponentListener
    public void componentHidden(ComponentEvent e){}
    public void componentMoved(ComponentEvent e) {}
    public void componentResized(ComponentEvent e)
    {
        JFrame frame = (JFrame) e.getComponent();
        width = frame.getContentPane().getWidth();
        height = frame.getContentPane().getHeight();
        view.redrawBoard(width, height);
    }
    public void componentShown(ComponentEvent e){}
    
    //MouseListener
    public void mouseClicked(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mousePressed(MouseEvent e) 
    {
        int mouseX = e.getX();
        int mouseY = e.getY();
    
        GameBoardView.GameBoardPanel panel = (GameBoardView.GameBoardPanel) view.getContentPane().getComponent(0);
        int cellSize = panel.getCellSize();
        int offsetX = panel.getOffsetX();
        int offsetY = panel.getOffsetY();
    
        int col = (mouseX - offsetX) / cellSize;
        int row = (mouseY - offsetY) / cellSize;
    
        if (row >= 0 && row < model.getBoard().length && col >= 0 && col < model.getBoard()[0].length) 
        {
            if (model.getBoard()[row][col] != null && model.getBoard()[row][col].getPlayer() == turn)
            {
                selectedRow = row;
                selectedCol = col;
                
                draggedPiece = model.getBoard()[row][col];
                model.getBoard()[row][col] = null;
                view.setDraggedPiece(draggedPiece, mouseX, mouseY);
                view.redrawBoard(width, height);
            }
        }
    }
    public void mouseReleased(MouseEvent e) 
    {
        if(draggedPiece == null)
            return;
        int mouseX = e.getX();
        int mouseY = e.getY();
    
        GameBoardView.GameBoardPanel panel = (GameBoardView.GameBoardPanel) view.getContentPane().getComponent(0);
        int cellSize = panel.getCellSize();
        int offsetX = panel.getOffsetX();
        int offsetY = panel.getOffsetY();
    
        int col = (mouseX - offsetX) / cellSize;
        int row = (mouseY - offsetY) / cellSize;
    
        if (row >= 0 && row < model.getBoard().length && col >= 0 && col < model.getBoard()[0].length) 
        {
            if((model.getBoard()[row][col] == null || model.getBoard()[row][col].getPlayer() != turn) && !(row == selectedRow && col == selectedCol)) 
            {
                model.getBoard()[selectedRow][selectedCol] = null;
                model.getBoard()[row][col] = draggedPiece;
                turn = turn == 0 ? 1 : 0; 
                model.turnBoard(turn);
                draggedPiece = null;
                selectedRow = -1;
                selectedCol = -1;
            } 
            else 
            {
                model.getBoard()[selectedRow][selectedCol] = draggedPiece;
            }
        } 
        else 
        {
            model.getBoard()[selectedRow][selectedCol] = draggedPiece;
        }
        
        view.clearDraggedPiece();
        view.redrawBoard(width, height);
    }
    
    //MouseMotionListener
    public void mouseDragged(MouseEvent e) 
    {
        if (draggedPiece != null) 
        {
            view.setDraggedPiece(draggedPiece, e.getX(), e.getY());
        }
    }
    public void mouseMoved(MouseEvent e) {}
    
    public static void main(String[] args) 
    {
        new GameController();
    }
}
