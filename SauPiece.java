import java.util.Arrays;
import java.util.ArrayList;

class SauPiece extends GamePiece
{
    public SauPiece(int r, int c, String player)
    {
        this.images = new ArrayList<String>();
        this.row = r;
        this.col = c;
        this.player = player;
        this.currentImage = 0;
        
        if(player.equals("Blue") || player.equals("Blue_Flip"))
        {
            images.add("Picture/Sau_Blue.png");
            images.add("Picture/Sau_Blue_Flip.png");
        }
        else if(player.equals("Red") || player.equals("Red_Flip"))
        {
            images.add("Picture/Sau_Red.png");
            images.add("Picture/Sau_Red_Flip.png");
        }
        
        if(player.equals("Blue_Flip") || player.equals("Red_Flip"))
        {
            this.player = player.split("_")[0];
            this.currentImage = 1;
        }
    }
    
    public String getPieceName()
    {
        return "Sau_" + player + (currentImage == 0? "": "_Flip");
    }

    public int[][] moveable(GamePiece[][] board)
    {
        int r, c;
        int count = 0;
        int[][] moves = new int[8][2];
        
        for(int i = -1; i < 2; i++)
        {
            for(int ii = -1; ii < 2; ii++)
            {
                if(i == 0 && ii == 0) continue;
                r = row + i;
                c = col + ii;
                
                if(r >= 0 && r < 8 && c >= 0 && c < 8)
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
        
        for(int i = -1; i < 2; i++)
        {
            for(int ii = -1; ii < 2; ii++)
            {
                if(i == 0 && ii == 0) continue;
                r = row + i;
                c = col + ii;
                
                if(r >= 0 && r < 8 && c >= 0 && c < 8)
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
        this.currentImage = this.currentImage == 0? 1: 0;
    }
    
}