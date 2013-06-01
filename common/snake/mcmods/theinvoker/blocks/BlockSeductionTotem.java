package snake.mcmods.theinvoker.blocks;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import snake.mcmods.theinvoker.TheInvoker;
import snake.mcmods.theinvoker.items.TIItems;
import snake.mcmods.theinvoker.lib.TIRenderID;
import snake.mcmods.theinvoker.lib.constants.TIGlobal;
import snake.mcmods.theinvoker.lib.constants.TIName;
import snake.mcmods.theinvoker.logic.SeductionTotemMisc;
import snake.mcmods.theinvoker.tileentities.TileSeductionTotem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSeductionTotem extends Block2HeightBase
{

    public BlockSeductionTotem(int id)
    {
        super(id, Material.wood);
        setCreativeTab(TheInvoker.tab);
        this.setHardness(2.5F);
        this.setLightValue(7);
        ghostBlockMetadata = SeductionTotemMisc.GHOST_BLOCK_METADATA;
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
        return TIRenderID.SEDUCTION_TOTEM;
    }

    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileSeductionTotem();
    }

    @Override
    public int idDropped(int metadata, Random par2Random, int fortuneLvl)
    {
        //TODO: drop broken seduction totem instead of the normal one.
        if(metadata==SeductionTotemMisc.NORMAL_METADATA)
            return TIItems.seductionTotem.itemID;
        return 0;
    }

    @Override
    public int damageDropped(int metadata)
    {
        return metadata;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon(TIGlobal.MOD_ID + ":" + TIName.ITEM_SEDUCTION_TOTEM);
    }
    
    /**
     * If you call this before break the block,
     * This will drop seduction totem with damage value inside. 
     */
    public void dropItemSafety()
    {
        
    }

    /**
     * This will drop broken seduction totem.
     */
    @Override
    protected void dropItem(World world, int x, int y, int z, int neighborBlockID, int metadata)
    {
        //TODO:add a wand to remove this safety, if player break it with tool, it will turn into the broken seduction totem.
        if (metadata != SeductionTotemMisc.NORMAL_METADATA)
            return;
        TileSeductionTotem tst = (TileSeductionTotem) world.getBlockTileEntity(x, y, z);
        if (tst != null)
        {
            int dmgVal = SeductionTotemMisc.getDamageDataFromAge(tst.getAge());
            if (dmgVal + 1 < SeductionTotemMisc.MAX_AGE_DMG_VALUE)
            {
                this.dropBlockAsItem(world, x, y, z, dmgVal, 0);
            }
        }
    }
    
    @Override
    public void dropBlockAsItemWithChance(World world, int x, int y, int z, int metadata,
            float chance, int fortune)
    {
        TileSeductionTotem tst = (TileSeductionTotem)world.getBlockTileEntity(x, y+1, z);
        if(tst!=null)
        {
            int dmgVal = SeductionTotemMisc.getDamageDataFromAge(tst.getAge());
            super.dropBlockAsItemWithChance(world, x, y, z, dmgVal, chance, fortune);
        }
    }

    @Override
    protected boolean shouldBreakWhenGhostBlockDestoryed(World world, int x, int y, int z,
            int neighborBlockID, int metadata)
    {
        return metadata!=SeductionTotemMisc.BROKEN_METADATA;
    }

}
