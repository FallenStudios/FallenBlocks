package org.imperiumstudios.imperiumblocks.Special;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class Light extends BlockAir {
    public Light() {
        super();
        setLightLevel(1);
        setBlockName("lightblock");
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        if(world.getBlock(x+1, y, z) != Blocks.air) return;
        if(world.getBlock(x-1, y, z) != Blocks.air) return;
        if(world.getBlock(x, y+1, z) != Blocks.air) return;
        if(world.getBlock(x, y-1, z) != Blocks.air) return;
        if(world.getBlock(x, y, z+1) != Blocks.air) return;
        if(world.getBlock(x, y, z-1) != Blocks.air) return;

        world.setBlock(x, y, z, Blocks.air);
    }
}
