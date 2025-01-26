import java.awt.event.*;
import javax.swing.SwingUtilities;
import java.awt.Color;

public class GamePageController
        implements ComponentListener, MouseListener, MouseMotionListener, ActionListener, ItemListener {
    private static GamePageController controller;
    private GameBoard gameModel;
    private GamePageView view;
    private NavbarModel navbarModel;
    private int width = 600;
    private int height = 600;

    private GamePageController(String gameType) {
        gameModel = new GameBoard();
        gameModel.getGameType(gameType);
        navbarModel = new NavbarModel(gameModel);
        view = new GamePageView(gameModel.getBoard(), width, height, navbarModel.getIsButtonSoundEnabled(), navbarModel.getIsMusicSoundEnabled());

        initializeListener();
        view.setVisible(true);
    }

    public static GamePageController getController(String gameType) {
        if (controller == null) {
            controller = new GamePageController(gameType);
        } else if (!controller.view.isDisplayable()) {
            controller.gameModel.getGameType(gameType);
            controller.view.setVisible(true);
        }
       
        return controller;
    }

    private void initializeListener() {
        view.addComponentListener(this);
        view.getGameBoardPanel().addMouseListener(this);
        view.getGameBoardPanel().addMouseMotionListener(this);
        view.getNavigationBar().getNewGameItem().addActionListener(this);
        view.getNavigationBar().getSaveGameItem().addActionListener(this);
        view.getNavigationBar().getExitItem().addActionListener(this);
        view.getNavigationBar().getButtonSoundMenuItem().addItemListener(this);
        view.getNavigationBar().getSoundMenuItem().addItemListener(this);
        view.getNavigationBar().getRulesItem().addActionListener(this);
        
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

    @Override
    public void componentResized(ComponentEvent e) {
        view.redrawBoard();
    }

    @Override
    public void componentShown(ComponentEvent e) {
        view.getGameStatusPanel().setStatus(gameModel.getMove(), gameModel.getTurn());
        view.getGameBoardPanel().setColors(Color.decode(gameModel.getPrimaryColor()), Color.decode(gameModel.getSecondaryColor()));
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
        int[] coor = view.adapter.convertCoordinate(e.getX(), e.getY());
        gameModel.selectPiece(coor[0], coor[1]);
        view.getGameBoardPanel().setPosibleMove(gameModel.getSelectedPiece(), gameModel.getMoveableSteps(),
                gameModel.getCapturableSteps());
        view.getGameBoardPanel().setDraggedPiece(e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int[] coor = view.adapter.convertCoordinate(e.getX(), e.getY());
        view.getGameBoardPanel().clear(gameModel.putPiece(coor[0], coor[1]));

        String winner = gameModel.getWinner();
        if (winner != null) {
            view.getGameBoardPanel().showWinnerDialog(winner);

            // after the OK button is pressed, transition to the landing page
            SwingUtilities.invokeLater(() -> {
                view.dispose(); // dispose of the current game window
                HomePageController.getController(); // navigate to the landing page
            });
        }
        
        view.getGameStatusPanel().setStatus(gameModel.getMove(), gameModel.getTurn());
    }

    // MouseMotionListener
    @Override
    public void mouseDragged(MouseEvent e) {
        view.getGameBoardPanel().setDraggedPiece(e.getX(), e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    // ActionListener
    public void actionPerformed(ActionEvent e) {
        new BtnSound("click", SettingController.getController()).actionPerformed(null);

        String command = e.getActionCommand();
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

    // ItemListener
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == view.getNavigationBar().getButtonSoundMenuItem()) {
            navbarModel.toogleButtonSound(); // Update button sound state
            SettingManager.setEnabledButtonSound(navbarModel.getIsButtonSoundEnabled()); // sync the state
        } else if (e.getSource() == view.getNavigationBar().getSoundMenuItem()) {
            navbarModel.toggleSound(); // Update music sound state
            SettingManager.setEnabledMusicSound(navbarModel.getIsMusicSoundEnabled()); // sync the state

            if (navbarModel.getIsMusicSoundEnabled()) {
                AudioPlayer.playBackgroundMusic();
            } else {
                AudioPlayer.stopBackgroundMusic();
            }
        }
        SettingManager.saveSetting(); // save to setting.txt

        new BtnSound("click", SettingController.getController()).actionPerformed(null);
    }
}
