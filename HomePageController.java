import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePageController 
{
    private HomePageView view;

    public HomePageController() 
    {
        view = new HomePageView();
        initializeListeners();
    }

    private void initializeListeners() 
    {
        view.getHomePanel().getNewGameButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                view.dispose();
                new GamePage("new");
            }
        });

        view.getHomePanel().getLoadGameButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                view.dispose();
                new GamePage("load");
            }
        });

        view.getHomePanel().getSettingsButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                view.dispose();
                SettingView.showSetting(view, new SettingController());
            }
        });

        view.getHomePanel().getQuitGameButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                AudioPlayer.stopBackgroundMusic();
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) 
    {
        new HomePageController();
    }
}
