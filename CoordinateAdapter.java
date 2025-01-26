/**
 * Adapter, it is responsible for converting screen (pixel) coordinates
 * to 2D array grid coordinates (row, column). This is useful in applications like
 * grid-based games or UI systems, where mouse clicks or pixel positions need to be mapped
 * to logical grid positions
 */
public class CoordinateAdapter {
    private int cellSize;
    private int offsetX;
    private int offsetY;

    public CoordinateAdapter() {
    }

    public void update(int s, int x, int y) {
        this.cellSize = s;
        this.offsetX = x;
        this.offsetY = y;
    }

    public int[] convertCoordinate(int X, int Y) {
        int col = X - offsetX < 0 ? -1 : (X - offsetX) / cellSize;
        int row = Y - offsetY < 0 ? -1 : (Y - offsetY) / cellSize;

        return new int[] { row, col };
    }
}
