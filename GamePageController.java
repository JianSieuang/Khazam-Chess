import java.awt.event.*;

public class GamePageController implements ComponentListener, MouseListener , MouseMotionListener
{
    private GameBoard model;
    private GamePageView view;
    private int width = 600;
    private int height = 600;
    
    public GamePageController() 
    {
        model = new GameBoard();
        view = new GamePageView(model.getBoard(), width, height);
        view.addComponentListener(this);
        view.getGameBoardPanel().addMouseListener(this);
        view.getGameBoardPanel().addMouseMotionListener(this);
    }
    
    //ComponentListener
    @Override
    public void componentHidden(ComponentEvent e){}
    public void componentMoved(ComponentEvent e) {}
    public void componentResized(ComponentEvent e)
    {
        view.redrawBoard();
    }
    public void componentShown(ComponentEvent e){}
    
    //MouseListener
    @Override
    public void mouseClicked(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mousePressed(MouseEvent e) 
    {
        int[] coor = view.adapter.convertCoordinate(e.getX(), e.getY());
        model.selectPiece(coor[0], coor[1]);
        view.getGameBoardPanel().setPosibleMove(model.getSelectedPiece(), model.getMoveableSteps(), model.getCapturableSteps());
        view.getGameBoardPanel().setDraggedPiece(e.getX(), e.getY());
    }
    public void mouseReleased(MouseEvent e) 
    {
        int[] coor = view.adapter.convertCoordinate(e.getX(), e.getY());
        view.getGameBoardPanel().clear(model.putPiece(coor[0], coor[1]));
    }
    
    //MouseMotionListener
    @Override
    public void mouseDragged(MouseEvent e) 
    {
        view.getGameBoardPanel().setDraggedPiece(e.getX(), e.getY());
    }
    public void mouseMoved(MouseEvent e){}
    
    public static void main(String[] args) 
    {
        new GamePageController();
    }
}
