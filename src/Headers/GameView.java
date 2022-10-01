package Headers;
import javax.swing.*;
import java.awt.*;

public class GameView extends JFrame
{
    //Data Members
    private GameModel model;

    private JTextField m_userInput = new JTextField(5);
    private JTextField m_total = new JTextField(10);
    private JButton button1 = new JButton("Button1");
    // view should have a table of buttons
    // and a table of images over the buttons.


    //Methods
    public GameView(GameModel gm) {
        model = gm;

        m_total.setText("Test 1");
        m_total.setEditable(false);

        JPanel content = new JPanel();
        content.setLayout(new FlowLayout());
        content.add(new JLabel("Input"));
        content.add(m_userInput);
        content.add(button1);
        content.add(new JLabel("Total"));
        content.add(m_total);

        this.setContentPane(content);
        this.pack();
        this.setTitle("Battleship");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public void displayShip(GameBoard b, Ship ship) {}
    public void showBoard(GameBoard a, GameBoard b) {}
    public void displayStatus(Ship[] ship1, Ship[] ship2) {}
    public void showFireResult(GameBoard a, int x, int y) {}
    public void displayWin(Player p) {}
}
