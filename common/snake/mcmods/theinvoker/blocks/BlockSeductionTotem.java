package snake.mcmods.theinvoker.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import snake.mcmods.theinvoker.TheInvoker;
import snake.mcmods.theinvoker.items.TIItems;
import snake.mcmods.theinvoker.lib.TIRenderID;
import snake.mcmods.theinvoker.lib.TotemType;
import snake.mcmods.theinvoker.lib.constants.TIGlobal;
import snake.mcmods.theinvoker.lib.constants.TIName;
import snake.mcmods.theinvoker.logic.SeductionTotemMisc;
import snake.mcmods.theinvoker.tileentities.TileSeductionTotem;

public class BlockSeductionTotem extends Block2HeightBase
{

    public BlockSeductionTotem(int id)
    {
        super(id, Material.wood);
        setCreativeTab(TheInvoker.tab);
        this.setHardness(2.5F);
        this.setLightValue(7);
        ghostBlockMetadata = 0;
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
        return TIItems.seductionTotem.itemID;
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

    @Override
    protected void dropItem(World world, int x, int y, int z, int neighborBlockID)
    {
        TileSeductionTotem tst = (TileSeductionTotem) world.getBlockTileEntity(x, y, z);
        if (tst != null)
        {
            int metadata = SeductionTotemMisc.getMetadataFromAge(tst.getAge());
            if (!SeductionTotemMisc.isGhostBlock(metadata))
            {
                this.dropBlockAsItem(world, x, y, z, SeductionTotemMisc.MAX_AGE_METADATA - metadata, 0);
            }
        }
    }

}
