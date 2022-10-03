package Headers;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class GameView extends JFrame
{
    private GameModel model;

    private final int BOARD_SIZE = 10;
    private final String IMAGES = "/Resources/Images/";

    private JButton[][] buttonGrid = new JButton[BOARD_SIZE][BOARD_SIZE];
    private JPanel oppPanel = new JPanel();
    private JPanel myPanel = new JPanel();
    private JTextField titleTextField = new JTextField("Battleship");
//----------------------------------------------------------------------------

    public GameView(GameModel gm) {
        model = gm;
        makePanel(oppPanel); // format top panel, where the user attacks.
        makePanel(myPanel); // format bottom panel, where the user places his ships.

        this.setTitle("Battleship");
        this.setPreferredSize(new Dimension(400, 800));
        this.setLocation(1000,70);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(oppPanel, BorderLayout.NORTH);
        this.add(titleTextField);
        this.add(myPanel, BorderLayout.SOUTH);
        this.pack();
        this.setVisible(true);
    }


    private void makePanel(JPanel newPanel)
    {
        newPanel.setLayout(new GridLayout(10,10));
        newPanel.setPreferredSize(new Dimension(300, 300));
        newPanel.setBackground(new Color(51, 152, 208));

        //Make 100 buttons
        for( int col = 0; col < 10; col++)
            for( int row = 0; row < 10; row++) {
                JButton newButton = new JButton();
                newButton.setBackground(Color.white);
                newButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                newButton.setBorder(BorderFactory.createLineBorder(new Color(20, 24, 24)));

                try {
                    Image img = ImageIO.read(getClass().getResource(IMAGES + "smoke.jpg"));
                    newButton.setIcon(new ImageIcon(img));
                } catch (IOException e) {
                    System.out.println("Couldn't set field icon: " + e);
                }
                //buttons[col][row] = newButton;
                newPanel.add(newButton);
            }

    }





    public void displayShip(GameBoard b, Ship ship) {}
    public void showBoard(GameBoard a, GameBoard b) {}
    public void displayStatus(Ship[] ship1, Ship[] ship2) {}
    public void showFireResult(GameBoard a, int x, int y) {}
    public void displayWin(Player p) {}

}




