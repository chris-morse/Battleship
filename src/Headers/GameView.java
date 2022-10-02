package Headers;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class GameView extends JFrame
{
    //Data Members
    private GameModel model;

    private final int SIZE = 10;
    private final String IMAGES = "/Resources/Images/";

    private JButton[][] buttons = new JButton[10][10];
    JPanel oppPanel = new JPanel();
    JPanel myPanel = new JPanel();
    JTextField titleTextField = new JTextField("Battleship");




    //Methods
    public GameView(GameModel gm) {
        model = gm;

        oppPanel.setLayout(new GridLayout(10,10));
        oppPanel.setPreferredSize(new Dimension(300, 300));
        oppPanel.setBackground(new Color(100, 200, 250));
        oppPanel.setBorder(BorderFactory.createLineBorder(new Color(50, 150, 200)));

        for( int col = 0; col < 10; col++)
            for( int row = 0; row < 10; row++)
            {
                JButton newButton = new JButton();
                newButton.setBackground(Color.blue);
                newButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                buttons[col][row] = newButton;
                oppPanel.add(newButton);
            }



        myPanel.setLayout(new GridLayout(10,10));
        myPanel.setPreferredSize(new Dimension(300, 300));
        myPanel.setBackground(new Color(200, 0, 0));
        myPanel.setBorder(BorderFactory.createLineBorder(new Color(50, 150, 200)));

        for( int col = 0; col < 10; col++)
            for( int row = 0; row < 10; row++)
            {
                JButton newButton = new JButton();
                newButton.setBackground(Color.white);
                newButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

                try {
                    Image img = ImageIO.read(getClass().getResource(IMAGES + "smoke.jpg"));
                    newButton.setIcon(new ImageIcon(img));
                } catch (IOException e) {
                    System.out.println("Couldn't set field icon: " + e);
                }

                //buttons[col][row] = newButton;
                myPanel.add(newButton);
            }



        this.setTitle("Battleship");
        this.setPreferredSize(new Dimension(400, 800));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(oppPanel, BorderLayout.NORTH);
        this.add(titleTextField);
        this.add(myPanel, BorderLayout.SOUTH);
        this.pack();
        this.setVisible(true);
    }


    public void displayShip(GameBoard b, Ship ship) {}
    public void showBoard(GameBoard a, GameBoard b) {}
    public void displayStatus(Ship[] ship1, Ship[] ship2) {}
    public void showFireResult(GameBoard a, int x, int y) {}
    public void displayWin(Player p) {}
}




