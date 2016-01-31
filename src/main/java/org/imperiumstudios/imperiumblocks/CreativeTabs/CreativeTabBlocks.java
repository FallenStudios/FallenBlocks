package org.imperiumstudios.imperiumblocks.CreativeTabs;

import net.minecraft.item.Item;
import org.imperiumstudios.imperiumblocks.ImperiumBlocks;

public class CreativeTabBlocks extends CreativeTabsIMP {
    public CreativeTabBlocks() {
        super("ImperiumBlocks");
    }

    @Override
    public Item getTabIconItem() {
        return ImperiumBlocks.blockIcon;
    }
}
