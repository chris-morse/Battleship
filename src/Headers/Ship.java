package Headers;

public class Ship
{
    //Data Members
    private int length;
    //private int width;
    private boolean isSunk;
    private int unitsLeft;

    //Methods
    Ship(int l) {
        length = l;
      //  width = w;
        unitsLeft = l;
        isSunk = false;
    }

    int getLength() { return length; }
    int getUnitsLeft() { return unitsLeft; }
    void setLength(int l) { length = l; }
   // void setWidth(int w) {width = w;}
    boolean getIsSunk() { return isSunk; }
    void setIsSunk(boolean b) {isSunk = b;}

}
