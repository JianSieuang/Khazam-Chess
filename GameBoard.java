
public class GameBoard
 {
    private int row = 8;
    private int col = 5;
    private GamePiece[][] board;
    private int turn = 0;
    
    private GamePiece selectedPiece;
    private int selectedRow = -1;
    private int selectedCol = -1;
    
    private int[][] moveableSteps;
    private int[][] capturableSteps;
    

    public GameBoard() 
    {
        board = new GamePiece[row][col];
        initBoard();
    }
    
    private void initBoard()
    {
        board[0][0] = new XorOrTorPiece(0, 0, "Picture/Tor_Red.png", "Picture/Xor_Red.png");
        board[0][1] = new BizPiece(0, 1, "Picture/Biz_Red.png");
        board[0][2] = new SauPiece(0, 2, "Picture/Sau_Red.png", "Picture/Sau_Red_Flip.png");
        board[0][3] = new BizPiece(0, 3, "Picture/Biz_Red.png");
        board[0][4] = new XorOrTorPiece(0, 4, "Picture/Xor_Red.png", "Picture/Tor_Red.png");

        board[7][0] = new XorOrTorPiece(7, 0, "Picture/Xor_Blue.png", "Picture/Tor_Blue.png");
        board[7][1] = new BizPiece(7, 1, "Picture/Biz_Blue.png");
        board[7][2] = new SauPiece(7, 2, "Picture/Sau_Blue.png", "Picture/Sau_Blue_Flip.png");
        board[7][3] = new BizPiece(7, 3, "Picture/Biz_Blue.png");
        board[7][4] = new XorOrTorPiece(7, 4, "Picture/Tor_Blue.png", "Picture/Xor_Blue.png");
        
        for(int i = 0; i < col; i++) 
        {
            board[1][i] = new RamPiece(1, i, "Picture/Ram_Red.png", "Picture/Ram_Red_Flip.png");
            board[6][i] = new RamPiece(6, i, "Picture/Ram_Blue.png", "Picture/Ram_Blue_FLip.png");
        }
    }
    
    public void turnBoard()
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
                    board[i][ii].updatePosition(i, ii);
                }
            }
        }
        clearSelectedPieceAndSteps();
    }
    
    public void selectPiece(int r, int c)
    {
        if(r < row && r >= 0 && c < col && c >= 0)
        {
            if (selectedPiece != null && selectedRow == r && selectedCol == c) 
            {
                clearSelectedPieceAndSteps();
                return;
            }
            else if(board[r][c] != null && board[r][c].player == turn)
            {
                selectedPiece = board[r][c];
                selectedRow = r;
                selectedCol = c;
                moveableSteps = selectedPiece.moveable(board);
                capturableSteps = selectedPiece.capturable(board);
            }
        }
    }
    
    public boolean putPiece(int r, int c)
    {
        if(selectedPiece == null)
            return false;
        if(r < row && r >= 0 && c < col && c >= 0)
        {
            if(board[r][c] != null)
            {
                if(board[r][c].getPlayer() != selectedPiece.getPlayer())
                {
                    for(int i = 0; i < capturableSteps.length; i++)
                    {
                        if(capturableSteps[i][0] == r && capturableSteps[i][1] == c)
                        {
                            board[r][c] = selectedPiece;
                            board[selectedRow][selectedCol] = null;
                            turn = turn == 0 ? 1 : 0;
                            turnBoard();
                            return true;
                        }
                    }
                }
            }
            else
            {
                if(r != selectedRow || c != selectedCol)
                {
                    for(int i = 0; i < moveableSteps.length; i++)
                    {
                        if(moveableSteps[i][0] == r && moveableSteps[i][1] == c)
                        {
                            board[r][c] = selectedPiece;
                            board[selectedRow][selectedCol] = null;
                            turn = turn == 0 ? 1 : 0;
                            turnBoard();
                            return true;
                        }
                    }
                }
            }
        }
        
        board[selectedRow][selectedCol] = selectedPiece;
        return false;
    }
    
    public void clearSelectedPieceAndSteps() 
    {
        moveableSteps = new int[0][0];
        capturableSteps = new int[0][0];
        selectedPiece = null;
        selectedRow = -1;
        selectedCol = -1;
    }
    
    public GamePiece getSelectedPiece()
    {
        if(selectedPiece != null)
        {
            board[selectedRow][selectedCol] = null;
        }   
        return selectedPiece;
    }
    
    public int[][] getCapturableSteps()
    {
        return capturableSteps;
    }
    
    public int[][] getMoveableSteps()
    {
        return moveableSteps;
    }
    
    public GamePiece[][] getBoard()
    {
        return board;
    }
}