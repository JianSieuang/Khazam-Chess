import java.util.ArrayList;

class RamPiece extends GamePiece
{
    private boolean moveForward = true;
    private boolean updated = false;
    
    public RamPiece(int r, int c, String player)
    {
        this.images = new ArrayList<String>();
        this.row = r;
        this.col = c;
        this.player = player;
        this.currentImage = 0;
        
        if(player == "Blue")
        {
            images.add("Picture/Ram_Blue.png");
            images.add("Picture/Ram_Blue_Flip.png");
        }
        else if(player == "Red")
        {
            images.add("Picture/Ram_Red.png");
            images.add("Picture/Ram_Red_Flip.png");
        }
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
        this.currentImage = this.currentImage == 0? 1: 0;
        reachTheEndCheck();
    }
    
    private void reachTheEndCheck()
    {
        if((row == 7 || row == 0) && !updated)
        {
            moveForward = !moveForward;
            this.currentImage = this.currentImage == 0? 1: 0;
            updated = true;
        }
        else if (row < 7 && row > 0)
        {
            updated = false;
        }
    }
}