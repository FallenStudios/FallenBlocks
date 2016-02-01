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
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class IMPBlock extends Block {

	ImperiumBlocks Core;
	
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
	
	public IMPBlock(ImperiumBlocks Core, String blockName, Properties blockProps) {		
        super(Helper.getMaterial(blockProps.getProperty("material", "rock")));

        this.Core = Core;
        
        this.setBlockName(blockName);
        this.setStepSound(Helper.getSoundType(blockProps.getProperty("sound", "stone")));
        this.setHardness(Float.valueOf(blockProps.getProperty("hardness", "2")));
        this.setLightLevel(Float.valueOf(blockProps.getProperty("light", "0.0F")));
		this.setCreativeTab(ImperiumBlocks.blockTab);
		if(Float.valueOf(blockProps.getProperty("blast", "-1")) != -1)
			setResistance(Float.valueOf(blockProps.getProperty("blast", "-1")));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister p_149651_1_) {
		try {
            textureName = this.getUnlocalizedName().substring(5).replace("Block", "");
			ImperiumBlocks.log.info(textureName);

			List textures = new ArrayList();
			for(String item: Core.utils.getResourceFolderContent("assets/imperiumblocks/textures/blocks/"+ textureName)) {
				textures.add(item);
				ImperiumBlocks.log.info(item);
			}

			if(textures.contains(textureName +".png")) {
				blockIconBottom = p_149651_1_.registerIcon(ImperiumBlocks.MODID +":"+ textureName +"/"+ textureName);
				blockIconEast = p_149651_1_.registerIcon(ImperiumBlocks.MODID +":"+ textureName +"/"+ textureName);
				blockIconNord = p_149651_1_.registerIcon(ImperiumBlocks.MODID +":"+ textureName +"/"+ textureName);
				blockIconSouth = p_149651_1_.registerIcon(ImperiumBlocks.MODID +":"+ textureName +"/"+ textureName);
				blockIconTop = p_149651_1_.registerIcon(ImperiumBlocks.MODID +":"+ textureName +"/"+ textureName);
				blockIconWest = p_149651_1_.registerIcon(ImperiumBlocks.MODID +":"+ textureName +"/"+ textureName);
			}

			if(textures.contains("bottom.png") && textures.contains("east.png") && textures.contains("north.png") &&
					textures.contains("south.png") && textures.contains("top.png") && textures.contains("west.png")) {
				blockIconBottom = p_149651_1_.registerIcon(ImperiumBlocks.MODID +":"+ textureName +"/bottom");
				blockIconEast = p_149651_1_.registerIcon(ImperiumBlocks.MODID +":"+ textureName +"/east");
				blockIconNord = p_149651_1_.registerIcon(ImperiumBlocks.MODID +":"+ textureName +"/north");
				blockIconSouth = p_149651_1_.registerIcon(ImperiumBlocks.MODID +":"+ textureName +"/south");
				blockIconTop = p_149651_1_.registerIcon(ImperiumBlocks.MODID +":"+ textureName +"/top");
				blockIconWest = p_149651_1_.registerIcon(ImperiumBlocks.MODID +":"+ textureName +"/west");
			}
			
		} catch(Exception ex) { ex.printStackTrace(); }
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
	
}
