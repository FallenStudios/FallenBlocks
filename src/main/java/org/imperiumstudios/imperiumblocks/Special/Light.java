package org.imperiumstudios.imperiumblocks.Special;

import net.minecraft.block.BlockAir;

public class Light extends BlockAir {
    public Light() {
        super();
        setLightLevel(1);
        setBlockName("lightblock");
    }
}
