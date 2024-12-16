import java.awt.Image;
import javax.swing.ImageIcon;

public class BizPiece extends GamePiece
{
    public BizPiece(int r, int c, String image, String image2)
    {
        int row = r;
        int col = c;
        pieceImage[0] = new ImageIcon(image).getImage();
        pieceImage[1] = new ImageIcon(image2).getImage();
        player = r == 7? 0: 1;
    }
    
    public int[][] moveable(GamePiece[][] board)
    {
        int r, c;
        int count = 0;
        int[][] moves = new int[8][2];
        int[][] value = {
          {-1,1},{-2,2}  
        };
        
        for(int i = 0; i < value.length; i++)
        {
            for(int ii = 0; ii < value[0].length; ii++)
            {
                r = row + value[0][i];
                c = col + value[0][ii];
                
                if(r >= 0 && r < 8 && c >= 0 && c < 8)
                {   
                    if(board[r][c] == null)
                    {
                        moves[count] = new int[] {r, c};
                        count++;
                    }
                }
                
                r = row + value[0][ii];
                c = col + value[0][i];
                
                if(r >= 0 && r < 8 && c >= 0 && c < 8)
                {   
                    if(board[r][c] == null)
                    {
                        moves[count] = new int[] {r, c};
                        count++;
                    }
                }
            }
        }
        
        return moves;
    }
    
    public int[][] capturable(GamePiece[][] board)
    {
        int r, c;
        int count = 0;
        int[][] moves = new int[8][2];
        int[][] value = {
          {-1,1},{-2,2}  
        };
        
        for(int i = 0; i < value.length; i++)
        {
            for(int ii = 0; ii < value[0].length; ii++)
            {
                r = row + value[0][i];
                c = col + value[0][ii];
                
                if(r >= 0 && r < 8 && c >= 0 && c < 8)
                {   
                    if(board[r][c] != null && board[r][c].getPlayer() != player)
                    {
                        moves[count] = new int[] {r, c};
                        count++;
                    }
                }
                
                r = row + value[0][ii];
                c = col + value[0][i];
                
                if(r >= 0 && r < 8 && c >= 0 && c < 8)
                {   
                    if(board[r][c] != null && board[r][c].getPlayer() != player)
                    {
                        moves[count] = new int[] {r, c};
                        count++;
                    }
                }
            }
        }
        return moves;
    }
    
    public void flipImage(int turn)
    {
        
    }

}
