/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Imperium Studios <https://imperiumstudios.org>
 * Copyright (c) 2016 garantiertnicht <>
 * Copyright (c) 2016 Kevin Olinger <https://kevinolinger.net>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

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
