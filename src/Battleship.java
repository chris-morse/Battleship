import Headers.*;

public class Battleship {
    public static void main(String args[])
    {
        /*
        Client application;

        if ( args.length == 0 )
            application = new Client( "127.0.0.1" ); // connect to localhost
        else
            application = new Client( args[ 0 ] );´ // use args to connect

        application.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        application.runClient();
        */
        boolean networkType = false;
        if(args.length != 0)
            if(args[0] == "server") networkType = true;


        GameModel model = new GameModel();
        GameView view = new GameView(model);
        GameController controller = new GameController(model, view, networkType);

    }
}
