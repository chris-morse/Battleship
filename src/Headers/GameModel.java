package Headers;

public class GameModel
{
    //Data Members
    private GameBoard board1;
    private GameBoard board2;
    private Ship[] ship1;
    private Ship[] ship2;
    private Player player1;
    private Player player2;

    //Methods

    public GameModel(){
        board1 = new GameBoard();
        board2 = new GameBoard();
        ship1 = new Ship[5];
        ship2 = new Ship[5];
        player1 = new Player();
        player2 = new Player();
    }


    GameBoard getBoard1() { return board1; }
    GameBoard getBoard2() { return board2; }
    Ship[] getShip(int id) {
        if(id == 1) return ship1;
        if(id == 2) return ship2;
        else throw new IllegalArgumentException("Invalid ID");
    }
    Player getPlayer(int id) {
        if(id == 1) return player1;
        if(id == 2) return player2;
        else throw new IllegalArgumentException("Invalid ID");
    }
    void setBoard1(GameBoard b) {board1 = b;}
    void setBoard2(GameBoard b) {board2 = b;}
    void setShip(int id, int x, int y, int status) {}

}
