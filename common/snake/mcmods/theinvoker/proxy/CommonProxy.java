package snake.mcmods.theinvoker.proxy;

import snake.mcmods.theinvoker.constants.TITileEntityID;
import snake.mcmods.theinvoker.net.packet.PacketTotemUpdate;
import snake.mcmods.theinvoker.tileentities.TileTotem;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy
{
    public void registerTileEntities()
    {
        GameRegistry.registerTileEntity(TileTotem.class, TITileEntityID.TE_TOTEM);
    }
    
    public void initRenderingStuff()
    {
        
    }
    
    public void handleTileTotemUpdate(PacketTotemUpdate p)
    {
        
    }
}
