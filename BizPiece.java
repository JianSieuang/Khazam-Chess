import java.util.Arrays;
import java.util.ArrayList;

class BizPiece extends GamePiece
{
    public BizPiece(int r, int c, String player)
    {
        this.images = new ArrayList<String>();
        this.row = r;
        this.col = c;
        this.player = player;
        this.currentImage = 0;
        
        if(player.equals("Blue"))
        {
            images.add("Picture/Biz_Blue.png");
        }
        else if(player.equals("Red"))
        {
            images.add("Picture/Biz_Red.png");
        }
    }
    
    public String getPieceName()
    {
        return "Biz_" + player;
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
                    if(board[r][c] != null && !board[r][c].getPlayer().equals(player))
                    {
                        moves[count++] = new int[] {r, c};
                    }
                }
                
                r = row + value[1][i];
                c = col + value[0][ii];
                
                if(r >= 0 && r < 8 && c >= 0 && c < 5)
                {   
                    if(board[r][c] != null && !board[r][c].getPlayer().equals(player))
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