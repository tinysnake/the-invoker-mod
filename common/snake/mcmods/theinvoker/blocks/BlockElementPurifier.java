package snake.mcmods.theinvoker.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import snake.mcmods.theinvoker.TheInvoker;
import snake.mcmods.theinvoker.lib.constants.TIName;
import snake.mcmods.theinvoker.lib.constants.TIRenderID;
import snake.mcmods.theinvoker.tileentities.TileElementPurifier;

public class BlockElementPurifier extends BlockContainer
{

	public BlockElementPurifier(int id)
	{
		super(id, Material.iron);
		this.setCreativeTab(TheInvoker.tab);
		this.setUnlocalizedName(TIName.BLOCK_ELEMENT_PURIFIER);
	}

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileElementPurifier();
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock()
	{
	    return false;
	}
	
	@Override
	public int getRenderType()
	{
	    return TIRenderID.ELEMENT_PURIFIER;
	}
}
