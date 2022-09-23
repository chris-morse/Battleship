package Headers;

public class GameBoard
{
    private static int[][] grid = new int[10][10];

    public GameBoard(){}
    void setGrid(int x, int y, int val) {grid[x][y] = val;}

}
