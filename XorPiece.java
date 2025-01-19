import java.util.Arrays;
import java.util.ArrayList;

class XorPiece extends GamePiece
{
    public XorPiece(int r, int c, String player)
    {
        this.images = new ArrayList<String>();
        this.row = r;
        this.col = c;
        this.player = player;
        this.currentImage = 0;
        
        if(player.equals("Blue"))
        {
            images.add("Picture/Xor_Blue.png");
        }
        else if(player.equals("Red"))
        {
            images.add("Picture/Xor_Red.png");
        }
    }
    
    public String getPieceName()
    {
        return "Xor_" + player;
    }


    public int[][] moveable(GamePiece[][] board)
    {
        boolean topLeft = true, topRight = true, bottomLeft = true, bottomRight = true;
        int i = 1;
        int count = 0;
        int[][] moves = new int[board[0].length + board[0].length - 2][2];
        
        while(topLeft || topRight || bottomLeft || bottomRight)
        {
            if(row - i >= 0 && col - i >= 0 && topLeft)
            {
                if(board[row-i][col-i] == null)
                {
                    moves[count++] = new int[] {row - i, col - i};
                }
                else
                {
                    topLeft = false;
                }
            }
            else
            {
                topLeft = false;
            }
            
            if(row - i >= 0 && col + i < board[0].length && topRight)
            {
                if(board[row-i][col+i] == null)
                {
                    moves[count++] = new int[] {row - i, col + i};
                }
                else
                {
                    topRight = false;
                }
            }
            else
            {
                topRight = false;
            }
            
            if(row + i < board.length && col - i >= 0 && bottomLeft)
            {
                if(board[row+i][col-i] == null)
                {
                    moves[count++] = new int[] {row + i, col - i};
                }
                else
                {
                    bottomLeft = false;
                }
            }
            else
            {
                bottomLeft = false;
            }
            
            if(row + i < board.length && col + i < board[0].length && bottomRight)
            {
                if(board[row+i][col+i] == null)
                {
                    moves[count++] = new int[] {row + i, col + i};
                }
                else
                {
                    bottomRight = false;
                }
            } 
            else
            {
                bottomRight = false;
            }
            
            i++;
        }
        
        return Arrays.copyOf(moves, count);
    }
    
    public int[][] capturable(GamePiece[][] board)
    {
        boolean topLeft = true, topRight = true, bottomLeft = true, bottomRight = true;
        int i = 1;
        int count = 0;
        int[][] moves = new int[4][2];
        
        while(topLeft || topRight || bottomLeft || bottomRight)
        {
            if(row - i >= 0 && col - i >= 0 && topLeft)
            {
                if(board[row-i][col-i] != null)
                {
                    if(!board[row-i][col-i].getPlayer().equals(player))
                    {
                        moves[count++] = new int[] {row - i, col - i};
                    }
                    topLeft = false;
                }
            }
            else
            {
                topLeft = false;
            }
            
            if(row - i >= 0 && col + i < board[0].length && topRight)
            {
                if(board[row-i][col+i] != null)
                {
                    if(!board[row-i][col+i].getPlayer().equals(player))
                    {
                        moves[count++] = new int[] {row - i, col + i};
                    }
                    topRight = false;
                }
            }
            else
            {
                topRight = false;
            }
            
            if(row + i < board.length && col - i >= 0 && bottomLeft)
            {
                if(board[row+i][col-i] != null)
                {
                    if(!board[row+i][col-i].getPlayer().equals(player))
                    {
                        moves[count++] = new int[] {row + i, col - i};
                    }
                    bottomLeft = false;
                }
            }
            else
            {
                bottomLeft = false;
            }
            
            if(row + i < board.length && col + i < board[0].length && bottomRight)
            {
                if(board[row+i][col+i] != null)
                {
                    if(!board[row+i][col+i].getPlayer().equals(player))
                    {
                        moves[count++] = new int[] {row + i, col + i};
                    }

                    bottomRight = false;
                }
            } 
            else
            {
                bottomRight = false;
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