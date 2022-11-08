package Headers;
import Network.*;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.*;
import java.io.*;

public class GameController
{
    private final String IMAGES = "/Resources/Images/";
    private GameModel model;
    private GameView view;
    private int playerID;
    private int enemyMsg;

    private int attackX, attackY;
    private boolean readyToStart;
    private ReadFromServer rfsRunnable;
    private WriteToServer wtsRunnable;
    private Socket socket;


    public GameController(GameModel m, GameView v)
    {
        enemyMsg = -1;
        model = m;
        view = v;

        connectToServer();
        view.setVisible(true);

        disableAttack();

        ButtonHandler handler = new ButtonHandler();

        for (int rows = 0; rows < 10; rows++)
            for (int cols = 0; cols < 10; cols++)
                view.oppButtons[rows][cols].addActionListener(handler);

//        AutoShipButtonHandler autoShipHandler = new AutoShipButtonHandler();
        // view.autoSetupShipsButton.addActionListener(autoShipHandler);

//        autoSetupBattleship();
        AutoButtonHandel auto = new AutoButtonHandel();
        view.autoSetupButton.addActionListener(auto);
        boolean oppReadyToStart = false;
        boolean meReadyToStart = false;
        int loopCount = 0;
        do {
            if(model.shipsPlaced()) {
                meReadyToStart = true;
                if(loopCount < 1) {
                    try {
                        wtsRunnable.dataOut.writeInt(22222);
                        System.out.println("I am ready to start.");

                    } catch (IOException e) {
                        System.out.println("IOEX in me ready to start");
                    }
                }
                loopCount++;
            }

            if(!oppReadyToStart){
                try {
                    if(rfsRunnable.dataIn.readInt() == 22222) {
                        oppReadyToStart = true;
                        System.out.println("Opponent is ready to start.");
                    }
                } catch (IOException e){
                    System.out.println("IOEX in opp ready to start");
                }
            }

        } while(!meReadyToStart || !oppReadyToStart);
        startGame();

    }

    public void startGame(){
        if(playerID == 1) enableAttack();
        else opponentMove();
    }

    private void connectToServer() {
        try{
            socket = new Socket(InetAddress.getByName( "localhost" ), 12345);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            playerID = in.readInt();
            System.out.println("Connected to server as Player " + playerID);
            if(playerID == 1) {
                System.out.println("Waiting for Player 2 to connect...");
            }
            rfsRunnable = new ReadFromServer(in);
            wtsRunnable = new WriteToServer(out);

            rfsRunnable.waitForStartMsg();

        } catch(IOException ioException){
            System.out.println("IOEx from connectToServer() method");
        }

    } // end method connectToServer()


    class ButtonHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            System.out.println("actionPerformed()");
            disableAttack();
            ((GameView.OppButton) event.getSource()).removeActionListener(this);
            ((GameView.OppButton) event.getSource()).isAlive = false;
            GameView.OppButton thisButton = (GameView.OppButton) event.getSource();
            attackX = thisButton.getRow();
            attackY = thisButton.getCol();
            fire(attackX, attackY);
        }
    }
    class AutoButtonHandel implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            autoSetup();
            view.autoSetupButton.setEnabled(false);
            for (int i = 0; i < 5; i++) {
                view.shipLabel[i].setVisible(false);
                view.shipLabel[i].setEnabled(false);
            }
        }
    }

//    class AutoShipButtonHandler implements ActionListener
//    {
//        public void actionPerformed(ActionEvent event)
//        {
//            autoSetupBattleship();
//        }
//    }

    public void fire(int row, int col)
    {
        disableAttack();
        System.out.println("fire()");

        // send coords to opponent
        String message = "" + row + col;
        int coords = Integer.valueOf(message);
        try {
            wtsRunnable.dataOut.writeInt(55555);

            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                System.out.println("IE in Thread.sleep() opp move");
            }

            wtsRunnable.dataOut.writeInt(coords);
            wtsRunnable.dataOut.flush();
            System.out.println("Sending coords " + coords + " to opponent.");

        } catch(IOException ioException){
            System.out.println("IOEx in fire() (writing coords)");
        }
        enemyMsg = -1;
        getResponse();

    }

    public void getResponse() {
        System.out.println("getResponse()");

        System.out.println("Waiting for enemy response to my attack...");
        boolean waiting = true;
        while(waiting) {
            try {
                if (rfsRunnable.dataIn.readInt() == 44444) {
                    System.out.println("Got 44444 verification.");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        System.out.println("IE in Thread.sleep() opp move");
                    }
                   do {
                        enemyMsg = rfsRunnable.dataIn.readInt();
                        System.out.println("****************Received response " + enemyMsg + " from opponent.");
                        waiting = false;
                    } while(enemyMsg == 44444);
                }
            } catch(IOException ioException) {
                System.out.println("IOEx in opponentMove()");
            }
        }


        boolean hit = (enemyMsg == 1) ? true : false;

        System.out.println("attack:"+ attackX+ attackY);
        if(hit) {
            model.setOppBoard(attackX, attackY, 1);
            model.incHits();
            view.attack(attackX, attackY);
        }
        else {
            model.setOppBoard(attackX, attackY, 2);
            view.playSound("splash.wav");
            //view.miss(x,y);
        }

        System.out.println("Points: " + model.getHits());
        if(checkWin()) {
            gameOver(true);
        }
        opponentMove();
    }

    public void opponentMove()
    {
        System.out.println("opponentMove()");
        System.out.println("Waiting for opponent to attack...");
        boolean waiting = true;
        while(waiting) {
            try {
                if (rfsRunnable.dataIn.readInt() == 55555) {
                    System.out.println("Got 55555 verification.");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        System.out.println("IE in Thread.sleep() opp move");
                    }

                    do {
                        enemyMsg = rfsRunnable.dataIn.readInt();
                        System.out.println("Received response " + enemyMsg + " from opponent.");
                        waiting = false;
                    } while(enemyMsg == 55555);
                }
            } catch (IOException ioException) {
                System.out.println("IOEx in opponentMove()");
            }
        }

        int messageX = enemyMsg / 10;
        int messageY = enemyMsg % 10;
        System.out.println("Enemy is attacking at coords " + messageX + ", " + messageY);
        System.out.println("Model value at coords is " + model.getMyBoard().getVal(messageY,messageX));
        boolean didHit = model.getAttacked(messageY, messageX);
        int response = didHit ? 1 : 0;
        displayMyBoard(messageX, messageY, didHit);
        try {
            wtsRunnable.dataOut.writeInt(44444);
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                System.out.println("IE in Thread.sleep() opp move");
            }
             wtsRunnable.dataOut.writeInt(response);
             wtsRunnable.dataOut.flush();
             System.out.println("Sending response " + response + " to opponent.");
        } catch (IOException ioException) {
            System.out.println("IOEx in opponentMove() reading message");
        }
        System.out.println("Points remaining: " + model.getMyShips());
        int counter = 0;
//
//        for(int i = 0; i < 10; i++)
//            for(int j = 0; j < 10; j++)
//                if (model.getOppBoard().getVal(i, j) == 1)
//                    counter++;
        view.setText1("Points remaining: " + model.getMyShips());
        if(checkLose()) gameOver(false);
        enableAttack();
    }

    public void gameOver(boolean didWin)
    {
        System.out.println("gameOver()");
        disableAttack();
        String message = didWin ? "You win!" : "You lose.";
        String winSound = didWin ? "win.wav" : "lose.wav";
        System.out.println("Game over. " + message);
        view.setText1(message);
        view.playSound(winSound);

    }

    boolean checkWin() { return (model.getHits() == 17) ? true : false; }
    boolean checkLose() { return (model.getMyShips() == 0) ? true : false; }

    public void enableAttack() { view.enableOppGrid(); }
    public void disableAttack(){ view.disableOppGrid(); }

//    public void autoSetupBattleship()
//    {
//        model.autoSetupBattleship();
//        for (int rows = 0; rows < 10; rows++)
//            for (int cols = 0; cols < 10; cols++)
//            {
//                if(model.getMyBoard().getVal(rows, cols) == 1)
//                {
//                    try {
//                        Image img = ImageIO.read(getClass().getResource(IMAGES + "smoke.jpg"));
//                        view.myButtons[rows][cols].setIcon(new ImageIcon(img));
//                    } catch (IOException e) {
//                        System.out.println("Couldn't set icon: " + e);
//                    }  // end catch
//                } // end if
//            } // end for
//    } // end autoSetupBattleship


    private class ReadFromServer implements Runnable
    {
        private DataInputStream dataIn;

        public ReadFromServer(DataInputStream in) {
            dataIn = in;
            System.out.println("RFS Runnable created.");
        }

        public void run() {

        }
        public void waitForStartMsg() {
            try {
                String startMsg = dataIn.readUTF();
                System.out.println("Message from server: " + startMsg);

                Thread readThread = new Thread(rfsRunnable);
                Thread writeThread = new Thread(wtsRunnable);
                readThread.start();
                writeThread.start();

            } catch (IOException ex) {
                System.out.println("IOex from waitForStartMsg()");
            }
        }
    } // end class RFS

    private class WriteToServer implements Runnable
    {
        private DataOutputStream dataOut;

        public WriteToServer(DataOutputStream out) {
            dataOut = out;
            System.out.println("WTS Runnable created.");
        }
        public void run() {
        }
    } // end class WTS



    // Auto setup ships
    public void autoSetup() {
        for (int i = 0; i < 5; i++)
            if (!model.getShips()[i].getIsDrop())
                placeRandomShip(model.getShips()[i]);
        System.out.println("Ships added: " + model.getCount());
    }

    public void placeRandomShip(Ship ship) {
        boolean collides = true;
        int horiz = (int) (Math.random() * 2);
        int shipRow, shipCol;
        boolean horizontal = (horiz == 1) ? true : false;
        String name = ship.getName();
        do {
            if (horizontal) {
                shipCol = (int) (Math.random() * (9 - ship.getLength() + 1));
                shipRow = (int) (Math.random() * (9 + 1));
            } else {
                shipCol = (int) (Math.random() * (9 + 1));
                shipRow = (int) (Math.random() * (9 - ship.getLength() + 1));
            }
            collides = placeShip(shipRow, shipCol, horizontal, ship);
        }
        while (!collides);
        if (horizontal) {
            for (int i = 0; i < ship.getLength(); i++) {
                try {
                    ImageIcon img = new ImageIcon(getClass().getResource(IMAGES + name + "_" + i + ".png"));
                    view.myButtons[shipRow][shipCol + i].setIcon(img);
                    model.getMyBoard().setGrid(shipRow, shipCol, 1);
//
                } catch (Exception err) {
                    System.out.println("Couldn't set icon: " + err);
                }
            }
        } else {
            for (int i = 0; i < ship.getLength(); i++) {
                try {
                    ImageIcon img = new ImageIcon(getClass().getResource(IMAGES + "vertical/" + name + "_" + i + ".png"));
                    view.myButtons[shipRow + i][shipCol].setIcon(img);
                    model.getMyBoard().setGrid(shipRow, shipCol, 1);
//
                } catch (Exception err) {
                    System.out.println("Couldn't set icon: " + err);
                }

            }
        }
    }

    boolean placeShip(int row, int col, boolean horizontal, Ship ship)
    {
        int length = ship.getLength();
        int iter = horizontal ? col : row;

        // check if the ship will collide with any ships.
        for (int i = iter; i < iter+length; i++) {
            if(horizontal) {
                if(model.getMyBoard().getVal(row, i) == 1) return false;}
            else {
                if(model.getMyBoard().getVal(i, col) == 1) return false; }
        }

        //place the ship
        for (int i = iter; i < iter+length; i++) {
            if(horizontal) model.getMyBoard().setGrid(row, i, 1);
            else model.getMyBoard().setGrid(i, col, 1);
            model.incCount();
        }
        return true;
    }

    public void displayMyBoard(int x, int y, boolean isHit) {
        if (isHit) {
            try {
                ImageIcon img = new ImageIcon(getClass().getResource(IMAGES + "bomb.jpg"));
                view.myButtons[y][x].setIcon(img);
            } catch (Exception err) {
                System.out.println("Couldn't set icon: " + err);
            }
        } else {
            try {
            ImageIcon img = new ImageIcon(getClass().getResource(IMAGES + "grey.png"));
            view.myButtons[y][x].setIcon(img);
        } catch (Exception err) {
            System.out.println("Couldn't set icon: " + err);
        }
        }
    }

} // end Class GameController