package snake.mcmods.theinvoker.blocks;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import snake.mcmods.theinvoker.constants.TIGlobal;
import snake.mcmods.theinvoker.constants.TIName;
import snake.mcmods.theinvoker.constants.TIRenderID;
import snake.mcmods.theinvoker.items.ItemTotem;
import snake.mcmods.theinvoker.items.TIItems;
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
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon(TIGlobal.MOD_ID+":"+TIName.BLOCK_TOTEM);
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
        if(metadata==ItemTotem.TotemType.TYPE_SOUL_ATTRACTIVE.toMetadata())
            return TIItems.totemSoulAttractive.itemID;
        return 0;
    }

    @Override
    public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side)
    {
        return super.canPlaceBlockOnSide(world, x, y, z, side);
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z)
    {
        return super.canPlaceBlockAt(world, x, y, z) && super.canPlaceBlockAt(world, x, y + 1, z);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving entityLiving,
            ItemStack itemStack)
    {
        TileTotem te = (TileTotem) world.getBlockTileEntity(x, y, z);
        int metaData = world.getBlockMetadata(x, y, z);
        te.setIsGhostBlock(metaData == ItemTotem.TotemType.TYPE_GHOST.toMetadata());
        int face = MathHelper
                .floor_double((double) (entityLiving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        switch (face)
        {
            case 0:
                te.setDirection((byte)2);
                break;
            case 1:
                te.setDirection((byte)5);
                break;
            case 2:
                te.setDirection((byte)3);
                break;
            case 3:
                te.setDirection((byte)4);
                break;
        }
    }
    
    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int neighborBlockID)
    {
        int metadata = world.getBlockMetadata(x, y, z);
        if(metadata==ItemTotem.TotemType.TYPE_GHOST.toMetadata())
        {
            //is ghost block, look for the totem blow it.
            if(world.getBlockId(x, y-1,z)!=this.blockID&&neighborBlockID==this.blockID)
            {
                world.setBlockToAir(x, y, z);
            }
        }
        else
        {
            boolean drop = false;
            //is the actual block, look for the ghost block above it.
            if(world.getBlockId(x, y+1,z)!=this.blockID&&neighborBlockID==this.blockID&&!world.isRemote)
            {
                drop=true;
            }
            if(!world.doesBlockHaveSolidTopSurface(x,y-1,z))
            {
                if(world.getBlockId(x, y+1, z)==this.blockID)
                {
                    world.setBlockToAir(x, y+1, z);
                }
            }
            
            if(drop==true)
            {
                world.setBlockToAir(x, y, z);
                this.dropBlockAsItem(world, x, y, z, metadata, 0);
            }
            
        }
    }
}
