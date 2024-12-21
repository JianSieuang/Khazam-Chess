import java.awt.Image;

abstract class GamePiece
{
    protected int row;
    protected int col;
    protected Image[] pieceImage = new Image[2];
    protected Image showImage;
    protected int player;
    
    public GamePiece(){}
    
    public Image getImage()
    {
        return showImage;
    }
    
    public int getPlayer()
    {
        return player;
    }
    
    public abstract int[][] moveable(GamePiece[][] board);
    public abstract int[][] capturable(GamePiece[][] board);
    public abstract void updatePosition(int r, int c);
}