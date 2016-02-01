/**
 * The MIT License (MIT)
 * 
 * Copyright (c) 2016 Imperium Studios <https://imperiumstudios.org>
 * Copyright (c) 2016 Kevin Olinger <https://kevinolinger.net>
 * Copyright (c) 2016 garantiertnicht <>
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

import org.imperiumstudios.imperiumblocks.Helper;
import org.imperiumstudios.imperiumblocks.ImperiumBlocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockWall;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
public class IMPWall extends BlockWall {

	ImperiumBlocks Core;
	
	@SideOnly(Side.CLIENT)
	protected IIcon blockIcon;
	
	public IMPWall(ImperiumBlocks Core, String blockName, Properties blockProps) {		
        super(Blocks.sandstone);

        this.Core = Core;
        
        this.setBlockName(blockName);
        this.setStepSound(Helper.getSoundType(blockProps.getProperty("sound", "stone")));
        this.setHardness(Float.valueOf(blockProps.getProperty("hardness", "2")));
		this.setCreativeTab(ImperiumBlocks.wallTab);
		if(Float.valueOf(blockProps.getProperty("blast", "-1")) != -1)
			setResistance(Float.valueOf(blockProps.getProperty("blast", "-1")));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List list) {
		list.add(new ItemStack(itemIn, 1, 0));
	};

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister p_149651_1_) {
		try {
			textureName = this.getUnlocalizedName().substring(5).replace("Wall", "");
			
			List textures = new ArrayList();
			for(String item: Core.utils.getResourceFolderContent("assets/imperiumblocks/textures/blocks/"+ textureName)) textures.add(item);

			if(textures.contains(textureName +".png")) blockIcon = p_149651_1_.registerIcon(ImperiumBlocks.MODID +":"+ textureName +"/"+ textureName);
		} catch(Exception ex) { ex.printStackTrace(); }
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int metadata) {
	    return blockIcon;
	}
	
	@Override
	public boolean canConnectWallTo(IBlockAccess blockAccess, int x, int y, int z) {
		Block block = blockAccess.getBlock(x, y, z);
		boolean su = super.canConnectWallTo(blockAccess, x, y, z);
		return block instanceof BlockFenceGate || su;
	}
	
    public boolean canPlaceTorchOnTop(World world, int x, int y, int z) {
		return true;
	}
}

