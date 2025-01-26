import java.util.Arrays;
import java.util.ArrayList;

class SauPiece extends GamePiece
{
    public SauPiece(int r, int c, String player)
    {
        // use ArrayList to store multiple iamge string (not every piece have a same quantity of picture)
        this.images = new ArrayList<String>();
        // store position
        this.row = r;
        this.col = c;
        // store red or blue
        // but because of ram piece have two picture so player might get red_flip or blue_flip
        // so use split to "blue" and "flip" and [0] to get the first value, which is "blue" or "red"
        this.player = player.split("_")[0];
        this.currentImage = 0;
        
        // based on player to add different image path        
        if(player.equals("Blue") || player.equals("Blue_Flip"))
        {
            // because sau piece is not Symmetry, so we save two pictures:
            // one is store for blue side view
            // one is store for red side view
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
            // if the board turn, so the currentImage will be one, use the second image
            this.currentImage = 1;
        }
    }

    @Override
    public int[][] moveable(GamePiece[][] board)
    {
        int r, c;
        // store how many possible move
        int count = 0;
        // store all the possible move
        // 8 is the maximum possible move for sau piece
        int[][] moves = new int[8][2];
        
        // sau piece can move only one step in any direction.
        // so have 3 row and 3 col
        // why starting at -1? because you moving at row is left right or not moving(0).
        // same thing in col, up, down or not moving (0)
        // with is combination can show that is moving left, right, or maybe Top left 
        for(int i = -1; i < 2; i++)
        {
            for(int ii = -1; ii < 2; ii++)
            {
                // if i and ii is 0, this is the current sau piece place
                // so skip this by using continue
                if(i == 0 && ii == 0) continue;
                r = row + i;
                c = col + ii;
                
                // check is in the board or not
                if(r >= 0 && r < 8 && c >= 0 && c < 8)
                {   
                    // this is moveable, so only for empty space then store the position that can move
                    if(board[r][c] == null)
                    {
                        // at the possible move into move
                        // why use count++? because will auto increase one after using it
                        // cannot use ++count because will aurto increase one before using it
                        moves[count++] = new int[] {r, c};
                    }
                }
            }
        }
        
        // this arrays.copyof is used for return the possible step based on count that store how many possible move
        // why? because if you use moves, the rest will auto declare as {0,0} which make the piece can move to position (0,0)
        return Arrays.copyOf(moves, count);
    }

    @Override
    public int[][] capturable(GamePiece[][] board)
    {
        int r, c;
        // store how many possible move
        int count = 0;
        // store all the possible move
        // 8 is the maximum possible move for sau piece
        int[][] moves = new int[8][2];
        
        // sau piece can move only one step in any direction.
        // so have 3 row and 3 col
        // why starting at -1? because you moving at row is left right or not moving(0).
        // same thing in col, up, down or not moving (0)
        // with is combination can show that is moving left, right, or maybe Top left 
        for(int i = -1; i < 2; i++)
        {
            for(int ii = -1; ii < 2; ii++)
            {
                // if i and ii is 0, this is the current sau piece place
                if(i == 0 && ii == 0) continue;
                r = row + i;
                c = col + ii;
                
                // check is in the board or not
                if(r >= 0 && r < 8 && c >= 0 && c < 8)
                {   
                    // this is capturable, so need to make sure that have piece and is another side piece
                    // != null is make sure that have a piece
                    // !.getPlayer().equals(player) is make sure that is another side piece
                    if(board[r][c] != null && !board[r][c].getPlayer().equals(player))
                    {
                        // at the possible move into move
                        // why use count++? because will auto increase one after using it
                        // cannot use ++count because will aurto increase one before using it
                        moves[count++] = new int[] {r, c};
                    }
                }
            }
        }
        
        // this arrays.copyof is used for return the possible step based on count that store how many possible move
        // why? because if you use moves, the rest will auto declare as {0,0} which make the piece can move to position (0,0)
        return Arrays.copyOf(moves, count);
    }
    
    // update current position
    public void updatePosition(int r, int c)
    {
        this.row = r;
        this.col = c;
        // change the image
        this.currentImage = this.currentImage == 0? 1: 0;
    }
}