import java.util.Scanner;
import Headers.*;
import Network.*;
import javax.swing.*;

public class Main {
    public static void main(String args[])
    {
        /*
        Server myserver = new Server(); // create server
        myserver.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        myserver.runServer();

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

        GameModel model = new GameModel(p1Board, p2Board, p1Ships, p2Ships, player1, player2);
        GameView view = new GameView(model);
        GameController controller = new GameController(model, view);

        Scanner sc = new Scanner(System.in);
        System.out.println("Hello. Welcome to BattleShip.\nCreate username: ");
        player1.setName(sc.nextLine());
        System.out.println("Hello " + player1.getName() + ".\nLet's play BattleShip!");

        System.out.println("Hello. Welcome to BattleShip.\nCreate username for player 2: ");
        player2.setName(sc.nextLine());
        System.out.println("Hello " + player2.getName() + ".\nLet's play BattleShip!");


    }



}
