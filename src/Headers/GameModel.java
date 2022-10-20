package Headers;

public class GameModel
{

    private GameBoard oppBoard;
    private GameBoard myBoard;
    private Ship[] ships;
    public int myShipsLeft;
    public int oppShipsLeft;

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
        myShipsLeft = 5;
        oppShipsLeft = 5;

    }

    GameBoard getOppBoard() { return oppBoard; }
    GameBoard getMyBoard() { return myBoard; }
    Ship[] getShips() { return ships; }

    void setOppBoard(int x, int y, int val){ oppBoard.setGrid(x, y, val); }
    void setShip(int id, int x, int y, int status) {}

}
