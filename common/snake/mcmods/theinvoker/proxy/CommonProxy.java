package snake.mcmods.theinvoker.proxy;

import net.minecraftforge.common.MinecraftForge;
import snake.mcmods.theinvoker.handlers.EventCenter;
import snake.mcmods.theinvoker.lib.SoulSmelterMisc;
import snake.mcmods.theinvoker.lib.constants.TITileEntityID;
import snake.mcmods.theinvoker.logic.seductiontotems.SeductionTotemEventHooks;
import snake.mcmods.theinvoker.logic.totems.TotemEventHooks;
import snake.mcmods.theinvoker.net.packet.PacketTileEntityUpdate;
import snake.mcmods.theinvoker.tileentities.TileSeductionTotem;
import snake.mcmods.theinvoker.tileentities.TileSoulSmelter;
import snake.mcmods.theinvoker.tileentities.TileTotem;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy
{
	public void registerTileEntities()
	{
		GameRegistry.registerTileEntity(TileTotem.class, TITileEntityID.TOTEM);
		GameRegistry.registerTileEntity(TileSeductionTotem.class, TITileEntityID.SEDUCTION_TOTEM);
		GameRegistry.registerTileEntity(TileSoulSmelter.class, TITileEntityID.SOUL_SMELTER);
	}

	public void initRenderingStuff()
	{

	}

	public void handleTileEntityUpdate(PacketTileEntityUpdate p)
	{

	}

	public void registerEventHooks()
	{
		MinecraftForge.EVENT_BUS.register(new EventCenter());
		MinecraftForge.EVENT_BUS.register(new TotemEventHooks());
		MinecraftForge.EVENT_BUS.register(new SeductionTotemEventHooks());
		MinecraftForge.EVENT_BUS.register(new SoulSmelterMisc());
	}
}
