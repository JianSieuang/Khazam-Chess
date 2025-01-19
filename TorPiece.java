import java.util.Arrays;
import java.util.ArrayList;

class TorPiece extends GamePiece
{
    public TorPiece(int r, int c, String player)
    {
        this.images = new ArrayList<String>();
        this.row = r;
        this.col = c;
        this.player = player;
        this.currentImage = 0;
        
        if(player.equals("Blue"))
        {
            images.add("Picture/Tor_Blue.png");
        }
        else if(player.equals("Red"))
        {
            images.add("Picture/Tor_Red.png");
        }
    }
    
    public String getPieceName()
    {
        return "Tor_" + player;
    }

    public int[][] moveable(GamePiece[][] board)
    {
        boolean top = true, bottom = true, left = true, right = true;
        int i = 1;
        int count = 0;
        int[][] moves = new int[board.length + board[0].length - 2][2];
        
        while(top || bottom || left || right)
        {
            if(row - i >= 0 && top)
            {
                if(board[row-i][col] == null)
                {
                    moves[count++] = new int[] {row - i, col};
                }
                else
                {
                    top = false;
                }
            }
            else
            {
                top = false;
            }
            
            if(row + i < board.length && bottom)
            {
                if(board[row+i][col] == null)
                {
                    moves[count++] = new int[] {row + i, col};
                }
                else
                {
                    bottom = false;
                }
            }
            else
            {
                bottom = false;
            }
            
            if(col - i >= 0 && left)
            {
                if(board[row][col-i] == null)
                {
                    moves[count++] = new int[] {row, col - i};
                }
                else
                {
                    left = false;
                }
            }
            else
            {
                left = false;
            }
            
            if(col + i < board[0].length && right)
            {
                if(board[row][col+i] == null)
                {
                    moves[count++] = new int[] {row, col + i};
                }
                else
                {
                    right = false;
                }
            } 
            else
            {
                right = false;
            }
            
            i++;
        }
        
        return Arrays.copyOf(moves, count);
    }
    
    public int[][] capturable(GamePiece[][] board)
    {
        boolean top = true, bottom = true, left = true, right = true;
        int i = 1;
        int count = 0;
        int[][] moves = new int[4][2];
        
        while(top || bottom || left || right)
        {
            if(row - i >= 0 && top)
            {
                if(board[row-i][col] != null)
                {
                    if(!board[row-i][col].getPlayer().equals(player))
                    {
                        moves[count++] = new int[] {row - i, col};
                    }
                    top = false;
                }                
            }
            else
            {
                top = false;
            }
            
            if(row + i < board.length && bottom)
            {
                if(board[row+i][col] != null)
                {
                    if(!board[row+i][col].getPlayer().equals(player))
                    {
                        moves[count++] = new int[] {row + i, col};
                    }
                    bottom = false;
                }         
            }
            else
            {
                bottom = false;
            }
            
            if(col - i >= 0 && left)
            {
                if(board[row][col-i] != null)
                {
                    if(!board[row][col-i].getPlayer().equals(player))
                    {
                        moves[count++] = new int[] {row, col - i};
                    }
                    left = false;
                }         
            }
            else
            {
                left = false;
            }
            
            if(col + i < board[0].length && right)
            {
                if(board[row][col+i] != null)
                {
                    if(!board[row][col+i].getPlayer().equals(player))
                    {
                        moves[count++] = new int[] {row, col + i};
                    }
                    right = false;
                }         
            } 
            else
            {
                right = false;
            }
            
            i++;
        }
        
        return Arrays.copyOf(moves, count);
    }
    
    public void updatePosition(int r, int c)
    {
        this.row = r;
        this.col = c;
    }
}