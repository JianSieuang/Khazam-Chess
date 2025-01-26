import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;

// Chong Jian Sieuang
/*
 * Abstract class, represent the game piece
 * store the position, player, and image of the piece
 * provide the method to get the image, player, and piece name
 * provide the abstract method for move and capture for different piece
 */
abstract class GamePiece {
    protected int row;
    protected int col;
    protected ArrayList<String> images;
    protected int currentImage;
    protected String player;

    public GamePiece() {
    }

    // get current image
    // because some of the piece have flip image
    public Image getImage() {
        return new ImageIcon(images.get(currentImage % images.size())).getImage();
    }

    // return "Red" or "Blue"
    public String getPlayer() {
        return player;
    }

    // return piece name. For example Tor_Blue, or Ram_Blue_Flip (this is for turn
    // board)
    public String getPieceName() {
        return images.get(currentImage).split("/")[1].split(".png")[0];
    }

    // for Polymorphism, because different piece have different move and capture
    public abstract int[][] moveable(GamePiece[][] board);

    public abstract int[][] capturable(GamePiece[][] board);

    public abstract void updatePosition(int r, int c);
}