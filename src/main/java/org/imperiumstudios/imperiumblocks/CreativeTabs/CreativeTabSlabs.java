package org.imperiumstudios.imperiumblocks.CreativeTabs;

import net.minecraft.item.Item;
import org.imperiumstudios.imperiumblocks.ImperiumBlocks;

public class CreativeTabSlabs extends CreativeTabsIMP {
    public CreativeTabSlabs() {
        super("ImperiumSlabs");
    }

    @Override
    public Item getTabIconItem() {
        return ImperiumBlocks.slabIcon;
    }
}
