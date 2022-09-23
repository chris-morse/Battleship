package Headers;

public class GameController
{
    //Data Members
    private GameModel model;
    private GameView view;

    //Methods
    public GameController(GameModel m, GameView v) { model = m; view = v;}
    //mouseDragged(MouseEvent event) {}
    //mouseMoved(MouseEvent event) {}
    void autoSetupBattleShip(GameBoard b, Ship ship) {}
    void setUpBattleShip(GameBoard b, Ship ship, int x, int y, int horOrVer) {}
    void checkShip(GameBoard b, Ship ship) {}
    void fire(GameBoard b, int x, int y) {}
    //Headers.Player checkWin(Headers.Ship[] s1, Headers.Ship[] s2) {}
}
