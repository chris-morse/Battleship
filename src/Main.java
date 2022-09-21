import java.util.Scanner;

public class Main
{
    public static void main(String args[])
    {
        Player player1 = new Player();
        Player player2 = new Player();
        GameBoard board1 = new GameBoard();
        GameBoard board2 = new GameBoard();
        Ship[] ship1 = new Ship[5];
        Ship[] ship2 = new Ship[5];

        GameModel model = new GameModel(board1, board2, ship1, ship2, player1, player2);
        GameView view = new GameView(model);
        GameController controller = new GameController(model, view);

        Scanner sc = new Scanner(System.in);
        System.out.println("Hello. Welcome to BattleShip.\nCreate username: ");
        player1.setName(sc.nextLine());
        System.out.println("Hello " + player1.getName() + ".\nLet's play BattleShip!");







    }





}
