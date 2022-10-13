package Headers;
import Network.*;

public class GameController
{
    //Data Members
    private GameModel model;
    private GameView view;
    private String username;
    private NetworkComponent networkComponent;

    //Methods
    public GameController(GameModel m, GameView v, boolean networkType)
    {
        model = m;
        view = v;
        if (networkType) networkComponent = new Server();
        else networkComponent = new Client("127.0.0.1");
        networkComponent.run();


        // Add listeners to view
        // view.add___Listener(new ___Listener());
        // view.add___Listener(new ___Listener());
        // ...
    }



    public void autoSetupBattleShip(GameBoard b, Ship ship) {}
    public void setUpBattleShip(GameBoard b, Ship ship, int x, int y, int horOrVer) {}
    public void checkShip(GameBoard b, Ship ship) {}
    public void fire(GameBoard b, int x, int y) {}
    // boolean checkWin(Headers.Ship[] s1, Headers.Ship[] s2) {}
    // mouseDragged(MouseEvent event) {}
    // mouseMoved(MouseEvent event) {}


}
