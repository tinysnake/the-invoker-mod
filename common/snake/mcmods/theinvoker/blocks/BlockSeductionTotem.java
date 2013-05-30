package snake.mcmods.theinvoker.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import snake.mcmods.theinvoker.tileentities.TileSeductionTotem;

public class BlockSeductionTotem extends Block2HeightBase
{

    public BlockSeductionTotem(int id)
    {
        super(id, Material.wood);
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileSeductionTotem();
    }

}
