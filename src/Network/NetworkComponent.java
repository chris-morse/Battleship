package Network;

import Headers.Coords;

import java.io.IOException;

public interface NetworkComponent
{
    public void run();
    public boolean sendAttack(Coords coords) throws IOException;
}
