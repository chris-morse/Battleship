public class GameModel
{
    //Data Members
    GameBoard board1;
    GameBoard board2;
    Ship[] ship1;
    Ship[] ship2;
    Player player1;
    Player player2;

    //Methods
    GameModel(){}
    GameBoard getBoard1() {return board1;}
    GameBoard getBoard2() {return board2;}
    void setBoard1(GameBoard b) {board1 = b;}
    void setBoard2(GameBoard b) {board2 = b;}
    Ship[] getShip(int id) {}
    void setShip(int id, int x, int y, int status) {}
    Player getPlayer(int id) {}

}
