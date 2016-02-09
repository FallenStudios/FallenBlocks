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
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import org.imperiumstudios.imperiumblocks.BlockHelper;
import org.imperiumstudios.imperiumblocks.ImperiumBlocks;

import java.util.Properties;

public class IMPGlassturnable extends BlockGlass implements IMPGenericBlock {
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

    private BlockHelper helper;

    public IMPGlassturnable(Properties blockProps, BlockHelper helper) {
        super(BlockHelper.getMaterial(blockProps.getProperty("material", "glass")), false);

        this.helper = helper;
        this.setCreativeTab(ImperiumBlocks.blockTab);
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
            IIcon icons[] = helper.registerIcons(iconReg, "downside", "right", "back", "font", "upside", "left");

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
            return blockIconFont;

        if(side == Facing.oppositeSide[meta])
            return blockIconBack;

        int insertNameHere[] = {2,3,4,5,3,2};
        //System.out.print('.');
        int m = insertNameHere[meta];

        if(side == m)
            return blockIconRight;

        if(side == Facing.oppositeSide[m])
            return blockIconLeft;

        int insertNameHere2[] = {4,5,0,0,5,4};

        if(side == insertNameHere2[meta])
            return blockIconUpside;

        return blockIconDownside;
    }

    @Override
    public Item getItem() {
        return null;
    }
}
