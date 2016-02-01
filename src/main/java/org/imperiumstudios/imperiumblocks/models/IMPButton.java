package org.imperiumstudios.imperiumblocks.models;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockButton;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import org.imperiumstudios.imperiumblocks.Helper;
import org.imperiumstudios.imperiumblocks.ImperiumBlocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class IMPButton extends BlockButton {
    private ImperiumBlocks Core;

    public IMPButton(ImperiumBlocks Core, String blockName, Properties blockProps) {
        super(Boolean.valueOf(blockProps.getProperty("buttonWooden", "false")));
        this.setCreativeTab(ImperiumBlocks.miscTab);
        this.setBlockName(blockName);
        this.setStepSound(Helper.getSoundType(blockProps.getProperty("sound", "soundTypeStone")));
        this.setHardness(Float.valueOf(blockProps.getProperty("hardness", "2")));
        this.setLightLevel(Float.valueOf(blockProps.getProperty("light", "0.0F")));

        this.Core = Core;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconReg) {
        try {
            textureName = this.getUnlocalizedName().substring(5).replace("Button", "");

            List textures = new ArrayList();
            for(String item: Core.utils.getResourceFolderContent("assets/imperiumblocks/textures/blocks/"+ textureName)) textures.add(item);

            if(textures.contains(textureName +".png")) blockIcon = iconReg.registerIcon(ImperiumBlocks.MODID +":"+ textureName +"/"+ textureName);
            else if(textures.contains("top.png")) blockIcon = iconReg.registerIcon(ImperiumBlocks.MODID +":"+ textureName +"/top.png");
        } catch(Exception ex) { ex.printStackTrace(); }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int metadata) {
        return blockIcon;
    }
}
