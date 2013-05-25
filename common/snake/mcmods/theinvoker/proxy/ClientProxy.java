package snake.mcmods.theinvoker.proxy;

import snake.mcmods.theinvoker.constants.TIRenderID;
import snake.mcmods.theinvoker.renderer.RenderTotem;
import snake.mcmods.theinvoker.tileentities.TileTotem;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{

    @Override
    public void registerRenderers()
    {
        TIRenderID.TOTEM = RenderingRegistry.getNextAvailableRenderId();
        
        ClientRegistry.bindTileEntitySpecialRenderer(TileTotem.class, new RenderTotem());
    }

}
