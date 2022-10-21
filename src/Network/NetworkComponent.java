package Network;

public interface NetworkComponent
{
    public void sendPacket(int coords);
    public int waitForPackets();
}
