package Headers;
import Network.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameController
{
    //Data Members
    private NetworkComponent network;
    private GameModel model;
    private GameView view;
    private String username;
    private int state;

    //Methods
    public GameController(GameModel m, GameView v, boolean isServer)
    {
        model = m;
        view = v;
        disableAttack();
        network = isServer ? new Server() : new Client();
        ButtonHandler handler = new ButtonHandler();

        for (int rows = 0; rows < 10; rows++)
            for (int cols = 0; cols < 10; cols++) {
                view.oppButtons[rows][cols].addActionListener(handler);
            }

        // PLACE SHIPS
        // METHOD HERE

        if(!isServer) enableAttack();
        else opponentMove();

        // enableOppGrid
        // When button clicked,
        // disable oppgrid
        // send attack coords
        // get response
        // if true, kill grid spot
        // check for win
        // wait for opp turn
        // respond to attack
        // check if lose
        // repeat

    }

    class ButtonHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            disableAttack();
            GameView.OppButton thisButton = (GameView.OppButton) event.getSource();
            fire(thisButton.getRow(), thisButton.getCol() );
            ((GameView.OppButton) event.getSource()).setEnabled(false);
            ((GameView.OppButton) event.getSource()).removeActionListener(this);
            ((GameView.OppButton) event.getSource()).isAlive = false;
        }
    }

    public void fire(int row, int col)
    {
        String message = "" + row + col;
        int coords = Integer.valueOf(message);
        network.sendPacket(coords);
        int response = network.waitForPackets();
        boolean hit = (response == 1) ? true : false;
        if(hit) {
            model.setOppBoard(row, col, 1);
            model.incHits();
            view.attack(row, col);
        }
        else {
            model.setOppBoard(row, col, 2);
            //view.miss(x,y);
        }

        if(checkWin()) gameOver(true);
        opponentMove();
    }

    public void opponentMove()
    {
        int message = network.waitForPackets();
        int messageX = message / 10;
        int messageY = message % 10;
        boolean didHit = model.getAttacked(messageX, messageY);
        int response = didHit ? 1 : 0;
        network.sendPacket(response);
        if(checkLose()) gameOver(false);
        enableAttack();
    }

    public void gameOver(boolean didWin)
    {}

    boolean checkWin() { return (model.getHits() == 17) ? true : false; }
    boolean checkLose() { return (model.getMyShips() == 0) ? true : false; }

    public void enableAttack(){
        view.enableOppGrid();
    }
    public void disableAttack(){
        view.disableOppGrid();
    }

    public void autoSetupBattleShip(GameBoard b, Ship ship) {}
    public void setUpBattleShip(GameBoard b, Ship ship, int x, int y, int horOrVer) {}

} // end Class GameController


