import javax.swing.ImageIcon;

public class RamPiece extends GamePiece
{
    private boolean moveForward = true;
    
    public RamPiece(int r, int c, String image, String image2)
    {
        this.row = r;
        this.col = c;
        pieceImage[0] = new ImageIcon(image).getImage();
        pieceImage[1] = new ImageIcon(image2).getImage();
        showImage = pieceImage[0];
        player = r == 6? 0: 1;
    }
    
    public int[][] moveable(GamePiece[][] board)
    {
        int r = row + (moveForward ? -1 : 1); 
        
        if(r >= 0 && r < 8)
        {   
            if(board[r][col] == null)
            {
                return new int [][] {{r, col}};
            }
        }
        return new int[0][0];
    }
    
    public int[][] capturable(GamePiece[][] board)
    {
        int r = row + (moveForward ? -1: 1);
        
        if(r >= 0 && r < 8)
        {   
            if(board[r][col] != null &&board[r][col].getPlayer() != player)
            {
                return new int [][] {{r, col}};
            }
        }
        return new int [0][0];
    }
    
    public void updatePosition(int r, int c)
    {
        this.row = r;
        this.col = c;
        showImage = showImage == pieceImage[0]? pieceImage[1]: pieceImage[0];
    }
    
    public void reachTheEnd()
    {
        
    }
}
