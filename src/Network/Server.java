package Network;
import java.io.*;
import java.net.*;

public class Server
{
    private ServerSocket serverSocket;
    private int numPlayers;
    private int maxPlayers;
    private Socket p1Socket;
    private Socket p2Socket;
    private ReadFromClient p1ReadRunnable;
    private ReadFromClient p2ReadRunnable;
    private WriteToClient p1WriteRunnable;
    private WriteToClient p2WriteRunnable;
    private int p1msg, p2msg;

    public Server() {
        System.out.println("===== GAME SERVER =====");
        numPlayers = 0;
        maxPlayers = 2;

        p1msg = -1;
        p2msg = -1;

        try{
            serverSocket = new ServerSocket(12345);
        }catch (IOException e){
            System.out.println("IOEx from server constructor");
        }
    }

    public void acceptConnections() {
        try{
            System.out.println("Waiting for players...");

            while(numPlayers < maxPlayers)
            {
                Socket socket = serverSocket.accept();
                DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

                numPlayers++;
                outputStream.writeInt(numPlayers);
                System.out.println("Player " + numPlayers + " has connected.");

                ReadFromClient readFromClient = new ReadFromClient(numPlayers, inputStream);
                WriteToClient writeToClient = new WriteToClient(numPlayers, outputStream);

                if(numPlayers == 1) {
                    p1Socket = socket;
                    p1ReadRunnable = readFromClient;
                    p1WriteRunnable = writeToClient;
                }
                else {
                    p2Socket = socket;
                    p2ReadRunnable = readFromClient;
                    p2WriteRunnable = writeToClient;

                    p1WriteRunnable.sendStartMsg();
                    p2WriteRunnable.sendStartMsg();

                    Thread readThread1 = new Thread(p1ReadRunnable);
                    Thread readThread2 = new Thread(p2ReadRunnable);
                    readThread1.start();
                    readThread2.start();

                    Thread writeThread1 = new Thread(p1WriteRunnable);
                    Thread writeThread2 = new Thread(p2WriteRunnable);
                    writeThread1.start();
                    writeThread2.start();

                } // end else
            } // end while
            System.out.println("No longer accepting new players.");

        }catch (IOException ioException){
            System.out.println("IOEx from acceptConnections() method");
        } // end catch

    } // end method acceptConnections

    private class ReadFromClient implements Runnable
    {
        private int playerID;
        private DataInputStream dataIn;

        public ReadFromClient(int pid, DataInputStream in) {
            playerID = pid;
            dataIn = in;
            System.out.println("Read from client " + playerID + " runnable created.");
        }

        public void run()
        {
            try {
                while(true) {
                    if(playerID == 1){
                        p1msg = dataIn.readInt();
                    }
                    else {
                        p2msg = dataIn.readInt();
                    }
                } // end while
            } catch(IOException ex) {
                System.out.println("IOex from RFC run()");
            }

        } // end method run()

    } // end class RFC

    private class WriteToClient implements Runnable
    {
        private int playerID;
        private DataOutputStream dataOut;

        public WriteToClient(int pid, DataOutputStream out) {
            playerID = pid;
            dataOut = out;
            System.out.println("Write to client " + playerID + " runnable created.");
        }

        public void run() {
            try {
                while(true) {
                    if(playerID == 1) {
                        dataOut.writeInt(p2msg);
                        dataOut.flush();
                    } else {
                        dataOut.writeInt(p1msg);
                        dataOut.flush();
                    }
                    try {
                        Thread.sleep(25);
                    } catch(InterruptedException ex){
                        System.out.println("Interrupted Ex from WTC run()");
                    }

                } // end while

            } catch (IOException ex) {
                System.out.println("IOEx from WTC run() method");
            }
        } // end method run()

        public void sendStartMsg() {
            try {
                dataOut.writeUTF("We now have two players. Go!");
            } catch (IOException ex) {
                System.out.println("IOEx from sendStartMsg()");
            }
        }
    } // end class WTC

}
