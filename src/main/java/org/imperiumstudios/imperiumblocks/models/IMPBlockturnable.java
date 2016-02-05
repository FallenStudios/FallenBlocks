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
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import org.imperiumstudios.imperiumblocks.BlockHelper;
import org.imperiumstudios.imperiumblocks.ImperiumBlocks;

import java.util.Properties;

public class IMPBlockturnable extends Block implements IMPGenericBlock {

    BlockHelper helper;

    @SideOnly(Side.CLIENT)
    protected IIcon blockIconUpside;
    @SideOnly(Side.CLIENT)
    protected IIcon blockIconDownside;
    @SideOnly(Side.CLIENT)
    protected IIcon blockIconBack;
    @SideOnly(Side.CLIENT)
    protected IIcon blockIconFont;
    @SideOnly(Side.CLIENT)
    protected IIcon blockIconLeft;
    @SideOnly(Side.CLIENT)
    protected IIcon blockIconRight;

    public IMPBlockturnable(Properties blockProps, BlockHelper helper) {
        super(BlockHelper.getMaterial(blockProps.getProperty("material", "rock")));

        this.helper = helper;
        this.setCreativeTab(ImperiumBlocks.blockTab);
        //TODO 1.8: Enable Meta
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
            IIcon icons[] = helper.registerIcons(iconReg, "upside", "downside", "back", "font", "lefr", "right");
            blockIconUpside     = icons[0];
            blockIconDownside   = icons[1];
            blockIconBack       = icons[2];
            blockIconFont       = icons[3];
            blockIconLeft       = icons[4];
            blockIconRight      = icons[5];
        } catch (BlockHelper.NoSuchTexture exc) {
            ImperiumBlocks.log.warn(exc.getMessage());
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta) {
        if(side == meta)
            return blockIconBack;

        int m;

        m = meta + 1;
        if(m > 5) m -= 6;
        if(side == m)
            return blockIconFont;

        return blockIconLeft;
    }

    @Override
    public Item getItem() {
        return null;
    }
}
