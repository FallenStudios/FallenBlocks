package org.imperiumstudios.imperiumblocks.models;

import net.minecraft.block.BlockPressurePlate;
import org.imperiumstudios.imperiumblocks.Helper;
import org.imperiumstudios.imperiumblocks.ImperiumBlocks;

import java.util.Properties;

public class IMPPressurePlate extends BlockPressurePlate {
    public IMPPressurePlate(String blockName, Properties blockProps) {
        super(ImperiumBlocks.MODID +":"+ blockName.replace("PressurePlate", "") +"/"+ blockName.replace("PressurePlate", ""), Helper.getMaterial(blockProps.getProperty("material", "wood")),
                Sensitivity.valueOf(blockProps.getProperty("plateSensitivity", "everything")));

        this.setBlockName(blockName);
        this.setStepSound(Helper.getSoundType(blockProps.getProperty("sound", "stone")));
        this.setHardness(Float.valueOf(blockProps.getProperty("hardness", "2")));
        this.setLightLevel(Float.valueOf(blockProps.getProperty("light", "0.0F")));
        this.setCreativeTab(ImperiumBlocks.miscTab);
        if(Float.valueOf(blockProps.getProperty("blast", "-1")) != -1)
            setResistance(Float.valueOf(blockProps.getProperty("blast", "-1")));
    }
}
