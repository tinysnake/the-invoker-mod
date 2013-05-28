package snake.mcmods.theinvoker.net.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import snake.mcmods.theinvoker.TheInvoker;
import snake.mcmods.theinvoker.net.PacketTypeHandler;

public class PacketTotemUpdate extends PacketTI
{
    public PacketTotemUpdate()
    {
        super(PacketTypeHandler.TOTEM, true);
    }
    public PacketTotemUpdate(int x, int y, int z, int direction)
    {
        super(PacketTypeHandler.TOTEM, true);
        this.x = x;
        this.y = y;
        this.z = z;
        this.direction = direction;
    }

    public int x;
    public int y;
    public int z;
    public int direction;

    @Override
    protected void writeData(DataOutputStream dos) throws IOException
    {
        dos.writeInt(x);
        dos.writeInt(y);
        dos.writeInt(z);
        dos.writeInt(direction);
    }

    @Override
    protected void readData(DataInputStream dis) throws IOException
    {
        x = dis.readInt();
        y = dis.readInt();
        z = dis.readInt();
        direction = dis.readInt();
    }

    @Override
    public void doItsThing()
    {
        TheInvoker.proxy.handleTileTotemUpdate(this);
    }

}
