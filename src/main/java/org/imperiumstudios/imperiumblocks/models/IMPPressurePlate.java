package org.imperiumstudios.imperiumblocks.models;

import net.minecraft.block.BlockPressurePlate;
import org.imperiumstudios.imperiumblocks.Helper;
import org.imperiumstudios.imperiumblocks.ImperiumBlocks;

import java.util.Properties;

public class IMPPressurePlate extends BlockPressurePlate {
    public IMPPressurePlate(String name, Properties blockProps) {
        super(ImperiumBlocks.MODID +":"+ name.replace("PressurePlate", "") +"/"+ name.replace("PressurePlate", ""), Helper.getMaterial(blockProps.getProperty("material", "wood")),
                Sensitivity.valueOf(blockProps.getProperty("plateSensitivity", "everything")));

        this.setBlockName(name);
        this.setStepSound(Helper.getSoundType(blockProps.getProperty("sound", "soundTypeStone")));
        this.setHardness(Float.valueOf(blockProps.getProperty("hardness", "2")));
        this.setLightLevel(Float.valueOf(blockProps.getProperty("light", "0.0F")));
        this.setCreativeTab(ImperiumBlocks.miscTab);
    }
}
