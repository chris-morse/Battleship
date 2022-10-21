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

    public OppButton[][] oppButtons = new OppButton[BOARD_SIZE][BOARD_SIZE];
    private JButton[][] myButtons = new JButton[BOARD_SIZE][BOARD_SIZE];

    private JPanel oppPanel = new JPanel();
    private JPanel myPanel = new JPanel();
    private JTextField titleTextField = new JTextField("Battleship");

/*----------------------------------------------------------------------------*/

    public GameView(GameModel gm)
    {
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

    class OppButton extends JButton
    {
        private int row;
        private int col;
        public boolean isAlive;
        public int getRow(){ return row; }
        public int getCol(){ return col; }

        OppButton(int x, int y)
        {
            super();
            isAlive = true;
            row = x;
            col = y;
            setBackground(Color.white);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setBorder(BorderFactory.createLineBorder(new Color(20, 24, 24)));
            setEnabled(true);

            try {
                Image img = ImageIO.read(getClass().getResource(IMAGES + "water.png"));
                setIcon(new ImageIcon(img));
            } catch (IOException e) {
                System.out.println("Couldn't set icon: " + e);
            }
        }
    }
    private void makePanel(JPanel newPanel)
    {
        newPanel.setLayout(new GridLayout(10,10));
        newPanel.setPreferredSize(new Dimension(300, 300));
        newPanel.setBackground(new Color(51, 152, 208));

        //Make all 100 buttons
        for( int col = 0; col < 10; col++)
            for( int row = 0; row < 10; row++) {
                JButton newButton;
                if(newPanel == oppPanel){
                    newButton = new OppButton(col, row);
                    oppButtons[col][row] = (OppButton) newButton;
                }
                else {
                    newButton = new JButton();
                    myButtons[col][row] = newButton;
                }
                newPanel.add(newButton);
            }
    }

    public void attack(int x, int y)
    { //turn the button into smoke
        try {
        Image img = ImageIO.read(getClass().getResource(IMAGES + "smoke.jpg"));
        oppButtons[x][y].setIcon(new ImageIcon(img));
        } catch (IOException e) {
            System.out.println("Couldn't set icon: " + e);
        }

    }

    public void enableOppGrid(){
        for (int rows = 0; rows < 10; rows++)
            for (int cols = 0; cols < 10; cols++) {
                if (oppButtons[rows][cols].isAlive) oppButtons[rows][cols].setEnabled(true);
            }
    }

    public void disableOppGrid(){
        for (int rows = 0; rows < 10; rows++)
            for (int cols = 0; cols < 10; cols++) {
                oppButtons[rows][cols].setEnabled(false);
            }
    }

    public void displayShip(GameBoard b, Ship ship) {}
    public void showBoard(GameBoard a, GameBoard b) {}
    public void displayStatus(Ship[] ship1, Ship[] ship2) {}
    public void showFireResult(GameBoard a, int x, int y) {}
   // public void displayWin(Player p) {}
}




