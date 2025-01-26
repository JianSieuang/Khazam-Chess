import java.util.Arrays;
import java.util.ArrayList;

class TorPiece extends GamePiece
{
    public TorPiece(int r, int c, String player)
    {
        // use ArrayList to store multiple iamge string (not every piece have a same quantity of picture)
        this.images = new ArrayList<String>();
        // store position
        this.row = r;
        this.col = c;
        // store red or blue
        this.player = player;
        this.currentImage = 0;
        
        // based on player to add different image path
        if(player.equals("Blue")) {
            images.add("Picture/Tor_Blue.png");
        } else if(player.equals("Red")) {
            images.add("Picture/Tor_Red.png");
        }
    }
    
    @Override
    public int[][] moveable(GamePiece[][] board)
    {
        // four dicrection left right bottom top
        boolean top = true, bottom = true, left = true, right = true;
        int i = 1;
        // store how many possible move
        int count = 0;
        // store all the possible move
        // 4 + 7 is maximum possible move for tor piece (not include itself) so 5 col will be 4 and 8 row will be 7
        int[][] moves = new int[board.length + board[0].length - 2][2];
        
        // tor piece can move orthogonally only but can go any distance
        // check one by one starting from i = 1 to 7 the maximum
        // this is moveable step, so any direction detect have piece, the moveable step is before the piece only
        // four direction have their own possible step to reach the end of the board or the piece
        // the while loop condition is based on the four direction
        while(top || bottom || left || right)
        {
            // this is for moving top
            if(row - i >= 0 && top)
            {
                if(board[row-i][col] == null)
                {
                    // at the possible move into move
                    // why use count++? because will auto increase one after using it
                    // cannot use ++count because will aurto increase one before using it
                    moves[count++] = new int[] {row - i, col};
                }
                else
                {
                    // once detect the piece, stop showing possible move after the piece
                    top = false;
                }
            }
            else
            {
                // reach the end
                top = false;
            }
            
            // this is for moving bottom
            if(row + i < board.length && bottom)
            {
                if(board[row+i][col] == null)
                {
                    // at the possible move into move
                    // why use count++? because will auto increase one after using it
                    // cannot use ++count because will aurto increase one before using it
                    moves[count++] = new int[] {row + i, col};
                }
                else
                {
                    // once detect the piece, stop showing possible move after the piece
                    bottom = false;
                }
            }
            else
            {
                // reach the end
                bottom = false;
            }
            
            // this is for moving left
            if(col - i >= 0 && left)
            {
                if(board[row][col-i] == null)
                {
                    // at the possible move into move
                    // why use count++? because will auto increase one after using it
                    // cannot use ++count because will aurto increase one before using it
                    moves[count++] = new int[] {row, col - i};
                }
                else
                {
                    // once detect the piece, stop showing possible move after the piece
                    left = false;
                }
            }
            else
            {
                // reach the end
                left = false;
            }
            
            // this is for moving right
            if(col + i < board[0].length && right)
            {
                if(board[row][col+i] == null)
                {
                    // at the possible move into move
                    // why use count++? because will auto increase one after using it
                    // cannot use ++count because will aurto increase one before using it
                    moves[count++] = new int[] {row, col + i};
                }
                else
                {
                    // once detect the piece, stop showing possible move after the piece
                    right = false;
                }
            } 
            else
            {
                // reach the end
                right = false;
            }
            
            // increase one for checking position is valid for any direction
            i++;
        }
        
        // this arrays.copyof is used for return the possible step based on count that store how many possible move
        // why? because if you use moves, the rest will auto declare as {0,0} which make the piece can move to position (0,0)
        return Arrays.copyOf(moves, count);
    }
    
    @Override
    public int[][] capturable(GamePiece[][] board)
    {
        // four dicrection left right bottom top
        boolean top = true, bottom = true, left = true, right = true;
        int i = 1;
        // store how many possible move
        int count = 0;
        // store all the possible move
        // 4 is the maximum possible move for tor piece
        // can only capture one piece of the four direction
        int[][] moves = new int[4][2];
        
        // tor piece can move orthogonally only but can go any distance
        // check one by one starting from i = 1 to 7 the maximum
        // this is captureable step, so any direction detect have piece, it will store the position if is another side piece and stop that direction 
        // four direction have their own capturable move, maybe there is no piece in that direction
        // the while loop condition is based on the four direction
        while(top || bottom || left || right)
        {
            // this is for moving top
            if(row - i >= 0 && top)
            {
                if(board[row-i][col] != null)
                {
                    // !.getPlayer().equals(player) is make sure that is another side piece
                    if(!board[row-i][col].getPlayer().equals(player))
                    {
                        // at the possible move into move
                        // why use count++? because will auto increase one after using it
                        // cannot use ++count because will aurto increase one before using it
                        moves[count++] = new int[] {row - i, col};
                    }
                    
                    // not matter what will stop because cannot capture after the piece
                    top = false;
                }                
            }
            else
            {
                //reach the end
                top = false;
            }
            
            // this is for moving bottom
            if(row + i < board.length && bottom)
            {
                if(board[row+i][col] != null)
                {
                    // !.getPlayer().equals(player) is make sure that is another side piece
                    if(!board[row+i][col].getPlayer().equals(player))
                    {
                        // at the possible move into move
                        // why use count++? because will auto increase one after using it
                        // cannot use ++count because will aurto increase one before using it
                        moves[count++] = new int[] {row + i, col};
                    }
                    // not matter what will stop because cannot capture after the piece
                    bottom = false;
                }         
            }
            else
            {
                // reach the end
                bottom = false;
            }
            
            // this is for moving left
            if(col - i >= 0 && left)
            {
                if(board[row][col-i] != null)
                {
                    // !.getPlayer().equals(player) is make sure that is another side piece
                    if(!board[row][col-i].getPlayer().equals(player))
                    {
                        // at the possible move into move
                        // why use count++? because will auto increase one after using it
                        // cannot use ++count because will aurto increase one before using it
                        moves[count++] = new int[] {row, col - i};
                    }
                    // not matter what will stop because cannot capture after the piece
                    left = false;
                }         
            }
            else
            {
                // reach the end
                left = false;
            }
            
            // this is for moving right
            if(col + i < board[0].length && right)
            {
                if(board[row][col+i] != null)
                {
                    // !.getPlayer().equals(player) is make sure that is another side piece
                    if(!board[row][col+i].getPlayer().equals(player))
                    {
                        // at the possible move into move
                        // why use count++? because will auto increase one after using it
                        // cannot use ++count because will aurto increase one before using it
                        moves[count++] = new int[] {row, col + i};
                    }
                    // not matter what will stop because cannot capture after the piece
                    right = false;
                }         
            } 
            else
            {
                // reach the end
                right = false;
            }
            
            // increase one for checking position is valid for any direction
            i++;
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
    }
}