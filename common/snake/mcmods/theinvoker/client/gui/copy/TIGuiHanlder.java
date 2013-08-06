package snake.mcmods.theinvoker.client.gui.copy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import snake.mcmods.theinvoker.inventory.ContainerElementPurifier;
import snake.mcmods.theinvoker.inventory.ContainerSoulSmelter;
import snake.mcmods.theinvoker.lib.constants.TIGuiID;
import snake.mcmods.theinvoker.tileentities.TileElementPurifier;
import snake.mcmods.theinvoker.tileentities.TileSoulSmelter;
import cpw.mods.fml.common.network.IGuiHandler;

public class TIGuiHanlder implements IGuiHandler
{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity te = world.getBlockTileEntity(x, y, z);
		switch (ID)
		{
			case TIGuiID.SOUL_SMELTER:
				if (te instanceof TileSoulSmelter)
					return new ContainerSoulSmelter(player.inventory, (TileSoulSmelter)te);
				break;
			case TIGuiID.ELEMENT_PURIFIER:
				if (te instanceof TileElementPurifier)
					return new ContainerElementPurifier(player.inventory, (TileElementPurifier)te);
				break;
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity te = world.getBlockTileEntity(x, y, z);
		switch (ID)
		{
			case TIGuiID.SOUL_SMELTER:
				if (te instanceof TileSoulSmelter)
					return new GuiSoulSmelter(player.inventory, (TileSoulSmelter)te);
				break;
			case TIGuiID.ELEMENT_PURIFIER:
				if (te instanceof TileElementPurifier)
					return new GuiElementPurifier(player.inventory, (TileElementPurifier)te);
				break;
		}
		return null;
	}

}
