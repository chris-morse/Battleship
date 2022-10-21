package Network;

import Headers.Coords;

import java.io.IOException;

public interface NetworkComponent
{
    public void sendPacket(int coords);
    public int waitForPackets();
}
