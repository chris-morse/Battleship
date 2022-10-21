package Network;// Fig. 24.11: Network.Client.java
// Network.Client that sends and receives packets to/from a server.
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;

public class Client implements NetworkComponent
{
   private DatagramSocket socket; // socket to connect to server

   // set up GUI and DatagramSocket
   public Client()
   {
      try // create DatagramSocket for sending and receiving packets
      {
         socket = new DatagramSocket();
      } // end try
      catch ( SocketException socketException ) 
      {
         socketException.printStackTrace();
         System.exit( 1 );
      } // end catch
   } // end Network.Client constructor

   public void sendPacket(int coords)
   {
      try // create and send packet
      {

         System.out.println( "\nSending packet containing integer message: " + coords + "\n" );

         byte data[] = ByteBuffer.allocate(4).putInt(coords).array(); // convert to bytes

         // create sendPacket
         DatagramPacket sendPacket = new DatagramPacket(
                 data, data.length, InetAddress.getLocalHost(), 5000 );

         socket.send( sendPacket ); // send packet
         System.out.println( "Packet sent\n" );
      } // end try
      catch ( IOException ioException )
      {
         System.out.println( ioException.toString() + "\n" );
         ioException.printStackTrace();
      } // end catch
   } // end sendPacket


   // wait for packets to arrive from Network.Server, display packet contents
   public int waitForPackets()
   {
      while ( true ) 
      {
         try // receive packet and display contents
         {
            byte data[] = new byte[ 100 ]; // set up packet
            DatagramPacket receivePacket = new DatagramPacket( data, data.length );

            socket.receive( receivePacket ); // wait for packet

            // display packet contents
            System.out.println( "\nPacket received:" +
               "\nFrom host: " + receivePacket.getAddress() + 
               "\nHost port: " + receivePacket.getPort() + 
               "\nLength: " + receivePacket.getLength() + 
               "\nContaining:\n\t" + new String( receivePacket.getData()));

            byte[] message = receivePacket.getData();
            int coords = ByteBuffer.wrap(message).getInt();
            System.out.println("coords: " + coords);
            return coords;
         } // end try
         catch ( IOException exception ) 
         {
            System.out.println( exception.toString() + "\n" );
            exception.printStackTrace();
         } // end catch
      } // end while
   } // end method waitForPackets

   // manipulates displayArea in the event-dispatch thread


}  // end class Network.Client


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
