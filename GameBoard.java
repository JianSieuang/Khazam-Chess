import java.io.*;
import java.util.Scanner;
import java.util.Arrays;

// Chong Jian Sieuang
/*
 * Model, manage the game board
 * handles game initialization, piece movement, saving/loading game state and determining the winner.
 */
public class GameBoard {
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

    public GameBoard() {
        // initialize the board
        board = new GamePiece[row][col];
    }

    public void getGameType(String gameType) {
        // differentiate the is it load game or new game
        if (gameType.equals("New Game")) {
            newGame();
        } else {
            loadGame();
        }
    }

    public void newGame() {
        // erase the old board data
        for (GamePiece[] row : board) {
            Arrays.fill(row, null);
        }
        // call this function to reset the board and game status
        initBoard();
        // load the board color
        loadBoardColor();
    }

    private void initBoard() {
        // these are initialize data
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
        
        // using for loop to create rma piece
        for (int i = 0; i < col; i++) {
            board[1][i] = new RamPiece(1, i, "Red");
            board[6][i] = new RamPiece(6, i, "Blue");
        }
    }

    public void saveGame() {
        // save the game
        try (PrintWriter writer = new PrintWriter("game.txt")) {
            // save turn and move
            writer.println("Turn: " + turn);
            writer.println("Move: " + move);
            // enter a space
            writer.println();
            
            // show how to read the board in txt file
            writer.println("Explaination: ");
            writer.println("The '.' represents an empty space on the board, indicating no piece is present in that position");
            writer.println("Each name consists of two parts: the type of piece and the team color");
            writer.println("The '_Flip' indicating a flipped state of the piece");
            // enter a space
            writer.println();
            
            // the board :)
            writer.println("Board:");
            // save all the board details
            for (GamePiece[] row : board) {
                for (GamePiece piece : row) {
                    if (piece != null) {
                        // if have piece, get the name and save it, will save like "Ram_Red"
                        writer.print(piece.getPieceName() + " ");
                    } else {
                        // if does not have piece will save a dot "." to represent null
                        writer.print(". ");
                    }
                }
                // this is for next line based on the board
                writer.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadGame() {
        // load the game
        try (Scanner scanner = new Scanner(new File("game.txt"))) {
            // scan the first line
            String turnLine = scanner.nextLine();
            // turnLine will get "Turn: 1" for exmaple
            // using split(": ") to split Turn and 1 then [1] to take second value
            // Integer.parseInt is change the datatype to int for the second value 
            turn = Integer.parseInt(turnLine.split(": ")[1]);
            
            // scan the second line
            String moveLine = scanner.nextLine();
            // turnLine will get "Move: Blue" for exmaple
            // using split(": ") to split Move and Blue then [1] to take second value
            this.move = moveLine.split(": ")[1];

            // skip the Explaination part 
            while (scanner.hasNextLine()) {
                // the scanner will read until board: exist
                if(scanner.nextLine().startsWith("Board:")) break;
            }

            // the content of the board
            for (int i = 0; i < row; i++) {
                // split but each piece including the dot
                String[] line = scanner.nextLine().split(" ");
                for (int j = 0; j < col; j++) {
                    String piece = line[j];
                    if (!piece.equals(".")) {
                        // if is not a dot, means is a piece
                        String[] parts = piece.split("_", 2);
                        // split into two parts: piece type and the player
                        String type = parts[0];
                        String player = parts[1];

                        // check the type
                        switch (type) {
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
                    } else {
                        // if is a dot, declare the position as null
                        board[i][j] = null;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // load the board color
        loadBoardColor();
    }

    private void loadBoardColor() {
        try (BufferedReader reader = new BufferedReader(new FileReader("setting.txt"))) {
            String line;
            // reader read the line one by one until is null
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Primary Color")) {
                    // if the string starts with primary color, then take the value using split
                    primaryColor = line.split(" : ")[1];
                      
                } else if (line.startsWith("Secondary Color")) {
                    // if the string starts with secondary color, then take the value using split
                    secondaryColor = line.split(" : ")[1];
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading settings file: " + e.getMessage());
        }
    }

    // get the primary color, main reason is for view can get the color
    public String getPrimaryColor() {
        // if no primary color then give a default color
        return primaryColor != null ? primaryColor : "#FFFFFF";
    }

    // get the secondary color, main reason is for view can get the color
    public String getSecondaryColor() {
        // if no secondary color then give a default color
        return secondaryColor != null ? secondaryColor : "#000000";
    }

    // turn the board, flip the board
    private void turnBoard() {
        if (winner == null) {
            // flip the board is like change the piece left to right and up to down
            // while simplify it become swap the piece at 0,0 to 7,4 , 1,0 to 6,4
            // in that case can write like i swap with row - 1 - i, ii swap with col - 1 - ii
            // and fliping it just half times of the total of the piece
            // can use row/2 or col/2 in the loop
            for (int i = 0; i < row / 2; i++) {
                for (int ii = 0; ii < col; ii++) {
                    GamePiece temp = board[i][ii];
                    board[i][ii] = board[row - 1 - i][col - 1 - ii];
                    board[row - 1 - i][col - 1 - ii] = temp;
                }
            }
            // update the position
            for (int i = 0; i < row; i++) {
                for (int ii = 0; ii < col; ii++) {
                    if (board[i][ii] != null) {
                        // let every piece update their position every time the board turn around
                        board[i][ii].updatePosition(i, ii);
                    }
                }
            }
        }

        // after turn the board, clear the selected piece
        clearSelectedPieceAndSteps();
    }

    private void clearSelectedPieceAndSteps() {
        // clear the steps and position of the seleceted piece
        moveableSteps = new int[0][0];
        capturableSteps = new int[0][0];
        selectedPiece = null;
        selectedRow = -1;
        selectedCol = -1;
    }

    private void countMove() {
        // change the move to the next player
        move = move.equals("Blue") ? "Red" : "Blue";
        // if the blue player plays agian, the turn + 1, consider as one round
        turn = move.equals("Blue") ? turn + 1 : turn;

        // while count the move, will do the checking for the tor and xor piece 
        // every 2 turns, will change the form
        if (turn % 2 == 0 && move.equals("Blue")) {
            for (int i = 0; i < row; i++) {
                for (int ii = 0; ii < col; ii++) {
                    if (board[i][ii] != null) {
                        // if is TorPiece, change to XorPiece while is XorPiece will change to TorPiece
                        // just to replace it only :)
                        if (board[i][ii] instanceof TorPiece) {
                            board[i][ii] = new XorPiece(i, ii, board[i][ii].getPlayer());
                        } else if (board[i][ii] instanceof XorPiece) {
                            board[i][ii] = new TorPiece(i, ii, board[i][ii].getPlayer());
                        }
                    }
                }
            }
        }
    }

    public void selectPiece(int r, int c) {
        if (winner != null)
            return;
        // check the r and c is in the board
        if (r < row && r >= 0 && c < col && c >= 0) {
            // if user click the same piece, consider as unselect the piece
            if (selectedPiece != null && selectedRow == r && selectedCol == c) {
                clearSelectedPieceAndSteps();
                return;
            } else if (board[r][c] != null && board[r][c].player.equals(move)) {
                // store the info of the selected piece
                selectedPiece = board[r][c];
                selectedRow = r;
                selectedCol = c;
                // get the moveable and capturable steps
                moveableSteps = selectedPiece.moveable(board);
                capturableSteps = selectedPiece.capturable(board);
            }
        }

    }

    public boolean putPiece(int r, int c) {
        // without selected piece, return false
        if (selectedPiece == null)
            return false;
            
         // check the r and c is in the board 
        if (r < row && r >= 0 && c < col && c >= 0) {
            // if the user click other pieces
            if (board[r][c] != null) {
                // check is different player piece or not
                if (board[r][c].getPlayer() != selectedPiece.getPlayer()) {
                    for (int i = 0; i < capturableSteps.length; i++) {
                        // check all capturable step to determine is valid or not
                        if (capturableSteps[i][0] == r && capturableSteps[i][1] == c) {
                            // if the captured piece is SauPiece
                            if (board[r][c] instanceof SauPiece) {
                                // save the winner
                                winner = selectedPiece.getPlayer();
                                
                                // delete the file because the game is end
                                File file = new File("game.txt");
                                if (file.exists()) {
                                    file.delete();
                                }
                            }
                            //replace the piece with selected piece
                            board[r][c] = selectedPiece;
                            // remove the selected piece from original place
                            board[selectedRow][selectedCol] = null;
                            // count the move and turn the board
                            countMove();
                            turnBoard();
                            return true;
                        }
                    }
                }
            } else {
                // if user click empty space
                if (r != selectedRow || c != selectedCol) {
                    for (int i = 0; i < moveableSteps.length; i++) {
                        // check all moveavle step to determine is valid or not
                        if (moveableSteps[i][0] == r && moveableSteps[i][1] == c) {
                            //replace the piece with selected piece
                            board[r][c] = selectedPiece;
                            // remove the selected piece from original place
                            board[selectedRow][selectedCol] = null;
                            // count the move and turn the board
                            countMove();
                            turnBoard();
                            return true;
                        }
                    }
                }
            }
        }

        //put the piece back to board
        board[selectedRow][selectedCol] = selectedPiece;
        return false;
    }

    // all Encapsulation below
    public String getWinner() {
        return winner;
    }

    public String getMove() {
        return move;
    }

    public int getTurn() {
        return turn;
    }

    public GamePiece getSelectedPiece() {
        if (selectedPiece != null) {
            board[selectedRow][selectedCol] = null;
        }
        return selectedPiece;
    }

    public int[][] getCapturableSteps() {
        return capturableSteps;
    }

    public int[][] getMoveableSteps() {
        return moveableSteps;
    }

    public GamePiece[][] getBoard() {
        return board;
    }
}