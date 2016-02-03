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

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import net.minecraft.item.Item;
import org.imperiumstudios.imperiumblocks.BlockHelper;
import org.imperiumstudios.imperiumblocks.ImperiumBlocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockGlass;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class IMPGlass extends BlockGlass implements IMPGenericBlock {
	
	private BlockHelper helper;
	
	@SideOnly(Side.CLIENT)
	protected IIcon blockIconBottom;
	@SideOnly(Side.CLIENT)
	protected IIcon blockIconEast;
	@SideOnly(Side.CLIENT)
	protected IIcon blockIconNord;
	@SideOnly(Side.CLIENT)
	protected IIcon blockIconSouth;
	@SideOnly(Side.CLIENT)
	protected IIcon blockIconTop;
	@SideOnly(Side.CLIENT)
	protected IIcon blockIconWest;

	public IMPGlass(Properties blockProps, BlockHelper helper) {
        super(BlockHelper.getMaterial(blockProps.getProperty("material", "glass")), false);

        this.helper = helper;
		this.setCreativeTab(ImperiumBlocks.blockTab);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconReg) {
		try {
			IIcon icons[] = helper.registerIcons(iconReg, "bottom", "east", "north", "south", "top", "west");

			blockIconBottom = icons[0];
			blockIconEast =   icons[1];
			blockIconNord =   icons[2];
			blockIconSouth =  icons[3];
			blockIconTop =    icons[4];
			blockIconWest =   icons[5];
		} catch (BlockHelper.NoSuchTexture exc) {
			ImperiumBlocks.log.warn(exc.getMessage());
		}
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int metadata) {
	    switch(side) {
	      case 0:
	        return blockIconBottom;
	      case 1:
	        return blockIconTop;
	      case 2:
	    	  return blockIconNord;
	      case 3:
	    	  return blockIconWest;
	      case 4:
	    	  return blockIconSouth;
	      case 5:
	    	  return blockIconEast;
	    }
	    
	    throw new IllegalArgumentException("Illegal side: "+ side);
	}

	@Override
	public Item getItem() {
		return null;
	}
}
