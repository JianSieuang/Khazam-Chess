import javax.swing.ImageIcon;
import java.util.Arrays;

public class BizPiece extends GamePiece
{
    public BizPiece(int r, int c, String image)
    {
        this.row = r;
        this.col = c;
        pieceImage[0] = new ImageIcon(image).getImage();
        showImage = pieceImage[0];
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
                c = col + value[1][ii];
                
                if(r >= 0 && r < 8 && c >= 0 && c < 5)
                {   
                    if(board[r][c] == null)
                    {
                        moves[count++] = new int[] {r, c};
                    }
                }
                
                r = row + value[1][i];
                c = col + value[0][ii];
                
                if(r >= 0 && r < 8 && c >= 0 && c < 5)
                {   
                    if(board[r][c] == null)
                    {
                        moves[count++] = new int[] {r, c};
                    }
                }
            }
        }
        
        return Arrays.copyOf(moves, count);
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
                c = col + value[1][ii];
                
                if(r >= 0 && r < 8 && c >= 0 && c < 5)
                {   
                    if(board[r][c] != null && board[r][c].getPlayer() != player)
                    {
                        moves[count++] = new int[] {r, c};
                    }
                }
                
                r = row + value[0][i];
                c = col + value[1][ii];
                
                if(r >= 0 && r < 8 && c >= 0 && c < 5)
                {   
                    if(board[r][c] != null && board[r][c].getPlayer() != player)
                    {
                        moves[count++] = new int[] {r, c};
                    }
                }
            }
        }
        return Arrays.copyOf(moves, count);
    }
    
    public void updatePosition(int r, int c)
    {
        this.row = r;
        this.col = c;
    }
}
