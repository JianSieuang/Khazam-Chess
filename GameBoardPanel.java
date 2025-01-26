import javax.swing.*;
import java.awt.*;

// Chong Jian Sieuang
/*
 * View,displaying the game board.
 * handles visual representation of the board, pieces, and possible moves.
 */
public class GameBoardPanel extends JPanel {
    private GamePiece[][] board;
    private int cellSize;
    private int offsetX;
    private int offsetY;
    private int margin;
    private int imageSizeX;
    private int imageSizeY;

    private GamePiece selectedPiece;
    private boolean isDragging = false;
    private int dragX;
    private int dragY;

    private int[][] moveableSteps;
    private int[][] capturableSteps;

    private Color primaryColor;
    private Color secondaryColor;

    public GameBoardPanel(GamePiece[][] board) {
        this.board = board;
    }

    public void updateDimensions(int cellSize, int offsetX, int offsetY) {
        // get the cell size of each piece place
        this.cellSize = cellSize;
        // put in the middle of the panel
        this.offsetX = offsetX;
        this.offsetY = offsetY;

        // put some margin for the picture
        margin = cellSize / 10;
        imageSizeX = cellSize - (2 * margin);
        imageSizeY = cellSize - (2 * margin);
    }

    public void setPosibleMove(GamePiece p, int[][] m, int[][] c) {
        // get selected piece move and captureable move
        this.selectedPiece = p;
        this.moveableSteps = m;
        this.capturableSteps = c;
        // repaint the board to show all the move
        repaint();
    }

    public void setDraggedPiece(int x, int y) {
        // will keep refreash while the piece are draging
        this.isDragging = true;
        this.dragX = x;
        this.dragY = y;
        repaint();
    }

    public void setColors(Color primary, Color secondary) {
        // set color of the game board
        this.primaryColor = primary;
        this.secondaryColor = secondary;
        repaint();
    }

    public void clear(boolean isMoved) {
        // clear the selected piece and its all possible move if the selected piece is moved
        this.isDragging = false;
        if (isMoved) {
            this.selectedPiece = null;
            this.moveableSteps = new int[0][0];
            this.capturableSteps = new int[0][0];
        }
        repaint();
    }

    public void showWinnerDialog(String winner) {
        // show the dialog while having a winner
        JOptionPane.showMessageDialog(this, "Winner: " + winner, "Game Over", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                // position x and y is based on offset and cellsize
                int positionX = offsetX + col * cellSize;
                int positionY = offsetY + row * cellSize;

                // paint the game board
                if ((row + col) % 2 == 0) {
                    // set the primary color
                    g.setColor(primaryColor != null ? primaryColor : Color.LIGHT_GRAY);
                } else {
                    // set the secondary color
                    g.setColor(secondaryColor != null ? secondaryColor : Color.DARK_GRAY);
                }
                // draw a solid rectangle for each cell
                g.fillRect(positionX, positionY, cellSize, cellSize);

                // draw the outside line of the rectangle
                g.setColor(Color.BLACK);
                g.drawRect(positionX, positionY, cellSize, cellSize);

                // show all the pieces
                if (board[row][col] != null) {
                    g.drawImage(board[row][col].getImage(), positionX + margin, positionY + margin, imageSizeX,
                            imageSizeY, this);
                }
            }
        }

        // show all the moveable steps
        if (moveableSteps != null && selectedPiece != null) {
            for (int i = 0; i < moveableSteps.length; i++) {
                // shown like a small dot
                int size = cellSize / 4;
                int positionX = offsetX + moveableSteps[i][1] * cellSize + (cellSize - size) / 2;
                int positionY = offsetY + moveableSteps[i][0] * cellSize + (cellSize - size) / 2;

                if ((moveableSteps[i][1] + moveableSteps[i][0]) % 2 == 0) {
                    // set the complementary colors based on primary color
                    g.setColor(primaryColor != null ? new Color(255 - primaryColor.getRed(), 255 - primaryColor.getGreen(), 255 - primaryColor.getBlue()) : Color.DARK_GRAY);
                } else {
                    // set the complementary colors based on secondary color
                    g.setColor(secondaryColor != null ? new Color(255 - secondaryColor.getRed(), 255 - secondaryColor.getGreen(), 255 - secondaryColor.getBlue()) : Color.LIGHT_GRAY);
                }

                g.fillArc(positionX, positionY, size, size, 0, 360);
            }
        }

        // show all the capturable steps
        if (capturableSteps != null && selectedPiece != null) {
            for (int i = 0; i < capturableSteps.length; i++) {
                // shown like a circle the pieces
                int positionX = offsetX + capturableSteps[i][1] * cellSize;
                int positionY = offsetY + capturableSteps[i][0] * cellSize;

                if ((capturableSteps[i][1] + capturableSteps[i][0]) % 2 == 0) {
                    // set the complementary colors based on primary color
                    g.setColor(primaryColor != null ? new Color(255 - primaryColor.getRed(), 255 - primaryColor.getGreen(), 255 - primaryColor.getBlue()) : Color.DARK_GRAY);
                } else {
                    // set the complementary colors based on secondary color
                    g.setColor(secondaryColor != null ? new Color(255 - secondaryColor.getRed(), 255 - secondaryColor.getGreen(), 255 - secondaryColor.getBlue()) : Color.LIGHT_GRAY);
                }

                g.drawArc(positionX, positionY, cellSize, cellSize, 0, 360);
            }
        }

        if (selectedPiece != null && isDragging) {
            // shown like you drag the piece
            g.drawImage(selectedPiece.getImage(), dragX - imageSizeX / 2, dragY - imageSizeY / 2, imageSizeX,
                    imageSizeY, this);
        }
    }
}
