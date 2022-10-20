import Headers.*;

public class BattleshipClient {
    public static void main(String args[])
    {
        boolean networkType = false;
        GameModel model = new GameModel();
        GameView view = new GameView(model);
        GameController controller = new GameController(model, view, networkType);



    }
}
