import Headers.*;

public class BC2 {
    public static void main(String args[])
    {
        GameModel model = new GameModel();
        GameView view = new GameView(model);
        GameController controller = new GameController(model, view);
    }
}
