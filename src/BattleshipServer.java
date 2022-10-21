import Headers.GameController;
import Headers.GameModel;
import Headers.GameView;

public class BattleshipServer {
    public static void main(String args[])
    {
        boolean isServer = true;
        GameModel model = new GameModel();
        GameView view = new GameView(model);
        GameController controller = new GameController(model, view, isServer);
    }
}
