package org.imperiumstudios.imperiumblocks.models;


import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;
import org.imperiumstudios.imperiumblocks.Helper;
import org.imperiumstudios.imperiumblocks.ImperiumBlocks;

import java.util.Properties;

public class IMPPane extends BlockPane {

    public IMPPane(String blockName, Properties blockProps) {
        super(ImperiumBlocks.MODID +":"+ blockName.replace("Pane", "") +"/"+ blockName.replace("Pane", ""),
                ImperiumBlocks.MODID +":"+ blockName.replace("Pane", "") +"/paneTop",
                Helper.getMaterial(blockProps.getProperty("material", "glass")),
                Helper.getMaterial(blockProps.getProperty("material", "glass")) != Material.glass);

        this.setBlockName(blockName);
        this.setStepSound(Helper.getSoundType(blockProps.getProperty("sound", "glass")));
        this.setHardness(Float.valueOf(blockProps.getProperty("hardness", "0.5")));
        this.setCreativeTab(ImperiumBlocks.miscTab);
        if(Float.valueOf(blockProps.getProperty("blast", "-1")) != -1)
            setResistance(Float.valueOf(blockProps.getProperty("blast", "-1")));
    }
}
