package snake.mcmods.theinvoker.blocks;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import snake.mcmods.theinvoker.TheInvoker;
import snake.mcmods.theinvoker.constants.TIGlobal;
import snake.mcmods.theinvoker.items.ItemTotem;
import snake.mcmods.theinvoker.items.TIItems;
import snake.mcmods.theinvoker.tileentities.TileTotem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTotem extends BlockContainer
{
    public BlockTotem(int id)
    {
        super(id, Material.cloth);
        this.setCreativeTab(TheInvoker.tab);
        //this.setUnlocalizedName(name);
        this.setBlockBounds(0.2F, 0F, 0.2F, 0.8F, 1F, 0.8F);
        this.setHardness(1.5F);
        this.setLightValue(7);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister)
    {
        //blockIcon = iconRegister.registerIcon(TIGlobal.MOD_ID + ":" + getUnlocalizedName2());
        blockIcon=null;
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
    public boolean canPlaceBlockOnSide(World par1World, int par2, int par3, int par4, int par5, ItemStack par6ItemStack) {
        return super.canPlaceBlockOnSide(par1World, par2, par3, par4, par5, par6ItemStack);
    }

    @Override
    public boolean canPlaceBlockOnSide(World par1World, int par2, int par3, int par4, int par5) {
        return super.canPlaceBlockOnSide(par1World, par2, par3, par4, par5) && par5 == 1;
    }

    @Override
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
        return super.canPlaceBlockAt(par1World, par2, par3, par4) && super.canPlaceBlockAt(par1World, par2, par3 + 1, par4);
    }

    @Override
    public int onBlockPlaced(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9) {
        return super.onBlockPlaced(par1World, par2, par3, par4, par5, par6, par7, par8, par9);
    }

    @Override
    public int idDropped(int par1, Random par2Random, int par3) {
        switch(par1)
        {
            case ItemTotem.TYPE_SOUL_ATTRACTIVE:
                return TIItems.totemSoulAttractive.itemID;
            default:
                return 0;
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileTotem();
    }
}
