import java.awt.*;

public class GameBoard
 {
    private int row = 8;
    private int col = 5;
    private GamePiece[][] board;

    public GameBoard() 
    {
        board = new GamePiece[row][col];
        initBoard();
    }
    
    private void initBoard()
    {
        board[0][0] = new XorOrTorPiece(0, 0, "Picture/Tor_Red.png", "Picture/Xor_Red.png");
        board[0][1] = new BizPiece(0, 1, "Picture/Biz_Red.png", "Picture/Biz_Red.png");
        board[0][2] = new SauPiece(0, 2, "Picture/Sau_Red.png", "Picture/Sau_Red_Flip.png");
        board[0][3] = new BizPiece(0, 3, "Picture/Biz_Red.png", "Picture/Biz_Red.png");
        board[0][4] = new XorOrTorPiece(0, 4, "Picture/Xor_Red.png", "Picture/Tor_Red.png");

        board[7][0] = new XorOrTorPiece(7, 0, "Picture/Xor_Blue.png", "Picture/Tor_Blue.png");
        board[7][1] = new BizPiece(7, 1, "Picture/Biz_Blue.png", "Picture/Biz_Blue.png");
        board[7][2] = new SauPiece(7, 2, "Picture/Sau_Blue.png", "Picture/Sau_Blue_Flip.png");
        board[7][3] = new BizPiece(7, 3, "Picture/Biz_Blue.png", "Picture/Biz_Blue.png");
        board[7][4] = new XorOrTorPiece(7, 4, "Picture/Tor_Blue.png", "Picture/Xor_Blue.png");
        
        for(int i = 0; i < col; i++) 
        {
            board[1][i] = new RamPiece(1, i, "Picture/Ram_Red.png", "Picture/Ram_Red_Flip.png");
            board[6][i] = new RamPiece(6, i, "Picture/Ram_Blue.png", "Picture/Ram_Blue_FLip.png");
        }
    }
    
    public void turnBoard(int turn)
    {
        for(int i = 0; i < row / 2; i++) 
        {
            for(int ii = 0; ii < col; ii++) 
            {
                GamePiece temp = board[i][ii];
                board[i][ii] = board[row - 1 - i][col - 1 - ii];
                board[row - 1 - i][col - 1 - ii] = temp;
            }
        }
        for(int i = 0; i < row; i++) 
        {
            for(int ii = 0; ii < col; ii++) 
            {
                if(board[i][ii] != null)
                {    
                    board[i][ii].flipImage(turn);
                }
            }
        }
    }
    
    public GamePiece[][] getBoard() {
        return board;
    }
}