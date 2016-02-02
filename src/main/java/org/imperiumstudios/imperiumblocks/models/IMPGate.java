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

import java.util.Properties;

import net.minecraft.item.Item;
import org.imperiumstudios.imperiumblocks.BlockHelper;
import org.imperiumstudios.imperiumblocks.ImperiumBlocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class IMPGate extends BlockFenceGate implements IMPGenericBlock {
	
	private BlockHelper helper;
	
	@SideOnly(Side.CLIENT)
	protected IIcon blockIcon;
	
	public IMPGate(Properties blockProps, BlockHelper helper) {
        super();
        this.helper = helper;
		this.setCreativeTab(ImperiumBlocks.fenceTab);
        this.useNeighborBrightness = true;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconReg) {
		try {
			blockIcon = helper.registerIcons(iconReg);
		} catch (BlockHelper.NoSuchTexture noSuchTexture) {
			ImperiumBlocks.log.warn(noSuchTexture.getMessage());
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int metadata) {
	    return blockIcon;
	}

	@Override
	public Item getItem() {
		return null;
	}
}