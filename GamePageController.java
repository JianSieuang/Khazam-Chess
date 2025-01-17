import java.awt.event.*;

public class GamePageController implements ComponentListener, MouseListener, MouseMotionListener, ActionListener, ItemListener
{
    private GameBoard gameModel;
    private GamePageView view;
    private SettingModel settingModel;
    private int width = 600;
    private int height = 600;
    
    public GamePageController() 
    {
        gameModel = new GameBoard();
        settingModel = new SettingModel();
        view = new GamePageView(gameModel.getBoard(), width, height);
        view.addComponentListener(this);
        view.getGameBoardPanel().addMouseListener(this);
        view.getGameBoardPanel().addMouseMotionListener(this);
        initializeMenuListener();
    }
    
    private void initializeMenuListener()
    {
        view.getNavigationBar().getNewGameItem().addActionListener(this);
        view.getNavigationBar().getSaveGameItem().addActionListener(this);
        view.getNavigationBar().getExitItem().addActionListener(this);
        view.getNavigationBar().getRulesItem().addActionListener(this);
        view.getNavigationBar().getSoundMenuItem().addItemListener(this);
    }
    
    
    //ComponentListener
    @Override public void componentHidden(ComponentEvent e){}
    @Override public void componentMoved(ComponentEvent e) {}
    @Override
    public void componentResized(ComponentEvent e)
    {
        view.redrawBoard();
    }
    @Override public void componentShown(ComponentEvent e){}
    
    //MouseListener
    @Override public void mouseClicked(MouseEvent e){}
    @Override public void mouseEntered(MouseEvent e){}
    @Override public void mouseExited(MouseEvent e){}
    @Override 
    public void mousePressed(MouseEvent e) 
    {
        int[] coor = view.adapter.convertCoordinate(e.getX(), e.getY());
        gameModel.selectPiece(coor[0], coor[1]);
        view.getGameBoardPanel().setPosibleMove(gameModel.getSelectedPiece(), gameModel.getMoveableSteps(), gameModel.getCapturableSteps());
        view.getGameBoardPanel().setDraggedPiece(e.getX(), e.getY());
    }
    @Override
    public void mouseReleased(MouseEvent e) 
    {
        int[] coor = view.adapter.convertCoordinate(e.getX(), e.getY());
        view.getGameBoardPanel().clear(gameModel.putPiece(coor[0], coor[1]));
    }
    
    //MouseMotionListener
    @Override
    public void mouseDragged(MouseEvent e) 
    {
        view.getGameBoardPanel().setDraggedPiece(e.getX(), e.getY());
    }
    @Override public void mouseMoved(MouseEvent e){}
    
    //ActionListener
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "New Game":
                settingModel.newGame();
                break;
            case "Save Game":
                settingModel.saveGame();
                break;
            case "Exit":
                settingModel.exitGame();
                break;
            case "Rules":
                view.getNavigationBar().showRulesDialog();
                break;
            default:
                break;
        }
    }
    
    //ItemListener
    public void itemStateChanged(ItemEvent e) 
    {
        settingModel.toggleSound();
    }
    
    public static void main(String[] args) 
    {
        new GamePageController();
    }
}
