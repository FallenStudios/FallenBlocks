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
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
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
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
		int l = determineOrientation(par1World, par2, par3, par4, par5EntityLivingBase); 
 		par1World.setBlockMetadataWithNotify(par2, par3, par4, l, 2); 
 		System.out.println("metadata for block: " + par1World.getBlockMetadata(par2, par3, par4)); 

    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconReg) {
        try {
            IIcon icons[] = helper.registerIcons(iconReg, "upside", "downside", "back", "font", "left", "right");
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
    	int k = getOrientation(meta); 
    	//return k > 5 ? this.blockIconUpside : (side == k ? (!isExtended(meta) && this.minX <= 0.0D && this.minY <= 0.0D && this.minZ <= 0.0D && this.maxX >= 1.0D && this.maxY >= 1.0D && this.maxZ >= 1.0D ? this.blockIconUpside : this.blockIcon) : (side == Facing.oppositeSide[k] ? this.blockIconDownside : this.blockIcon)); 

				if (meta == 0 && side == 0)
					return blockIconDownside;
				if (meta == 1 && side == 1)
					return blockIconUpside;
						if (side == 2)
							return blockIconBack;
						if (side == 3)
							return blockIconFont;
						if (side == 4)
							return blockIconLeft;
						if (side == 5)
							return blockIconRight;
				if (meta == 3 && side == 3)
					return blockIconFont;
				if (meta == 2 && side == 2)
					return blockIconBack;
				if (meta == 4 && side == 4)
					return blockIconLeft;
				if (meta == 5 && side == 5)
					return blockIconRight;
				return blockIcon;
    	
    }
    
	public static int determineOrientation(World par0World, int par1, int par2, int par3, EntityLivingBase par4EntityLivingBase) {
		if (MathHelper.abs((float)par4EntityLivingBase.posX - (float)par1) < 2.0F && MathHelper.abs((float)par4EntityLivingBase.posZ - (float)par3) < 2.0F) {
			double d0 = par4EntityLivingBase.posY + 1.82D - (double)par4EntityLivingBase.yOffset;

			if (d0 - (double)par2 > 2.0D) {
				return 1;
			}

			if ((double)par2 - d0 > 0.0D) {
				return 0;
			}
		}
		int l = MathHelper.floor_double((double)(par4EntityLivingBase.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		return l == 0 ? 2 : (l == 1 ? 5 : (l == 2 ? 3 : (l == 3 ? 4 : 0)));
	}

	public static int getOrientation(int par0) {
		return par0 & 7;
	}

	public static boolean isExtended(int par0) {
		return (par0 & 8) != 0;
	}

	public int getRenderType() {
		return 16;
	}

    @Override
    public Item getItem() {
        return null;
    }
}
