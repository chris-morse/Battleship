import Headers.*;

public class Battleship {
    public static void main(String args[])
    {
        /*
        Client application;

        if ( args.length == 0 )
            application = new Client( "127.0.0.1" ); // connect to localhost
        else
            application = new Client( args[ 0 ] ); // use args to connect

        application.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        application.runClient();

        */

        Player player1 = new Player();
        Player player2 = new Player();
        GameBoard p1Board = new GameBoard();
        GameBoard p2Board = new GameBoard();
        Ship[] p1Ships = new Ship[5];
        Ship[] p2Ships = new Ship[5];

        GameModel model = new GameModel();
        GameView view = new GameView(model);
        GameController controller = new GameController(model, view);






    }
}
