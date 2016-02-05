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
import net.minecraft.block.BlockPistonBase;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import org.imperiumstudios.imperiumblocks.BlockHelper;
import org.imperiumstudios.imperiumblocks.ImperiumBlocks;

import java.util.Properties;

public class IMPGlassturnable extends IMPGlass implements IMPGenericBlock {
    public IMPGlassturnable(Properties blockProps, BlockHelper helper) {
        super(blockProps, helper);
        //TODO 1.8: Enable Metadata
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(world, x, y, z, placer, stack);
        int meta = BlockPistonBase.determineOrientation(world, x, y, z, placer); //Im not going to use REINVENTTHEWHEEL (TM)
        world.setBlockMetadataWithNotify(x, y, z, meta, 2);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconReg) {
        try {
            IIcon icons[] = super.helper.registerIcons(iconReg, "downside", "right", "back", "font", "upside", "left");

            super.blockIconBottom = icons[0]; //Down
            super.blockIconEast =   icons[1]; //Right
            super.blockIconNord =   icons[2]; //Back
            super.blockIconSouth =  icons[3]; //Font
            super.blockIconTop =    icons[4]; //Up
            super.blockIconWest =   icons[5]; //Left
        } catch (BlockHelper.NoSuchTexture exc) {
            ImperiumBlocks.log.warn(exc.getMessage());
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta) {
        side -= meta + 2;
        while(side < 0)
            side += 6;
        while(side > 5) {
            super.helper.warn("Illegal side for Meta " + meta);
            side -= 6;
        }

        return super.getIcon(side, 0);
    }
}
