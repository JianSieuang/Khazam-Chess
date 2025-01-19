import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;

abstract class GamePiece
{
    protected int row;
    protected int col;
    protected ArrayList<String> images;
    protected int currentImage;
    protected String player;
    
    public GamePiece(){}
    
    public Image getImage()
    {
        return new ImageIcon(images.get(currentImage)).getImage();
    }
    
    public String getPlayer()
    {
        return player;
    }
    
    public abstract String getPieceName();
    public abstract int[][] moveable(GamePiece[][] board);
    public abstract int[][] capturable(GamePiece[][] board);
    public abstract void updatePosition(int r, int c);
}