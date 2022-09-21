import java.awt.event.MouseEvent;

public class GameController
{
    //Data Members
    private GameModel model;
    private GameView view;

    //Methods
    GameController(GameModel m, GameView v) { model = m; view = v;}
    //mouseDragged(MouseEvent event) {}
    //mouseMoved(MouseEvent event) {}
    void autoSetupBattleShip(GameBoard b, Ship ship) {}
    void setUpBattleShip(GameBoard b, Ship ship, int x, int y, int horOrVer) {}
    void checkShip(GameBoard b, Ship ship) {}
    void fire(GameBoard b, int x, int y) {}
    //Player checkWin(Ship[] s1, Ship[] s2) {}
}
