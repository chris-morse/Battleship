public class Ship
{
    //Data Members
    private int length;
    private int width;
    private String name;
    private boolean isSunk;

    //Methods
    Ship(int l, int w, String n) {
        length = l;
        width = w;
        name = n;
        isSunk = false;
    }
    int getLength() {return length;}
    int getWidth() {return width;}
    String getName() {return name;}
    void setLength(int l) {length = l;}
    void setWidth(int w) {width = w;}
    void setName(String n) {name = n;}
    boolean getIsSunk() {return isSunk;}
    void setIsSunk(boolean b) {isSunk = b;}

}
