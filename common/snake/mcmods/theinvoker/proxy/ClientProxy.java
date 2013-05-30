package snake.mcmods.theinvoker.proxy;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraftforge.common.ForgeDirection;
import snake.mcmods.theinvoker.lib.TIRenderID;
import snake.mcmods.theinvoker.net.packet.PacketTileEntityUpdate;
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
    public void handleTileEntityUpdate(PacketTileEntityUpdate p)
    {
        WorldClient world = FMLClientHandler.instance().getClient().theWorld;
        TileTotem tt = (TileTotem)world.getBlockTileEntity(p.x, p.y, p.z);
        if(tt!=null)
        {
            tt.xCoord=p.x;
            tt.yCoord=p.y;
            tt.zCoord=p.z;
            tt.setDirection(ForgeDirection.getOrientation(p.direction));
            tt.setOwnerName(p.ownerName);
        }
    }
}
