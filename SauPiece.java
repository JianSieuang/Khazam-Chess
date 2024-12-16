import javax.swing.ImageIcon;

public class SauPiece extends GamePiece
{
    public SauPiece(int r, int c, String image, String image2)
    {
        this.row = r;
        this.col = c;
        pieceImage[0] = new ImageIcon(image).getImage();
        pieceImage[1] = new ImageIcon(image2).getImage();
        player = r == 7? 0: 1;
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
        
        for(int i = -1; i < 2; i++)
        {
            for(int ii = -1; ii < 2; ii++)
            {
                if(i == 0 && ii == 0) continue;
                r = row + i;
                c = col + ii;
                
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
        flip = turn;
    }
}
