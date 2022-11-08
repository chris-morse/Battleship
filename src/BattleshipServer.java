import Headers.GameController;
import Headers.GameModel;
import Headers.GameView;
import Network.Server;

public class BattleshipServer {
    public static void main(String args[])
    {
        Server server = new Server();
        server.acceptConnections();
    }
}
