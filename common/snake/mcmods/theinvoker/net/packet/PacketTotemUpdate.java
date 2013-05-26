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
    public PacketTotemUpdate(int x, int y, int z, byte direction, boolean isGhostBlock)
    {
        super(PacketTypeHandler.TOTEM, true);
        this.x = x;
        this.y = y;
        this.z = z;
        this.direction = direction;
        this.isGhostBlock = isGhostBlock;
    }

    public int x;
    public int y;
    public int z;
    public byte direction;
    public boolean isGhostBlock;

    @Override
    protected void writeData(DataOutputStream dos) throws IOException
    {
        dos.writeInt(x);
        dos.writeInt(y);
        dos.writeInt(z);
        dos.writeByte(direction);
        dos.writeBoolean(isGhostBlock);
    }

    @Override
    protected void readData(DataInputStream dis) throws IOException
    {
        x = dis.readInt();
        y = dis.readInt();
        z = dis.readInt();
        direction = dis.readByte();
        isGhostBlock = dis.readBoolean();
    }

    @Override
    public void doItsThing()
    {
        TheInvoker.proxy.handleTileTotemUpdate(this);
    }

}
