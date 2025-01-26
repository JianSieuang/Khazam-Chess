import java.awt.event.*;
import javax.swing.SwingUtilities;
import java.awt.Color;

// Chong Jian Sieuang
/* 
 * Controller, manage the game page.
 * handle interactions between the game model (GameBoard) and the view (GamePageView).
 */
public class GamePageController
        implements ComponentListener, MouseListener, MouseMotionListener, ActionListener, ItemListener {
    private static GamePageController controller;
    private GameBoard gameModel;
    private GamePageView view;
    private NavbarModel navbarModel;
    private int width = 600;
    private int height = 600;

    // Singleton design pattern 
    private GamePageController(String gameType) {
        gameModel = new GameBoard();
        gameModel.getGameType(gameType);
        navbarModel = new NavbarModel(gameModel);
        view = new GamePageView(gameModel.getBoard(), width, height, navbarModel.getIsButtonSoundEnabled(),
                navbarModel.getIsMusicSoundEnabled());

        initializeListener();
        view.setVisible(true);
    }

    // if the controller is null, will generate a controller
    // else will use the same controller 
    public static GamePageController getController(String gameType) {
        if (controller == null) {
            controller = new GamePageController(gameType);
        } else if (!controller.view.isDisplayable()) {
            controller.gameModel.getGameType(gameType);
            controller.view.setVisible(true);
        }

        return controller;
    }

    // initialize all the listener
    private void initializeListener() {
        view.addComponentListener(this);
        // this is game board panel's listener
        view.getGameBoardPanel().addMouseListener(this);
        view.getGameBoardPanel().addMouseMotionListener(this);
        // this is navigation bar's listener
        view.getNavigationBar().getNewGameItem().addActionListener(this);
        view.getNavigationBar().getSaveGameItem().addActionListener(this);
        view.getNavigationBar().getExitItem().addActionListener(this);
        view.getNavigationBar().getButtonSoundMenuItem().addItemListener(this);
        view.getNavigationBar().getSoundMenuItem().addItemListener(this);
        view.getNavigationBar().getRulesItem().addActionListener(this);

        // this is for closing game page listener, before close game will ask wanna save game or not;
        view.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new BtnSound("click", SettingController.getController()).actionPerformed(null);
                navbarModel.exitGame(view.showConfirmExitDialog(), view);
            }
        });
    }

    // ComponentListener
    @Override
    public void componentHidden(ComponentEvent e) {
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    // while the jframe is resized, the game board will redraw
    @Override
    public void componentResized(ComponentEvent e) {
        view.redrawBoard();
    }

    // while the jframe is shown, the game status panel will get turns and move from game board (model)
    // the game board panel will get priamry and secondary color from game board (model)
    @Override
    public void componentShown(ComponentEvent e) {
        view.getGameStatusPanel().setStatus(gameModel.getMove(), gameModel.getTurn());
        view.getGameBoardPanel().setColors(Color.decode(gameModel.getPrimaryColor()),
                Color.decode(gameModel.getSecondaryColor()));
    }

    // MouseListener
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // when mouse is press, the coordinate that you pressed will send to adapter and convert to the 2D array
        int[] coor = view.adapter.convertCoordinate(e.getX(), e.getY());
        // the 2d array will send to the game board (model) for checking 
        gameModel.selectPiece(coor[0], coor[1]);
        // game board view will get the selected piece and the possible move from the game board (model)
        view.getGameBoardPanel().setPosibleMove(gameModel.getSelectedPiece(), gameModel.getMoveableSteps(),
                gameModel.getCapturableSteps());
        view.getGameBoardPanel().setDraggedPiece(e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // when mouse is release, the coordinate that you released will send to adapter and convert to the 2D array
        int[] coor = view.adapter.convertCoordinate(e.getX(), e.getY());
        // gameModel.putPiece will check is valid or not, then will pass true or false to the clear() function in the game board model
        // if is true, the possible move will be cleared and the selected piece will move to the place that you released
        view.getGameBoardPanel().clear(gameModel.putPiece(coor[0], coor[1]));

        // every time of relase will check having a winner or not
        String winner = gameModel.getWinner();
        if (winner != null) {
            // if have winner, show dialog
            view.getGameBoardPanel().showWinnerDialog(winner);

            // after the OK button is pressed, transition to the landing page
            SwingUtilities.invokeLater(() -> {
                // dispose of the current game window
                view.dispose(); 

                // navigate to the landing page
                HomePageController.getController();
            });
        }
        
        //the game status panel will get turns and move from game board (model) each turn
        view.getGameStatusPanel().setStatus(gameModel.getMove(), gameModel.getTurn());
    }

    // MouseMotionListener
    @Override
    public void mouseDragged(MouseEvent e) {
        // if draging, the selected piece will following the mouse
        view.getGameBoardPanel().setDraggedPiece(e.getX(), e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    // ActionListener
    public void actionPerformed(ActionEvent e) {
        // this actionperformed is for navigation bar
        // sound of clicking
        new BtnSound("click", SettingController.getController()).actionPerformed(null);

        // get the button name
        String command = e.getActionCommand();
        // using switch case to find out the function, the funtion details is in the NavbarModel (model)
        switch (command) {
            case "New Game":
                navbarModel.newGame(view.getNavigationBar().showConfirmNewGameDialog());
                view.redrawBoard();
                break;
            case "Save Game":
                navbarModel.saveGame();
                break;
            case "Exit":
                navbarModel.exitGame(view.showConfirmExitDialog(), view);
                break;
            case "Rules":
                view.getNavigationBar().showRulesDialog();
                break;
            default:
                break;
        }
    }

    /*
     * ItemListener, handle the item state change event.
     */
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == view.getNavigationBar().getButtonSoundMenuItem()) {
            // Update button sound state
            navbarModel.toogleButtonSound();

            // sync the state
            SettingManager.setEnabledButtonSound(navbarModel.getIsButtonSoundEnabled());
        } else if (e.getSource() == view.getNavigationBar().getSoundMenuItem()) {
            // update music sound state
            navbarModel.toggleSound();

            // sync the state
            SettingManager.setEnabledMusicSound(navbarModel.getIsMusicSoundEnabled());

            if (navbarModel.getIsMusicSoundEnabled()) {
                AudioPlayer.playBackgroundMusic();
            } else {
                AudioPlayer.stopBackgroundMusic();
            }
        }
        // save to setting.txt
        SettingManager.saveSetting();

        new BtnSound("click", SettingController.getController()).actionPerformed(null);
    }
}
