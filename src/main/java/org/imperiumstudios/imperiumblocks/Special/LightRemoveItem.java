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

public class LightRemoveItem extends Item {
    public LightRemoveItem() {
        setCreativeTab(ImperiumBlocks.miscTab);
        setUnlocalizedName("light_off");
        setTextureName(String.format("%s:%s", ImperiumBlocks.MODID, "light_off"));
    }

    //Right-Click will remove all Light Blocks in a 7x7x7 square.
    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer ply, World world, int x, int y, int z, int side, float bx, float by, float bz) {
        onItemRightClick(stack, world, ply);
        return true;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer ply) {
        double px = ply.posX;
        double py = ply.posY;
        double pz = ply.posZ;

        boolean removed = false;

        for(int i = (int) px - 7; i < px + 8; i++)
            for(int j = (int) py - 7; j < py + 8; j++)
                for(int k = (int) pz - 7; k < pz + 8; k++)
                    if(world.getBlock(i, j, k) == ImperiumBlocks.light) {
                        world.setBlock(i, j, k, Blocks.air);
                        removed = true;
                    }

        //Remove item in Survival ONLY when at least one block was removed.
        if(!ply.capabilities.isCreativeMode && removed)
            stack.stackSize--;

        return stack;
    }
}
