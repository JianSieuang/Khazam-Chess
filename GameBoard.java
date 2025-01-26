import java.io.*;
import java.util.Scanner;
import java.util.Arrays;

public class GameBoard
{
    // store gameboard size
    private int row = 8;
    private int col = 5;
    private GamePiece[][] board;
    // store turn and move
    private static int turn;
    private String move;
    // store winner
    private String winner;
    // store selected piece while click or drag
    private GamePiece selectedPiece;
    private int selectedRow = -1;
    private int selectedCol = -1;
    
    // show the moveable and capturable steps for selected piece
    private int[][] moveableSteps;
    private int[][] capturableSteps;
    
    // store board color
    private String primaryColor;
    private String secondaryColor;

    public GameBoard() 
    {
        board = new GamePiece[row][col];
        newGame();
    }
    
    public void newGame()
    {
        for (GamePiece[] row : board) {
            Arrays.fill(row, null); 
        }
        initBoard();
        loadBoardColor();
    }
    
    private void initBoard()
    {
        move = "Blue";
        turn = 0;
        board[0][0] = new TorPiece(0, 0, "Red");
        board[0][1] = new BizPiece(0, 1, "Red");
        board[0][2] = new SauPiece(0, 2, "Red");
        board[0][3] = new BizPiece(0, 3, "Red");
        board[0][4] = new XorPiece(0, 4, "Red");
        
        board[7][0] = new XorPiece(7, 0, "Blue");
        board[7][1] = new BizPiece(7, 1, "Blue");
        board[7][2] = new SauPiece(7, 2, "Blue");
        board[7][3] = new BizPiece(7, 3, "Blue");
        board[7][4] = new TorPiece(7, 4, "Blue");
        
        for(int i = 0; i < col; i++) 
        {
            board[1][i] = new RamPiece(1, i, "Red");
            board[6][i] = new RamPiece(6, i, "Blue");
        }
    }

    public void saveGame()
    {
        try (PrintWriter writer = new PrintWriter("game.txt")) 
        {
            writer.println("Turn: " + turn);
            writer.println("Move: " + move);
            writer.println();
    
            writer.println("Board:");
            for (GamePiece[] row : board) 
            {
                for (GamePiece piece : row) 
                {
                    if (piece != null) 
                    {
                        writer.print(piece.getPieceName() + " ");
                    } 
                    else 
                    {
                        writer.print(". ");
                    }
                }
                writer.println();
            }
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
    
    public void loadGame() 
    {
        try (Scanner scanner = new Scanner(new File("game.txt"))) 
        {
            String turnLine = scanner.nextLine();
            this.turn = Integer.parseInt(turnLine.split(": ")[1]);
    
            String moveLine = scanner.nextLine();
            this.move = moveLine.split(": ")[1];
    
            if (scanner.hasNextLine()) 
            {
                scanner.nextLine();
            }
    
            if (scanner.hasNextLine()) 
            {
                scanner.nextLine();
            }
    
            for (int i = 0; i < row; i++) 
            {
                String[] line = scanner.nextLine().split(" ");
                for (int j = 0; j < col; j++) 
                {
                    String piece = line[j];
                    if (!piece.equals(".")) 
                    {
                        String[] parts = piece.split("_", 2);
                        String type = parts[0];
                        String player = parts[1];

                        switch (type) 
                        {
                            case "Tor":
                                board[i][j] = new TorPiece(i, j, player);
                                break;
                            case "Xor":
                                board[i][j] = new XorPiece(i, j, player);
                                break;
                            case "Biz":
                                board[i][j] = new BizPiece(i, j, player);
                                break;
                            case "Sau":
                                board[i][j] = new SauPiece(i, j, player);
                                break;
                            case "Ram":
                                board[i][j] = new RamPiece(i, j, player);
                                break;
                            default:
                                throw new IllegalArgumentException("Unknown piece type: " + type);
                        }
                    } 
                    else 
                    {
                        board[i][j] = null;
                    }
                }
            }
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
        loadBoardColor();
    }
    
    private void loadBoardColor()
    {
        try (BufferedReader reader = new BufferedReader(new FileReader("setting.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Primary Color")) {
                    primaryColor = line.split(" : ")[1];
                } else if (line.startsWith("Secondary Color")) {
                    secondaryColor = line.split(" : ")[1];
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading settings file: " + e.getMessage());
        }
    }
    
    public String getPrimaryColor() {
        return primaryColor != null ? primaryColor : "#FFFFFF";
    }
    
    public String getSecondaryColor() {
        return secondaryColor != null ? secondaryColor : "#000000";
    }
        
    private void turnBoard()
    {
        if(winner == null)
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
        }
        
        clearSelectedPieceAndSteps();
    }
    
    private void clearSelectedPieceAndSteps() 
    {
        moveableSteps = new int[0][0];
        capturableSteps = new int[0][0];
        selectedPiece = null;
        selectedRow = -1;
        selectedCol = -1;
    }
    
    private void countMove()
    {
        move = move.equals("Blue")? "Red" : "Blue";
        turn = move.equals("Blue")? turn + 1: turn;
        
        if(turn % 2 == 0 &&  move.equals("Blue"))
        {
            for(int i = 0; i < row; i++) 
            {
                for(int ii = 0; ii < col; ii++) 
                {
                    if(board[i][ii] != null)
                    {
                        if(board[i][ii] instanceof TorPiece)
                        {
                            board[i][ii] = new XorPiece(i, ii, board[i][ii].getPlayer());
                        }
                        else if(board[i][ii] instanceof XorPiece)
                        {
                            board[i][ii] = new TorPiece(i, ii, board[i][ii].getPlayer());
                        }
                    }
                }
            }
        }
    }
    
    public void selectPiece(int r, int c)
    {
        if(winner != null) return;
        if(r < row && r >= 0 && c < col && c >= 0)
        {
            if (selectedPiece != null && selectedRow == r && selectedCol == c) 
            {
                clearSelectedPieceAndSteps();
                return;
            }
            else if(board[r][c] != null && board[r][c].player.equals(move))
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
                            if (board[r][c] instanceof SauPiece)
                            {
                                winner = selectedPiece.getPlayer();

                                File file = new File("game.txt");

                                if (file.exists()) {
                                    file.delete();
                                }
                            }
                            board[r][c] = selectedPiece;
                            board[selectedRow][selectedCol] = null;
                            countMove();
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
                            countMove();
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
    
    public String getWinner() {
        return winner;
    }
    
    public String getMove() {
        return move;
    }
    
    public int getTurn() {
        return turn;
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