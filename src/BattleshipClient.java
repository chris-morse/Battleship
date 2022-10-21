import Headers.*;

public class BattleshipClient {
    public static void main(String args[])
    {
        boolean isServer = false;
        GameModel model = new GameModel();
        GameView view = new GameView(model);
        GameController controller = new GameController(model, view, isServer);

    }
}
