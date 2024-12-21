import java.awt.event.*;

public class GameController implements ComponentListener, MouseListener , MouseMotionListener
{
    private GameBoard model;
    private GameBoardView view;
    private int width = 600;
    private int height = 600;
    
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
        view.redrawBoard();
    }
    public void componentShown(ComponentEvent e){}
    
    //MouseListener
    public void mouseClicked(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mousePressed(MouseEvent e) 
    {
        int[] coor = view.adapter.convertCoordinate(e.getX(), e.getY());
        model.selectPiece(coor[0], coor[1]);
        view.setPosibleMove(model.getSelectedPiece(), model.getMoveableSteps(),model.getCapturableSteps());
        view.setDraggedPiece(e.getX(), e.getY());
    }
    public void mouseReleased(MouseEvent e) 
    {
        int[] coor = view.adapter.convertCoordinate(e.getX(), e.getY());
        view.clear(model.putPiece(coor[0], coor[1]));
    }
    
    //MouseMotionListener
    public void mouseDragged(MouseEvent e) 
    {
        view.setDraggedPiece(e.getX(), e.getY());
    }
    public void mouseMoved(MouseEvent e){}
    
    public static void main(String[] args) 
    {
        new GameController();
    }
}
