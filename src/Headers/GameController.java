package Headers;
import javax.swing.*;

public class GameController
{
    //Data Members
    private GameModel model;
    private GameView view;

    //Methods
    public GameController(GameModel m, GameView v) {
        model = m;
        view = v;
        // Add listeners to view
        // view.add___Listener(new ___Listener());
        // view.add___Listener(new ___Listener());
        // ...
    }


    public void autoSetupBattleShip(GameBoard b, Ship ship) {}
    public void setUpBattleShip(GameBoard b, Ship ship, int x, int y, int horOrVer) {}
    public void checkShip(GameBoard b, Ship ship) {}
    public void fire(GameBoard b, int x, int y) {}
    // Headers.Player checkWin(Headers.Ship[] s1, Headers.Ship[] s2) {}
    // mouseDragged(MouseEvent event) {}
    // mouseMoved(MouseEvent event) {}


}
