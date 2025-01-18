import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class HomePageController 
{
    private HomePageView view;
    private SettingController settingController;

    public HomePageController() 
    {
        view = new HomePageView();
        settingController = new SettingController();
        AudioPlayer.playBackgroundMusic();
        initializeListeners();
    }

    private void initializeListeners() 
    {
        buttonSound(view.getHomePanel().getNewGameButton());
        view.getHomePanel().getNewGameButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                view.dispose();
                new GamePage("new");
            }
        });

        buttonSound(view.getHomePanel().getLoadGameButton());
        view.getHomePanel().getLoadGameButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                view.dispose();
                new GamePage("load");
            }
        });

        buttonSound(view.getHomePanel().getSettingsButton());
        view.getHomePanel().getSettingsButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                view.dispose();
                settingController.navigateToSettingPage(view);
            }
        });

        buttonSound(view.getHomePanel().getQuitGameButton());
        view.getHomePanel().getQuitGameButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                settingController.checkSetting();
                AudioPlayer.stopBackgroundMusic();
                System.exit(0);
            }
        });
    }

    private void buttonSound(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(button.getBackground().darker());
                button.setForeground(Color.WHITE);
                new BtnSound("hover").actionPerformed(null);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(button.getBackground().brighter());
                button.setForeground(Color.BLACK);
            }
        });

        button.addActionListener(e -> new BtnSound("click").actionPerformed(null));
    }

    public static void main(String[] args) 
    {
        new HomePageController();
    }
}
