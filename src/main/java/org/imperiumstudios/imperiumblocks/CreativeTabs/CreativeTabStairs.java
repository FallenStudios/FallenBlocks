package org.imperiumstudios.imperiumblocks.CreativeTabs;

import net.minecraft.item.Item;
import org.imperiumstudios.imperiumblocks.ImperiumBlocks;

public class CreativeTabStairs extends CreativeTabsIMP {
    public CreativeTabStairs() {
        super("ImperiumStairs");
    }

    @Override
    public Item getTabIconItem() {
        return ImperiumBlocks.stairIcon;
    }
}
