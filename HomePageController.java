import java.awt.event.*;

public class HomePageController
{
    private HomePageView view;

    
    public HomePageController()
    {
        view = new HomePageView();
    }
        
    public static void main(String[] args) 
    {
        new HomePageController();
    }
}
