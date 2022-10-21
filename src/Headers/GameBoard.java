package Headers;

public class GameBoard
{
    private static int[][] grid = new int[10][10];

    public GameBoard() {
        for (int row = 0; row < 10; row++)
            for (int col = 0; col < 10; col++)
                grid[row][col] = 0;
    }
    public void setGrid(int x, int y, int val) { grid[x][y] = val; }
    public int getVal(int x, int y) { return grid[x][y]; }
}
