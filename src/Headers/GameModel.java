package Headers;

public class GameModel
{

    private GameBoard oppBoard;
    private GameBoard myBoard;
    private Ship[] ships;
    private int myShips;
    private int hits;

    public GameModel()
    {
        oppBoard = new GameBoard();
        myBoard = new GameBoard();
        ships = new Ship[5];
        ships[0] = new Ship(2);
        ships[1] = new Ship(3);
        ships[2] = new Ship(3);
        ships[3] = new Ship(4);
        ships[4] = new Ship(5);

        myShips = 17;
        hits = 0;

    }

    boolean placeShip(int row, int col, boolean direction, Ship ship)
    {
        int length = ship.getLength();
        int iter = direction ? col : row;
        // check if you can place a ship
        for (int i = iter; i < iter+length; i++) {
            if(direction) if(myBoard.getVal(row, i) == 1) return false;
            else { if(myBoard.getVal(i, col) == 1) return false; }
        }
        //place the ship
        for (int i = iter; i < iter+length; i++) {
           if(direction) myBoard.setGrid(row, i, 1);
           else myBoard.setGrid(i, col, 1);
        }
        return true;
    }

    public void incHits() { hits++; }
    public int getHits() { return hits; }

    public void decMyShips() { myShips--; }
    public int getMyShips() { return myShips; }

    public GameBoard getOppBoard() { return oppBoard; }
    public GameBoard getMyBoard() { return myBoard; }
    Ship[] getShips() { return ships; }

    boolean getAttacked(int row, int col)
    {
        boolean didHit = false;
        if(myBoard.getVal(row, col) == 1) {
            didHit = true;
            myBoard.setGrid(row, col, 2);
            decMyShips();
        }
        return didHit;
    }

    void setOppBoard(int x, int y, int val){ oppBoard.setGrid(x, y, val); }
    void setShip(int id, int x, int y, int status) {}

}
