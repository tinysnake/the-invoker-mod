package snake.mcmods.theinvoker.proxy;

import net.minecraft.client.multiplayer.WorldClient;
import snake.mcmods.theinvoker.constants.TIRenderID;
import snake.mcmods.theinvoker.net.packet.PacketTotemUpdate;
import snake.mcmods.theinvoker.renderer.RenderTotem;
import snake.mcmods.theinvoker.tileentities.TileTotem;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{
    @Override
    public void initRenderingStuff()
    {
        TIRenderID.TOTEM = RenderingRegistry.getNextAvailableRenderId();
        
        ClientRegistry.bindTileEntitySpecialRenderer(TileTotem.class, new RenderTotem());
    }

    @Override
    public void handleTileTotemUpdate(PacketTotemUpdate p)
    {
        WorldClient world = FMLClientHandler.instance().getClient().theWorld;
        TileTotem tt = (TileTotem)world.getBlockTileEntity(p.x, p.y, p.z);
        if(tt!=null)
        {
            tt.xCoord=p.x;
            tt.yCoord=p.y;
            tt.zCoord=p.z;
            tt.setDirection(p.direction);
            tt.setIsGhostBlock(p.isGhostBlock);
        }
    }
}
