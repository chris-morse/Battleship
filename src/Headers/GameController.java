package Headers;
import Network.*;

import java.io.IOException;

public class GameController
{
    //Data Members
    private NetworkComponent networkComponent;
    private GameModel model;
    private GameView view;
    private String username;
    private int state;

    //Methods
    public GameController(GameModel m, GameView v, boolean networkType)
    {
        model = m;
        view = v;
        if (networkType) networkComponent = new Server(m);
        else networkComponent = new Client("127.0.0.1", m);
        networkComponent.run();

        disableAttack();

        //Welcome to battleship
        //please place your ships now
        //wait for player to place all ships
        //wait for signal that opponent placed ships
        //now the game can begin.

        if(networkType){enableAttack();}

        do {
            playBattleship();
        } while(model.myShipsLeft > 0 && model.oppShipsLeft > 0);

        gameOver();

        // Add listeners to view
        // view.add___Listener(new ___Listener());
        // view.add___Listener(new ___Listener());
        // ...

    }



    public void playBattleship()
    {


        // if server, go first.
        // if client, wait for server.
        // when it's your turn, enable oppBoard on view.
        // button listener calls attack on its button coords. Instantly disables the oppBoard.
        // if you get a hit, check if you win.
        // if you didn't win, continue. Unable to perform any actions until the opponent sends his move.
        //





    }

    public void gameOver()
    {


    }

    public void enableAttack(){}
    public void disableAttack(){}

    public void autoSetupBattleShip(GameBoard b, Ship ship) {}
    public void setUpBattleShip(GameBoard b, Ship ship, int x, int y, int horOrVer) {}


    public void fire(int x, int y)
    {
        boolean hit = false;
        try {
           hit = networkComponent.sendAttack(new Coords(x, y));
        } catch(IOException ioException)
        { /*something here*/ }

        if(hit) {
            model.setOppBoard(x, y, 1);
            //view.setOppBoard(x y smokeImage)
        }
        else {
            model.setOppBoard(x, y, 2);
            //view.setOppBoard(x y missImage)
        }

        if(checkWin()) gameOver();



    }

    boolean checkWin() {return false;}



}
