package Network;// Fig. 24.9: Network.Server.java
// Network.Server that receives and sends packets from/to a client.
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;

public class Server implements NetworkComponent
{
   private InetAddress clientAddress;
   private int clientPort;
   private DatagramSocket socket; // socket to connect to client

   // set up GUI and DatagramSocket
   public Server()
   {
      try // create DatagramSocket for sending and receiving packets
      {
         socket = new DatagramSocket( 5000 );
      } // end try
      catch ( SocketException socketException ) 
      {
         socketException.printStackTrace();
         System.exit( 1 );
      } // end catch
   } // end Network.Server constructor

   // wait for packets to arrive, display data and echo packet to client
   public int waitForPackets()
   {
      while ( true ) 
      {
         try // receive packet, display contents, return copy to client
         {
            byte data[] = new byte[ 100 ]; // set up packet
            DatagramPacket receivePacket = new DatagramPacket( data, data.length );

            socket.receive( receivePacket ); // wait to receive packet
            clientAddress = receivePacket.getAddress();
            clientPort = receivePacket.getPort();

            // display information from received packet 
            System.out.println("\nPacket received:" +
               "\nFrom host: " + receivePacket.getAddress() + 
               "\nHost port: " + receivePacket.getPort() + 
               "\nLength: " + receivePacket.getLength() + 
               "\nContaining:\n\t" + receivePacket.getData() );

            byte[] message = receivePacket.getData();
            int coords = ByteBuffer.wrap(message).getInt();
            System.out.println("coords: " + coords);
            return coords;
         } // end try
         catch ( IOException ioException )
         {
            System.out.println( ioException.toString());
            ioException.printStackTrace();
         } // end catch
      } // end while
   } // end method waitForPackets

   // send packet to client
   public void sendPacket( int coords )
   {
      try // create and send packet
      {
         System.out.println("\n\nSend data to client. Message: " + coords);
         byte data[] = ByteBuffer.allocate(4).putInt(coords).array(); // convert to bytes
         // create packet to send
         DatagramPacket sendPacket = new DatagramPacket(data, data.length, clientAddress, clientPort);
         socket.send(sendPacket); // send packet to client
         System.out.println("Packet sent\n");
      } // end try
      catch ( IOException ioException )
      {
         System.out.println( ioException.toString() + "\n" );
         ioException.printStackTrace();
      } // end catch
   } // end method sendPacketToClient




} // end class Network.Server

/**************************************************************************
 * (C) Copyright 1992-2007 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 *************************************************************************/