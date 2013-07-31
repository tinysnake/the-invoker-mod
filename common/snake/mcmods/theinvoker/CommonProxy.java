package snake.mcmods.theinvoker;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import snake.mcmods.theinvoker.energy.EnergyCenter;
import snake.mcmods.theinvoker.handlers.MiscEventCenter;
import snake.mcmods.theinvoker.lib.constants.TITileEntityID;
import snake.mcmods.theinvoker.logic.grimoire.GrimoireSystem;
import snake.mcmods.theinvoker.logic.seductiontotems.SeductionTotemEventHooks;
import snake.mcmods.theinvoker.logic.totems.TotemCenter;
import snake.mcmods.theinvoker.logic.totems.TotemEventHooks;
import snake.mcmods.theinvoker.net.packet.PacketTileEntityUpdate;
import snake.mcmods.theinvoker.tileentities.TileElemPillar;
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
		GameRegistry.registerTileEntity(TileElemPillar.class, TITileEntityID.ELEMENT_PILLAR);
	}

	public void initRenderingStuff()
	{

	}

	public void registerEventHooks()
	{
		MinecraftForge.EVENT_BUS.register(new MiscEventCenter());
		MinecraftForge.EVENT_BUS.register(new TotemEventHooks());
		MinecraftForge.EVENT_BUS.register(new SeductionTotemEventHooks());
		MinecraftForge.EVENT_BUS.register(EnergyCenter.INSTANCE);
	}

	public void registerTickHandlers()
	{
		TickRegistry.registerTickHandler(TotemCenter.INSTANCE, Side.SERVER);
		TickRegistry.registerTickHandler(EnergyCenter.INSTANCE, Side.SERVER);
		TickRegistry.registerTickHandler(GrimoireSystem.INSTANCE, Side.CLIENT);
	}

	public void handleTileEntityUpdate(PacketTileEntityUpdate p, EntityPlayer player)
	{

	}
}
