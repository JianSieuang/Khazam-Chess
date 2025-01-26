import java.util.Arrays;
import java.util.ArrayList;

// Chong Jian Sieuang
/* 
 * Model, this piece move in 'L’ shape, similiar to be a knight in chess
 * can both move and capture pieces with movement pattern
 */
class BizPiece extends GamePiece {
    public BizPiece(int r, int c, String player) {
        // use ArrayList to store multiple iamge string (not every piece have a same
        // quantity of picture)
        this.images = new ArrayList<String>();
        // store position
        this.row = r;
        this.col = c;
        // store red or blue
        this.player = player;
        this.currentImage = 0;

        // based on player to add different image path
        if (player.equals("Blue")) {
            images.add("Picture/Biz_Blue.png");
        } else if (player.equals("Red")) {
            images.add("Picture/Biz_Red.png");
        }
    }

    @Override
    public int[][] moveable(GamePiece[][] board) {
        int r, c;
        // store how many possible move
        int count = 0;
        // store all the possible move
        // 8 is the maximum possible move for biz piece
        int[][] moves = new int[8][2];
        // biz piece move like L shape, so will two set of the value
        // -1 and 1 shows the short part
        // -2 and 2 shows that long part
        int[][] value = {
                { -1, 1 }, { -2, 2 }
        };

        for (int i = 0; i < 2; i++) {
            for (int ii = 0; ii < 2; ii++) {
                // i is for row
                // ii is for col
                // row and col taking different set
                // value[0][i] is {-1,1}
                r = row + value[0][i];
                // value[1][i] is {-2,2}
                c = col + value[1][ii];

                // check is in the board or not
                if (r >= 0 && r < 8 && c >= 0 && c < 5) {
                    // this is moveable, so only for empty space then store the position that can
                    // move
                    if (board[r][c] == null) {
                        // at the possible move into move
                        // why use count++? because will auto increase one after using it
                        // cannot use ++count because will aurto increase one before using it
                        moves[count++] = new int[] { r, c };
                    }
                }

                // why seems like repeat? because row and col exchange the set
                // row takes value[1][i] while col takes value[0][i]
                r = row + value[1][i];
                c = col + value[0][ii];

                if (r >= 0 && r < 8 && c >= 0 && c < 5) {
                    // this is moveable, so only for empty space then store the position that can
                    // move
                    if (board[r][c] == null) {
                        // at the possible move into move
                        // why use count++? because will auto increase one after using it
                        // cannot use ++count because will aurto increase one before using it
                        moves[count++] = new int[] { r, c };
                    }
                }
            }
        }

        return Arrays.copyOf(moves, count);
    }

    @Override
    public int[][] capturable(GamePiece[][] board) {
        int r, c;
        // store how many possible move
        int count = 0;
        // store all the possible move
        // 8 is the maximum possible move for biz piece
        int[][] moves = new int[8][2];
        // biz piece move like L shape, so will two set of the value
        // -1 and 1 shows the short part
        // -2 and 2 shows that long part
        int[][] value = {
                { -1, 1 }, { -2, 2 }
        };

        for (int i = 0; i < value.length; i++) {
            for (int ii = 0; ii < value[0].length; ii++) {
                // i is for row
                // ii is for col
                // row and col taking different set
                // value[0][i] is {-1,1}
                r = row + value[0][i];
                // value[1][i] is {-2,2}
                c = col + value[1][ii];

                // check is in the board or not
                if (r >= 0 && r < 8 && c >= 0 && c < 5) {
                    // this is capturable, so need to make sure that have piece and is another side
                    // piece
                    // != null is make sure that have a piece
                    // !.getPlayer().equals(player) is make sure that is another side piece
                    if (board[r][c] != null && !board[r][c].getPlayer().equals(player)) {
                        // at the possible move into move
                        // why use count++? because will auto increase one after using it
                        // cannot use ++count because will aurto increase one before using it
                        moves[count++] = new int[] { r, c };
                    }
                }

                // why seems like repeat? because row and col exchange the set
                // row takes value[1][i] while col takes value[0][i]
                r = row + value[1][i];
                c = col + value[0][ii];

                if (r >= 0 && r < 8 && c >= 0 && c < 5) {
                    // this is capturable, so need to make sure that have piece and is another side
                    // piece
                    // != null is make sure that have a piece
                    // !.getPlayer().equals(player) is make sure that is another side piece
                    if (board[r][c] != null && !board[r][c].getPlayer().equals(player)) {
                        // at the possible move into move
                        // why use count++? because will auto increase one after using it
                        // cannot use ++count because will aurto increase one before using it
                        moves[count++] = new int[] { r, c };
                    }
                }
            }
        }
        return Arrays.copyOf(moves, count);
    }

    // update current position
    public void updatePosition(int r, int c) {
        this.row = r;
        this.col = c;
    }
}