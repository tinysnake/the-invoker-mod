package snake.mcmods.theinvoker.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import snake.mcmods.theinvoker.TheInvoker;
import snake.mcmods.theinvoker.constants.TIGlobal;
import snake.mcmods.theinvoker.constants.TINames;
import snake.mcmods.theinvoker.constants.TIRenderIDs;
import snake.mcmods.theinvoker.tileentities.TileTotem;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockTotemBase extends BlockContainer
{
    public BlockTotemBase(int id)
    {
        super(id, Material.cloth);
        this.setUnlocalizedName(TINames.BLOCK_TOTEM);
        this.setCreativeTab(TheInvoker.tab);
        this.setBlockBounds(0.25F, 0F, 0.8125F, 0.75F, 1.5F, 0.1875F);
        this.setHardness(1.0F);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon(TIGlobal.MOD_ID + ":" + getUnlocalizedName2());
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public int getRenderType()
    {
        return TIRenderIDs.TOTEM;
    }

    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileTotem();
    }
}
