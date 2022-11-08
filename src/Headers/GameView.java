package Headers;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.HashMap;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;


public class GameView extends JFrame
{
    private GameModel model;

    private final int BOARD_SIZE = 10;
    private final int SHIP_NUM = 5;
    private final String IMAGES = "/Resources/Images/";
    private final String SOUNDS = "./src/Resources/Sounds/";

    public OppButton[][] oppButtons = new OppButton[BOARD_SIZE][BOARD_SIZE];
    public JButton[][] myButtons = new JButton[BOARD_SIZE][BOARD_SIZE];

    private JPanel oppPanel = new JPanel();
    private MyPane myPane;
    //private JTextField titleTextField = new JTextField("Battleship");

    private JTextField titleTextField = new JTextField("Battleship");
    protected JLabel[] shipLabel = new JLabel[SHIP_NUM];
    HashMap<JLabel, Ship> map = new HashMap<>();
    private JLabel dragLabel;
    private JLabel clickLabel;
    private JButton dropButton;
    protected JButton autoSetupButton;

    protected JLabel text1 = new JLabel("Battle ship game", SwingConstants.CENTER);

    public GameView(GameModel gm)
    {
        model = gm;
        makePanel(oppPanel); // format top panel, where the user attacks.

        this.setTitle("Battleship");
        this.setPreferredSize(new Dimension(320, 820));
        this.setLocation(1000,70);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(oppPanel, BorderLayout.NORTH);
        this.add(titleTextField);
        myPane = new MyPane();
        myPane.setBackground(new Color(255, 255, 255));
        this.add(myPane, BorderLayout.SOUTH);
        this.pack();
        this.setLocationRelativeTo(null);

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
        for (int row = 0; row < 10; row++)
            for (int col = 0; col < 10; col++) {
                JButton newButton;
                newButton = new OppButton(col, row);
                oppButtons[col][row] = (OppButton) newButton;
                newPanel.add(newButton);
            }
    }

    public class MyPane extends JLayeredPane {
        int buttonSideLen = 30;
        int width = buttonSideLen * 10;
        int height = 180;

        public MyPane() {
            this.setOpaque(true);
            this.setBackground(new Color(255, 255, 255));


            String[] shipName = {"battleship4.png", "destoryer2.png", "cruiser3.png", "submarine3.png", "carrier5.png"};

            for (int index = 0; index < 5; index++) {
                String text = "Label " + index;
                System.out.println(IMAGES + shipName[index]);
                ImageIcon icon = null;

                try {
                    icon = new ImageIcon(ImageIO.read(getClass().getResource(IMAGES + shipName[index])));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                shipLabel[index] = new JLabel(icon);
                shipLabel[index].setSize(shipLabel[index].getPreferredSize());

                int x = ((index % 2 == 0) ? 0 : 150);
                int y = 30 + (index / 2) * 50;

                map.put(shipLabel[index], model.getShips()[index]);


                shipLabel[index].setLocation(x, y);
                add(shipLabel[index]);
            }

            // set buttons
            for (int row = 0; row < 10; row++){
                for (int col = 0; col < 10; col++) {
                    JButton newButton;
                    newButton = new JButton();
                    int fineTuning = 3;
                    myButtons[row][col] = newButton;
                    newButton.setLocation(fineTuning + col * buttonSideLen, height + row * buttonSideLen);
                    newButton.setSize(buttonSideLen, buttonSideLen);
                    add(newButton);
                    DropMouseHandler handlerDrop = new DropMouseHandler();
                    newButton.addMouseListener(handlerDrop);
                    newButton.addMouseMotionListener(handlerDrop);
                    System.out.printf("(%d, %d)  ", row, col);
                }
                System.out.println();
            }
            text1.setLocation(0, 0);
            text1.setSize(290, 30);
            add(text1);
            autoSetupButton = new JButton("Auto Setup");
            autoSetupButton.setLocation(170, 140);
            autoSetupButton.setSize(100, 30);
            add(autoSetupButton);
            MoveMouseHandler handler = new MoveMouseHandler();
            addMouseListener(handler);
            addMouseMotionListener(handler);

        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(width, height+width);
        }
    }


    public class MoveMouseHandler extends MouseAdapter {

        private int xOffset;
        private int yOffset;
        private int xOrigin;
        private int yOrigin;
        private int gridFrontClick;
        private String oldText;

        @Override
        public void mouseReleased(MouseEvent me) {
            if (dragLabel != null) {
                dragLabel.setText(oldText);
                dragLabel.setSize(dragLabel.getPreferredSize());
            }
            if (dropButton != null) {
                Ship ship = map.get(dragLabel);
                String name = ship.getName();
                int length = ship.getLength();
                boolean isVertical = ship.getVertical();
                int[] a = new int[2];
                a = getIndex(dropButton);
                int row = a[0], col = a[1];
                System.out.printf("click index: %d, %d\n", row, col);
                if (isDropped(this, row, col, length, isVertical)) {
                    dragLabel.setVisible(false);
                    for (int i = 0; i < length; i++) {
                        try {
                            if (isVertical) {
                                ImageIcon img = new ImageIcon(getClass().getResource(IMAGES + "vertical/" + name + "_" + i + ".png"));
                                myButtons[row- gridFrontClick + i][col].setIcon(img);
                                model.getMyBoard().setGrid(row- gridFrontClick + i, col, 1);
                                model.incCount();
                            } else {
                                ImageIcon img = new ImageIcon(getClass().getResource(IMAGES + name + "_" + i + ".png"));
                                myButtons[row][col - gridFrontClick + i].setIcon(img);
                                model.getMyBoard().setGrid(row, col - gridFrontClick + i, 1);
                                model.incCount();
                            }
//                            System.out.printf("set index %d: %d, %d\n", i, row, col - gridFrontClick + i);
                        } catch (Exception err) {
                            System.out.println("Couldn't set icon: " + err);
                        }
                    }
                    ship.setIsDrop(true);
                } else {
                    dragLabel.setLocation(xOrigin, yOrigin);
                }

                dropButton = null;
            }
            dragLabel = null;

        }

        public void mousePressed(MouseEvent me) {
            JComponent comp = (JComponent) me.getComponent();
            Component child = comp.findComponentAt(me.getPoint());
            if (child instanceof JLabel) {
                xOrigin = child.getX();
                yOrigin = child.getY();
                // offset is between JLabel set point and mouse click point
                xOffset = me.getX() - child.getX();
                yOffset = me.getY() - child.getY();
//                System.out.println(me.getX());
//                System.out.println(me.getY());
//                System.out.println(child.getX());
//                System.out.println(child.getY());
                dragLabel = (JLabel) child;
                Ship ship = map.get(dragLabel);
                gridFrontClick = xOffset / 30;
                if (ship.getVertical())
                    gridFrontClick = yOffset / 30;
                System.out.println(gridFrontClick);
                oldText = dragLabel.getText();
                dragLabel.setText("Drag");
                dragLabel.setSize(dragLabel.getPreferredSize());
            }
        }

        public void mouseDragged(MouseEvent me) {
            if (dragLabel != null) {
                dragLabel.setLocation(me.getX() - xOffset, me.getY() - yOffset);
            }
        }
        @Override
        public void mouseClicked(MouseEvent me){
            JComponent comp = (JComponent) me.getComponent();
            Component child = comp.findComponentAt(me.getPoint());
            clickLabel = (JLabel) child;
            if(me.getClickCount()==2){
                Ship ship = map.get(clickLabel);
                String name = ship.getName();
                System.out.println(name);
                if ( ship.getVertical()) {
                    ship.setVertical(false);
                    try {
                        ImageIcon img = new ImageIcon(getClass().getResource(IMAGES + name + ".png"));
                        clickLabel.setIcon(img);
                    } catch (Exception err) {
                        System.out.println("Couldn't set icon: " + err);
                    }

                } else {
                    ship.setVertical(true);
                    try {
                        ImageIcon img = new ImageIcon(getClass().getResource(IMAGES + "vertical/" + name + ".png"));
                        clickLabel.setIcon(img);
                    } catch (Exception err) {
                        System.out.println("Couldn't set icon: " + err);
                    }
                }
            }
        }
        public int getGridFrontClick() { return gridFrontClick; }
    }

    // add mouse listener for each grid
    public class DropMouseHandler extends MouseAdapter {
//        private boolean active = false;


        @Override
        public void mouseEntered(MouseEvent e) {
            if (dragLabel != null) {
                Ship ship = map.get(dragLabel);
                int length = ship.getLength();
                boolean isVertical = ship.getVertical();
//                active = true;
                dropButton = (JButton) e.getComponent();
                int[] a;
                a = getIndex(dropButton);
                MoveMouseHandler mv = new MoveMouseHandler();
                if (isDropped(mv, a[0], a[1], length, isVertical)) {
                    dragLabel.setText("Yes");
                } else {
                    dragLabel.setText("No");
                }
            }
        }

    }

    public int[] getIndex(JButton button) {
        int[] index = new int[2];
        for (int row = 0; row < 10; row++)
            for (int col = 0; col < 10; col++) {
                if (myButtons[row][col] == button) {
                    index = new int[]{row, col};
                    System.out.println(index[0]);
                    System.out.println(index[1]);
                }

            }
        return index;
    }

    public boolean isDropped(MoveMouseHandler mv, int row, int col, int length, boolean isVertical) {
        // two cases: out of bound and drop on other ship
        if (!isVertical) {
            if (col - mv.getGridFrontClick() < 0)
                return false;
            for (int i = 0; i < length; i++) {
                if (model.getMyBoard().getVal(row, col - mv.getGridFrontClick() + i) == 1)
                    return false;
            }
        } else {
            if (row - mv.getGridFrontClick() < 0)
                return false;
            for (int i = 0; i < length; i++) {
                if (model.getMyBoard().getVal(row - mv.getGridFrontClick() + i, col) == 1)
                    return false;
            }
        }
        return true;
    }

    public void attack(int x, int y)
    { //turn the button into smoke
        try {
            Image img = ImageIO.read(getClass().getResource(IMAGES + "bomb.jpg"));
            oppButtons[x][y].setIcon(new ImageIcon(img));
        } catch (IOException e) {
            System.out.println("Couldn't set icon: " + e);
        }
        playSound("bomb.wav");
    }

    public void playSound(String name) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(SOUNDS + name).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch(Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
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

    public void setText1(String t) {
        text1.setText(t);
    }

    public void displayShip(GameBoard b, Ship ship) {}
    public void showBoard(GameBoard a, GameBoard b) {}
    public void displayStatus(Ship[] ship1, Ship[] ship2) {}
    public void showFireResult(GameBoard a, int x, int y) {}
   // public void displayWin(Player p) {}
}




