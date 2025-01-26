import java.util.ArrayList;

// Chong Jian Sieuang
/*
 * Model, Ram piece can move forward or backward only (depending on its current image state).
 * it can also flip its orientation when it reaches the end of the board.
 */
class RamPiece extends GamePiece {
    public RamPiece(int r, int c, String player) {
        // use ArrayList to store multiple iamge string (not every piece have a same
        // quantity of picture)
        this.images = new ArrayList<String>();
        // store position
        this.row = r;
        this.col = c;
        // store red or blue
        // but because of ram piece have two picture so player might get red_flip or
        // blue_flip
        // so use split to "blue" and "flip" and [0] to get the first value, which is
        // "blue" or "red"
        this.player = player.split("_")[0];
        this.currentImage = 0;

        // based on player to add different image path
        if (player.equals("Blue") || player.equals("Blue_Flip")) {
            // because Ram piece is not Symmetry, so we save two pictures:
            // one is store for blue side view
            // one is store for red side view
            images.add("Picture/Ram_Blue.png");
            images.add("Picture/Ram_Blue_Flip.png");
        } else if (player.equals("Red") || player.equals("Red_Flip")) {
            images.add("Picture/Ram_Red_Flip.png");
            images.add("Picture/Ram_Red.png");
        }

        if (player.equals("Blue_Flip") || player.equals("Red")) {
            // if the board turn, so the currentImage will be one, use the second image
            this.currentImage = 1;
        }
    }

    @Override
    public int[][] moveable(GamePiece[][] board) {
        // ram piece move forward or backward only, so using current image to identify
        // it
        // for example, starting the blue side move with name blue the current image is
        // 0, so is move forward
        // while red side moving, is turning the board and the current image will change
        // but in constructer i set red and blue flip as 1, so will become 0 after
        // changing the image :)
        int r = row + (currentImage == 0 ? -1 : 1);

        // check the r is in the board range
        if (r >= 0 && r < 8) {
            // this is moveable, so only for empty space then return the position that can
            // move
            if (board[r][col] == null) {
                return new int[][] { { r, col } };
            }
        }

        // return a empty 2d array says that dont have any moveable step
        return new int[0][0];
    }

    @Override
    public int[][] capturable(GamePiece[][] board) {
        // same reason for geting is moving forward or backward
        int r = row + (currentImage == 0 ? -1 : 1);

        // check the r is in the board range
        if (r >= 0 && r < 8) {
            // this is capturable, so need to make sure that have piece and is another side
            // piece
            // != null is make sure that have a piece
            // !.getPlayer().equals(player) is make sure that is another side piece
            if (board[r][col] != null && !board[r][col].getPlayer().equals(player)) {
                return new int[][] { { r, col } };
            }
        }

        // return a empty 2d array says that dont have any capturable step
        return new int[0][0];
    }

    // update current position
    public void updatePosition(int r, int c) {
        this.row = r;
        this.col = c;
        // change the image
        this.currentImage = this.currentImage == 0 ? 1 : 0;
    }
}