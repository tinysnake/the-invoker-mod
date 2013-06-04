package snake.mcmods.theinvoker.blocks;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import snake.mcmods.theinvoker.TheInvoker;
import snake.mcmods.theinvoker.items.ItemEvilTouch;
import snake.mcmods.theinvoker.items.TIItems;
import snake.mcmods.theinvoker.lib.TIRenderID;
import snake.mcmods.theinvoker.lib.constants.TIGlobal;
import snake.mcmods.theinvoker.lib.constants.TIName;
import snake.mcmods.theinvoker.logic.EvilTouchMisc;
import snake.mcmods.theinvoker.logic.seductiontotems.SeductionTotemMisc;
import snake.mcmods.theinvoker.tileentities.TileSeductionTotem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSeductionTotem extends Block2HeightBase
{

    public BlockSeductionTotem(int id)
    {
        super(id, Material.wood);
        setCreativeTab(TheInvoker.tab);
        this.setHardness(7F);
        this.setLightValue(0.5F);
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
        return TIItems.brokenSeductionTotem.itemID;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon(TIGlobal.MOD_ID + ":" + TIName.ITEM_SEDUCTION_TOTEM);
    }

    /**
     * This will drop broken seduction totem.
     */
    @Override
    protected void dropItem(World world, int x, int y, int z, int neighborBlockID, int metadata)
    {

    }

    @Override
    protected boolean shouldBreakWhenGhostBlockDestoryed(World world, int x, int y, int z,
            int neighborBlockID, int metadata)
    {
        return metadata != SeductionTotemMisc.BROKEN_METADATA;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
        ItemStack is = player.getHeldItem();
        if (is != null && is.getItem() instanceof ItemEvilTouch && is.getItemDamage() < EvilTouchMisc.MAX_USAGE_DAMGE_VALUE)
        {
            TileSeductionTotem tst = (TileSeductionTotem) world.getBlockTileEntity(x, y, z);
            if (tst == null)
            {
                return false;
            }
            else
            {
                int ty = y;
                if (tst.getIsGhostBlock() && world.getBlockId(x, y - 1, z) == this.blockID)
                {
                    ty -= 1;
                    tst = (TileSeductionTotem) world.getBlockTileEntity(x, ty, z);
                }
                if (tst.getIsBroken())
                {
                    this.dropBlockAsItem(world, x, y, z, 0, 0);
                }
                else
                {
                    int metadata = SeductionTotemMisc.getDamageDataFromAge(tst.getAge()) + 1;
                    if (metadata < SeductionTotemMisc.MAX_AGE_DMG_VALUE)
                    {
                        ItemStack dropItemStack = new ItemStack(TIItems.seductionTotem.itemID, 1, metadata);
                        this.dropBlockAsItem_do(world, x, y, z, dropItemStack);
                    }
                    else
                    {
                        this.dropBlockAsItem(world, x, y, z, 0, 0);
                    }
                }
                world.setBlockToAir(x, y, z);
                EvilTouchMisc.onEvilTouchUsed(is, player);
                return true;
            }
        }
        return false;
    }
}
