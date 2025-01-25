import java.util.ArrayList;

class RamPiece extends GamePiece
{
    private boolean updated = false;
    
    public RamPiece(int r, int c, String player)
    {
        this.images = new ArrayList<String>();
        this.row = r;
        this.col = c;
        this.player = player;
        this.currentImage = 0;
        
        if(player.equals("Blue") || player.equals("Blue_Flip"))
        {
            images.add("Picture/Ram_Blue.png");
            images.add("Picture/Ram_Blue_Flip.png");
        }
        else if(player.equals("Red") || player.equals("Red_Flip"))
        {
            images.add("Picture/Ram_Red_Flip.png");
            images.add("Picture/Ram_Red.png");
        }
        
        if(player.equals("Blue_Flip") || player.equals("Red"))
        {
            this.currentImage = 1;
        }
        
        this.player = player.split("_")[0];
    }

    public int[][] moveable(GamePiece[][] board)
    {
        int r = row + (currentImage == 0? -1 : 1); 

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
        int r = row + (currentImage == 0? -1: 1);
        
        if(r >= 0 && r < 8)
        {   
            if(board[r][col] != null && !board[r][col].getPlayer().equals(player))
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
        this.currentImage = this.currentImage == 0? 1: 0;
        reachTheEndCheck();
    }
    
    private void reachTheEndCheck()
    {
        if((row == 7 || row == 0) && !updated)
        {
            this.currentImage = this.currentImage == 0? 1: 0;
            updated = true;
        }
        else if (row < 7 && row > 0)
        {
            updated = false;
        }
    }
}