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

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.imperiumstudios.imperiumblocks.ImperiumBlocks;

public class LightItem extends Item {
    public LightItem() {
        setCreativeTab(ImperiumBlocks.miscTab);
        setUnlocalizedName("light");
        setTextureName(String.format("%s:%s", ImperiumBlocks.MODID, "light"));
    }

    //Right-Click should set a block
    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer ply, World world, int x, int y, int z, int side, float bx, float by, float bz) {
        double distance = ply.getDistance(x, y, z);
        if(distance > 5)
            return false;

        switch(side) {
            case 0:
                y--;
                break;
            case 1:
                y++;
                break;
            case 2:
                z--;
                break;
            case 3:
                z++;
                break;
            case 4:
                x--;
                break;
            case 5:
                x++;
                break;
        }

        if(world.getBlock(x, y, z) != Blocks.air)
            return false;

        world.setBlock(x, y, z, ImperiumBlocks.light);

        if(!ply.capabilities.isCreativeMode)
            stack.stackSize--;

        return true;
    }
}
