import java.awt.Image;

abstract class GamePiece
{
    protected int row;
    protected int col;
    protected Image[] pieceImage = new Image[2];
    protected int flip = 0;
    protected int player;
    
    public GamePiece()
    {
    }
    
    public Image getImage()
    {
        return pieceImage[flip];
    }
    
    public int getPlayer()
    {
        return player;
    }
    
    public abstract void flipImage(int turn);
}