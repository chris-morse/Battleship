package Headers;

public class GameModel
{

    private GameBoard oppBoard;
    private GameBoard myBoard;
    private Ship[] ships;
    private int myShips;
    private int hits;
    private int count;

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
    count = 0;
    }

    // autoSetupBattleship
    // For each ship: pick random direction.
    // If horiz, pick random x, and y <= 10-length.
    // If vert, pick x <= 10-length, random y.
    // Check if collides w/ other ships. If it does, restart.
    public void autoSetupBattleship() {
        for (int i = 0; i < 5; i++)
            placeRandomShip(ships[i]);
        System.out.println("Ships added: " + count);
    }

    public void placeRandomShip(Ship ship)
    {
        boolean collides = true;
        int horiz = (int)(Math.random() * 2);
        int shipRow, shipCol;
        boolean horizontal = (horiz == 1) ? true : false;
        do{
            if(horizontal){
                shipCol = (int)(Math.random() * (9 - ship.getLength()+1 + 1));
                shipRow = (int)(Math.random() * (9 + 1));
            }
            else{
                shipCol = (int)(Math.random() * (9 + 1));
                shipRow = (int)(Math.random() * (9-ship.getLength()+1 + 1));
            }
            collides = placeShip(shipRow, shipCol, horizontal, ship);
        }
        while(!collides);
    }

    boolean placeShip(int row, int col, boolean horizontal, Ship ship)
    {
        int length = ship.getLength();
        int iter = horizontal ? col : row;

        // check if the ship will collide with any ships.
        for (int i = iter; i < iter+length; i++) {
            if(horizontal) {
                if(myBoard.getVal(row, i) == 1) return false;}
            else {
                if(myBoard.getVal(i, col) == 1) return false; }
        }

        //place the ship
        for (int i = iter; i < iter+length; i++) {
           if(horizontal) myBoard.setGrid(row, i, 1);
           else myBoard.setGrid(i, col, 1);
           count++;
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
