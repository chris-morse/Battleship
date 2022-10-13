// Fig. 24.8: ClientTest.java
// Test the Client class.
package Network;
import javax.swing.JFrame;

public class ClientTest 
{
   public static void main( String args[] )
   {
      OldClient application; // declare client application

      // if no command line args
      if ( args.length == 0 )
         application = new OldClient( "131.118.58.13" ); // connect to localhost
      else
         application = new OldClient( args[0] ); // use args to connect

      application.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
      application.runClient(); // run client application
   } // end main
} // end class ClientTest

 //application = new Client( "127.0.0.1" );
/**************************************************************************
 * (C) Copyright 1992-2005 by Deitel & Associates, Inc. and               *
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
