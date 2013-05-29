package snake.mcmods.theinvoker.blocks;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import snake.mcmods.theinvoker.items.ItemTotem;
import snake.mcmods.theinvoker.items.TIItems;
import snake.mcmods.theinvoker.lib.TIRenderID;
import snake.mcmods.theinvoker.lib.TotemType;
import snake.mcmods.theinvoker.lib.constants.TIGlobal;
import snake.mcmods.theinvoker.tileentities.TileTotem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTotem extends BlockContainer
{
    public BlockTotem(int id)
    {
        super(id, Material.wood);
        this.setBlockBounds(0.2F, 0F, 0.2F, 0.8F, 1F, 0.8F);
        this.setHardness(2.5F);
        this.setLightValue(7);
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
        if(metadata==0)
            return blockIcons[0];
        return blockIcons[metadata-1];
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
    public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side)
    {
        return super.canPlaceBlockOnSide(world, x, y, z, side);
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z)
    {
        return y >= 255 ? false : world.doesBlockHaveSolidTopSurface(x, y - 1, z) && super.canPlaceBlockAt(world, x, y, z) && super.canPlaceBlockAt(world, x, y + 1, z);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving entityLiving,
            ItemStack itemStack)
    {
        TileTotem te = (TileTotem) world.getBlockTileEntity(x, y, z);
        te.setOwnerName(entityLiving.getEntityName());
        int face = MathHelper
                .floor_double((double) (entityLiving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        switch (face)
        {
            case 0:
                te.setDirection((byte) 2);
                break;
            case 1:
                te.setDirection((byte) 5);
                break;
            case 2:
                te.setDirection((byte) 3);
                break;
            case 3:
                te.setDirection((byte) 4);
                break;
        }
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int neighborBlockID)
    {
        int metadata = world.getBlockMetadata(x, y, z);
        if (metadata == TotemType.TYPE_GHOST.ordinal())
        {
            // is ghost block, look for the totem blow it.
            if (world.getBlockId(x, y - 1, z) != this.blockID && neighborBlockID == this.blockID)
            {
                world.setBlockToAir(x, y, z);
            }
        }
        else
        {
            boolean drop = false;
            // is the actual block, look for the ghost block above it.
            if (world.getBlockId(x, y + 1, z) != this.blockID && neighborBlockID == this.blockID && !world.isRemote)
            {
                drop = true;
            }
            if (!world.doesBlockHaveSolidTopSurface(x, y - 1, z))
            {
                if (world.getBlockId(x, y + 1, z) == this.blockID)
                {
                    world.setBlockToAir(x, y + 1, z);
                }
            }

            if (drop == true)
            {
                world.setBlockToAir(x, y, z);
                this.dropBlockAsItem(world, x, y, z, metadata, 0);
            }

        }
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rdm) {
        
    }
}
