package snake.mcmods.theinvoker.proxy;

import snake.mcmods.theinvoker.lib.constants.TITileEntityID;
import snake.mcmods.theinvoker.net.packet.PacketTileEntityUpdate;
import snake.mcmods.theinvoker.tileentities.TileSeductionTotem;
import snake.mcmods.theinvoker.tileentities.TileTotem;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy
{
    public void registerTileEntities()
    {
        GameRegistry.registerTileEntity(TileTotem.class, TITileEntityID.TOTEM);
        GameRegistry.registerTileEntity(TileSeductionTotem.class, TITileEntityID.SEDUCTION_TOTEM);
    }
    
    public void initRenderingStuff()
    {
        
    }
    
    public void handleTileEntityUpdate(PacketTileEntityUpdate p)
    {
        
    }
}
