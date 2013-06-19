package snake.mcmods.theinvoker;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import snake.mcmods.theinvoker.entities.EntitySoulStoneMonitor;
import snake.mcmods.theinvoker.lib.constants.TIRenderID;
import snake.mcmods.theinvoker.net.packet.PacketTileEntityUpdate;
import snake.mcmods.theinvoker.renderer.RenderElementPurifier;
import snake.mcmods.theinvoker.renderer.RenderSeductionTotem;
import snake.mcmods.theinvoker.renderer.RenderSoulStoneMonitor;
import snake.mcmods.theinvoker.renderer.RenderTotem;
import snake.mcmods.theinvoker.tileentities.TileElementPurifier;
import snake.mcmods.theinvoker.tileentities.TileSeductionTotem;
import snake.mcmods.theinvoker.tileentities.TileTIBase;
import snake.mcmods.theinvoker.tileentities.TileTotem;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{
	@Override
	public void initRenderingStuff()
	{
		TIRenderID.TOTEM = RenderingRegistry.getNextAvailableRenderId();
		TIRenderID.SEDUCTION_TOTEM = RenderingRegistry.getNextAvailableRenderId();
		TIRenderID.ELEMENT_PURIFIER = RenderingRegistry.getNextAvailableRenderId();

		ClientRegistry.bindTileEntitySpecialRenderer(TileTotem.class, new RenderTotem());
		ClientRegistry.bindTileEntitySpecialRenderer(TileSeductionTotem.class, new RenderSeductionTotem());
		ClientRegistry.bindTileEntitySpecialRenderer(TileElementPurifier.class, new RenderElementPurifier());

		RenderingRegistry.registerEntityRenderingHandler(EntitySoulStoneMonitor.class, new RenderSoulStoneMonitor());
	}

	@Override
	public void registerTickHandlers()
	{
		super.registerTickHandlers();
	}

	@Override
	public void handleTileEntityUpdate(PacketTileEntityUpdate p, EntityPlayer player)
	{
		World world = player.worldObj;
		TileTIBase tt = (TileTIBase)world.getBlockTileEntity(p.x, p.y, p.z);
		if (tt != null)
		{
			tt.xCoord = p.x;
			tt.yCoord = p.y;
			tt.zCoord = p.z;
			tt.setDirection(ForgeDirection.getOrientation(p.direction));
			tt.setOwnerName(p.ownerName);
		}
	}
}
