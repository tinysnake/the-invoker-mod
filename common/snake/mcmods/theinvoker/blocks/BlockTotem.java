package snake.mcmods.theinvoker.blocks;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import snake.mcmods.theinvoker.items.ItemTotem;
import snake.mcmods.theinvoker.items.TIItems;
import snake.mcmods.theinvoker.lib.TIRenderID;
import snake.mcmods.theinvoker.lib.TotemType;
import snake.mcmods.theinvoker.lib.constants.TIGlobal;
import snake.mcmods.theinvoker.tileentities.TileTotem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTotem extends Block2HeightBase
{
    public BlockTotem(int id)
    {
        super(id, Material.wood);
        this.setBlockBounds(0.2F, 0F, 0.2F, 0.8F, 1F, 0.8F);
        this.setHardness(2.5F);
        this.setLightValue(7);
        ghostBlockMetadata = TotemType.GHOST.ordinal();
    }

    private Icon[] blockIcons;

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister)
    {
        blockIcons = new Icon[ItemTotem.NAMES.length - 1];
        for (int i = 1; i < ItemTotem.NAMES.length; i++)
        {
            blockIcons[i - 1] = iconRegister.registerIcon(TIGlobal.MOD_ID + ":" + ItemTotem.NAMES[i]);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int metadata)
    {
        if (metadata == 0)
            return blockIcons[0];
        return blockIcons[metadata - 1];
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
        return TIRenderID.TOTEM;
    }

    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileTotem();
    }

    @Override
    public int idDropped(int metadata, Random par2Random, int fortuneLvl)
    {
        return TIItems.totem.itemID;
    }

    @Override
    public int damageDropped(int metadata)
    {
        return metadata;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        if (world.getBlockMetadata(x, y, z) == TotemType.GHOST.ordinal())
            return super.getCollisionBoundingBoxFromPool(world, x, y, z);
        return AxisAlignedBB.getAABBPool().getAABB((double) x + this.minX, (double) y + this.minY, (double) z + this.minZ, (double) x + this.maxX, (double) y + this.maxY + 1, (double) z + this.maxZ);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rdm) {

    }

}
