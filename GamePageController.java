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
        gameModel = new GameBoard(gameType);
        navbarModel = new NavbarModel(gameModel);
        view = new GamePageView(gameModel.getBoard(), width, height, navbarModel.getIsButtonSoundEnabled(),
                navbarModel.getIsMusicSoundEnabled());

        view.addComponentListener(this);
        view.getGameBoardPanel().addMouseListener(this);
        view.getGameBoardPanel().addMouseMotionListener(this);
        initializeMenuListener();
        updatePanelColors();
    }

    public static GamePageController getController(String gameType) {
        if (controller == null) {
            controller = new GamePageController(gameType);
        } else if (controller.view == null || !controller.view.isDisplayable()) {
            controller.gameModel = new GameBoard(gameType);
            controller.navbarModel = new NavbarModel(controller.gameModel);
            controller.view = new GamePageView(controller.gameModel.getBoard(), controller.width, controller.height,
                    controller.navbarModel.getIsButtonSoundEnabled(), controller.navbarModel.getIsMusicSoundEnabled());
            controller.view.addComponentListener(controller);
            controller.view.getGameBoardPanel().addMouseListener(controller);
            controller.view.getGameBoardPanel().addMouseMotionListener(controller);
            controller.initializeMenuListener();
            controller.updatePanelColors();
        }
        return controller;
    }

    private void initializeMenuListener() {
        view.getNavigationBar().getNewGameItem().addActionListener(this);
        view.getNavigationBar().getSaveGameItem().addActionListener(this);
        view.getNavigationBar().getExitItem().addActionListener(this);
        view.getNavigationBar().getButtonSoundMenuItem().addItemListener(this);
        view.getNavigationBar().getSoundMenuItem().addItemListener(this);
        view.getNavigationBar().getRulesItem().addActionListener(this);
    }
    
    public void updatePanelColors() {
        String primaryColorStr = gameModel.getPrimaryColor();
        String secondaryColorStr = gameModel.getSecondaryColor();

        Color primaryColor = Color.decode(primaryColorStr);
        Color secondaryColor = Color.decode(secondaryColorStr);

        view.getGameBoardPanel().setColors(primaryColor, secondaryColor);
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
                //
            });
        }
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
                view.dispose();
                HomePageController.getController();
                navbarModel.exitGame();
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
