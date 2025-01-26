import java.util.Arrays;
import java.util.ArrayList;

// Chong Jian Sieuang
/*
 * Model, this class is to manage the xor piece
 * it can move at angle (bothe left & right) across the board
 * move until blocked by another piece or the edge of the board
 */
class XorPiece extends GamePiece {
    public XorPiece(int r, int c, String player) {
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
            images.add("Picture/Xor_Blue.png");
        } else if (player.equals("Red")) {
            images.add("Picture/Xor_Red.png");
        }
    }

    @Override
    public int[][] moveable(GamePiece[][] board) {
        // four dicrection
        boolean topLeft = true, topRight = true, bottomLeft = true, bottomRight = true;
        int i = 1;
        // store how many possible move
        int count = 0;
        // store all the possible move
        // 4 + 4 is maximum possible move for tor piece (not include itself)
        int[][] moves = new int[board[0].length + board[0].length - 2][2];

        // xor piece can move diagonally only but can go any distance
        // same concept with tor piece
        // this is moveable step, so any direction detect have piece, the moveable step
        // is before the piece only
        // four direction have their own possible step to reach the end of the board or
        // the piece
        // the while loop condition is based on the four direction
        while (topLeft || topRight || bottomLeft || bottomRight) {
            // this is for moving top left
            if (row - i >= 0 && col - i >= 0 && topLeft) {
                if (board[row - i][col - i] == null) {
                    // at the possible move into move
                    // why use count++? because will auto increase one after using it
                    // cannot use ++count because will aurto increase one before using it
                    moves[count++] = new int[] { row - i, col - i };
                } else {
                    // once detect the piece, stop showing possible move after the piece
                    topLeft = false;
                }
            } else {
                // reach the end
                topLeft = false;
            }

            // this is for moving top right
            if (row - i >= 0 && col + i < board[0].length && topRight) {
                if (board[row - i][col + i] == null) {
                    // at the possible move into move
                    // why use count++? because will auto increase one after using it
                    // cannot use ++count because will aurto increase one before using it
                    moves[count++] = new int[] { row - i, col + i };
                } else {
                    // once detect the piece, stop showing possible move after the piece
                    topRight = false;
                }
            } else {
                // reach the end
                topRight = false;
            }

            // this is for moving bottom left
            if (row + i < board.length && col - i >= 0 && bottomLeft) {
                if (board[row + i][col - i] == null) {
                    // at the possible move into move
                    // why use count++? because will auto increase one after using it
                    // cannot use ++count because will aurto increase one before using it
                    moves[count++] = new int[] { row + i, col - i };
                } else {
                    // once detect the piece, stop showing possible move after the piece
                    bottomLeft = false;
                }
            } else {
                // reach the end
                bottomLeft = false;
            }

            // this is for moving bottom right
            if (row + i < board.length && col + i < board[0].length && bottomRight) {
                if (board[row + i][col + i] == null) {
                    // at the possible move into move
                    // why use count++? because will auto increase one after using it
                    // cannot use ++count because will aurto increase one before using it
                    moves[count++] = new int[] { row + i, col + i };
                } else {
                    // once detect the piece, stop showing possible move after the piece
                    bottomRight = false;
                }
            } else {
                // reach the end
                bottomRight = false;
            }

            // increase one for checking position is valid for any direction
            i++;
        }

        // this arrays.copyof is used for return the possible step based on count that
        // store how many possible move
        // why? because if you use moves, the rest will auto declare as {0,0} which make
        // the piece can move to position (0,0)
        return Arrays.copyOf(moves, count);
    }

    @Override
    public int[][] capturable(GamePiece[][] board) {
        boolean topLeft = true, topRight = true, bottomLeft = true, bottomRight = true;
        int i = 1;
        int count = 0;
        int[][] moves = new int[4][2];

        while (topLeft || topRight || bottomLeft || bottomRight) {
            // this is for moving top left
            if (row - i >= 0 && col - i >= 0 && topLeft) {
                if (board[row - i][col - i] != null) {
                    // !.getPlayer().equals(player) is make sure that is another side piece
                    if (!board[row - i][col - i].getPlayer().equals(player)) {
                        // at the possible move into move
                        // why use count++? because will auto increase one after using it
                        // cannot use ++count because will aurto increase one before using it
                        moves[count++] = new int[] { row - i, col - i };
                    }
                    // not matter what will stop because cannot capture after the piece
                    topLeft = false;
                }
            } else {
                // reach the end
                topLeft = false;
            }

            // this is for moving top right
            if (row - i >= 0 && col + i < board[0].length && topRight) {
                if (board[row - i][col + i] != null) {
                    // !.getPlayer().equals(player) is make sure that is another side piece
                    if (!board[row - i][col + i].getPlayer().equals(player)) {
                        // at the possible move into move
                        // why use count++? because will auto increase one after using it
                        // cannot use ++count because will aurto increase one before using it
                        moves[count++] = new int[] { row - i, col + i };
                    }
                    // not matter what will stop because cannot capture after the piece
                    topRight = false;
                }
            } else {
                // reach the end
                topRight = false;
            }

            // this is for moving bottom left
            if (row + i < board.length && col - i >= 0 && bottomLeft) {
                if (board[row + i][col - i] != null) {
                    // !.getPlayer().equals(player) is make sure that is another side piece
                    if (!board[row + i][col - i].getPlayer().equals(player)) {
                        // at the possible move into move
                        // why use count++? because will auto increase one after using it
                        // cannot use ++count because will aurto increase one before using it
                        moves[count++] = new int[] { row + i, col - i };
                    }
                    // not matter what will stop because cannot capture after the piece
                    bottomLeft = false;
                }
            } else {
                // reach the end
                bottomLeft = false;
            }

            // this is for moving bottom right
            if (row + i < board.length && col + i < board[0].length && bottomRight) {
                if (board[row + i][col + i] != null) {
                    // !.getPlayer().equals(player) is make sure that is another side piece
                    if (!board[row + i][col + i].getPlayer().equals(player)) {
                        // at the possible move into move
                        // why use count++? because will auto increase one after using it
                        // cannot use ++count because will aurto increase one before using it
                        moves[count++] = new int[] { row + i, col + i };
                    }
                    // not matter what will stop because cannot capture after the piece
                    bottomRight = false;
                }
            } else {
                // reach the end
                bottomRight = false;
            }

            // increase one for checking position is valid for any direction
            i++;
        }

        // this arrays.copyof is used for return the possible step based on count that
        // store how many possible move
        // why? because if you use moves, the rest will auto declare as {0,0} which make
        // the piece can move to position (0,0)
        return Arrays.copyOf(moves, count);
    }

    // update current position
    public void updatePosition(int r, int c) {
        this.row = r;
        this.col = c;
    }
}