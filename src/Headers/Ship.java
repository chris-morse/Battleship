package Headers;

public class Ship
{
    //Data Members
    private String name = "battleship";
    private int length;
    //private int width;
    private boolean isSunk;
    private int unitsLeft;
    private boolean isVertical = false;
    private boolean isDrop = false;

    //Methods
    Ship(int l, String n) {
        name = n;
        length = l;

        //  width = w;
        unitsLeft = l;
        isVertical =
                isSunk = false;
    }
    String getName() { return name; }
    int getLength() { return length; }
    int getUnitsLeft() { return unitsLeft; }
    void setLength(int l) { length = l; }
    // void setWidth(int w) {width = w;}
    boolean getIsSunk() { return isSunk; }
    void setIsSunk(boolean b) {isSunk = b;}
    void setVertical(boolean b){ isVertical = b; }
    boolean getVertical() { return isVertical; }
    void setIsDrop(boolean b) { isDrop = b; }
    boolean getIsDrop() { return isDrop; }

}

