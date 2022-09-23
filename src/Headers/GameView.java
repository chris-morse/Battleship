package Headers;

public class GameView
{
    //Data Members
    private GameModel model;

    //Methods
    public GameView(GameModel gm) {model = gm;}
    void displayShip(GameBoard b, Ship ship) {}
    void showBoard(GameBoard a, GameBoard b) {}
    void displayStatus(Ship[] ship1, Ship[] ship2) {}
    void showFireResult(GameBoard a, int x, int y) {}
    void displayWin(Player p) {}
}
