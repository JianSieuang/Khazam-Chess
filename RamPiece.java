import java.awt.Image;
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
        player = r == 6? 0: 1;
    }
    
    public int[][] moveable(GamePiece[][] board)
    {
        int[][] moves = new int[1][2];
        int r = row + (moveForward ? 1 : -1); 
        
        if(r >= 0 && r < 8)
        {   
            if(board[r][col] == null)
            {
                moves[0] = new int[] {r, col};
                return moves;
            }
        }
        return new int[0][0];
    }
    
    public int[][] capturable(GamePiece[][] board)
    {
        int[][] moves = new int[1][2];
        int r = row + (moveForward ? 1 : -1);
        
        if(r >= 0 && r < 8)
        {   
            if(board[r][col] != null && board[r][col].getPlayer() != player)
            {
                moves[0] = new int[] {r, col};
                return moves;
            }
        }
        return new int [0][0];
    }
    
    public void flipImage(int turn)
    {
        flip = turn;
    }
}
