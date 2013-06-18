package snake.mcmods.theinvoker;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import snake.mcmods.theinvoker.energy.EnergyTickHandler;
import snake.mcmods.theinvoker.handlers.ForgeTickHandler;
import snake.mcmods.theinvoker.handlers.MiscEventCenter;
import snake.mcmods.theinvoker.lib.constants.TITileEntityID;
import snake.mcmods.theinvoker.logic.seductiontotems.SeductionTotemEventHooks;
import snake.mcmods.theinvoker.logic.totems.TotemEventHooks;
import snake.mcmods.theinvoker.net.packet.PacketTileEntityUpdate;
import snake.mcmods.theinvoker.tileentities.TileElementPurifier;
import snake.mcmods.theinvoker.tileentities.TileSeductionTotem;
import snake.mcmods.theinvoker.tileentities.TileSoulSmelter;
import snake.mcmods.theinvoker.tileentities.TileSoulStone;
import snake.mcmods.theinvoker.tileentities.TileTotem;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class CommonProxy
{
	public void registerTileEntities()
	{
		GameRegistry.registerTileEntity(TileTotem.class, TITileEntityID.TOTEM);
		GameRegistry.registerTileEntity(TileSeductionTotem.class, TITileEntityID.SEDUCTION_TOTEM);
		GameRegistry.registerTileEntity(TileSoulSmelter.class, TITileEntityID.SOUL_SMELTER);
		GameRegistry.registerTileEntity(TileSoulStone.class, TITileEntityID.SOUL_STONE);
		GameRegistry.registerTileEntity(TileElementPurifier.class, TITileEntityID.ELEMENT_PURIFIER);
	}

	public void initRenderingStuff()
	{

	}

	public void registerEventHooks()
	{
		MinecraftForge.EVENT_BUS.register(new MiscEventCenter());
		MinecraftForge.EVENT_BUS.register(new TotemEventHooks());
		MinecraftForge.EVENT_BUS.register(new SeductionTotemEventHooks());
	}

	public void registerTickHandlers()
	{
		TickRegistry.registerTickHandler(new ForgeTickHandler(), Side.SERVER);
		TickRegistry.registerTickHandler(new EnergyTickHandler(), Side.SERVER);
	}

	public void handleTileEntityUpdate(PacketTileEntityUpdate p, EntityPlayer player)
	{

	}
}
