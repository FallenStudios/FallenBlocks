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

package org.imperiumstudios.imperiumblocks.models;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockDoor;
import net.minecraft.client.renderer.IconFlipped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.imperiumstudios.imperiumblocks.BlockHelper;
import org.imperiumstudios.imperiumblocks.ImperiumBlocks;
import org.imperiumstudios.imperiumblocks.Special.DoorItem;

import java.util.Properties;
import java.util.Random;


public class IMPDoor extends BlockDoor implements IMPGenericBlock {

    private BlockHelper helper;

    @SideOnly(Side.CLIENT)
    private IIcon[] upper;
    @SideOnly(Side.CLIENT)
    private IIcon[] lower;

    public String name;
    private DoorItem item;

    public IMPDoor(Properties blockProps, BlockHelper helper) {
        super(BlockHelper.getMaterial(blockProps.getProperty("material", "rock")));
        this.helper = helper;
        this.useNeighborBrightness = true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconReg)
    {
        textureName = this.getUnlocalizedName().substring(5).replace("Door", "");

        this.upper = new IIcon[2];
        this.lower = new IIcon[2];

        try {
            IIcon icons[] = helper.registerIcons(iconReg, "doorUpper", "doorLower");

            this.upper[0] = icons[0];
            this.lower[0] = icons[1];
        } catch (BlockHelper.NoSuchTexture noSuchTexture) {
            ImperiumBlocks.log.warn(noSuchTexture.getMessage());
            return;
        }
        this.upper[1] = new IconFlipped(this.upper[0], true, false);
        this.lower[1] = new IconFlipped(this.lower[0], true, false);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int p_149691_1_, int p_149691_2_)
    {
        return this.lower[0];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess blockAccess, int x, int y, int z, int side)
    {
        if (side != 1 && side != 0)
        {
            int i1 = this.func_150012_g(blockAccess, x, y, z);
            int j1 = i1 & 3;
            boolean flag = (i1 & 4) != 0;
            boolean flag1 = false;
            boolean flag2 = (i1 & 8) != 0;

            if (flag)
            {
                if (j1 == 0 && side == 2)
                {
                    flag1 = !flag1;
                }
                else if (j1 == 1 && side == 5)
                {
                    flag1 = !flag1;
                }
                else if (j1 == 2 && side == 3)
                {
                    flag1 = !flag1;
                }
                else if (j1 == 3 && side == 4)
                {
                    flag1 = !flag1;
                }
            }
            else
            {
                if (j1 == 0 && side == 5)
                {
                    flag1 = !flag1;
                }
                else if (j1 == 1 && side == 3)
                {
                    flag1 = !flag1;
                }
                else if (j1 == 2 && side == 4)
                {
                    flag1 = !flag1;
                }
                else if (j1 == 3 && side == 2)
                {
                    flag1 = !flag1;
                }

                if ((i1 & 16) != 0)
                {
                    flag1 = !flag1;
                }
            }

            return flag2 ? this.upper[flag1?1:0] : this.lower[flag1?1:0];
        }
        else
        {
            return this.lower[0];
        }
    }

    public void setDoorItem(DoorItem item) {
        this.item = item;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Item getItem(World world, int x, int y, int z) {
        return item;
    }

    public Item getItemDropped(int p_149650_1_, Random rand, int p_149650_3_) {
        return (p_149650_1_ & 8) != 0 ? null : item;
    }

    @Override
    public Item getItem() {
        DoorItem item = new DoorItem(this);
        this.setDoorItem(item);
        return item;
    }
}
