package org.imperiumstudios.imperiumblocks.CreativeTabs;

import net.minecraft.item.Item;
import org.imperiumstudios.imperiumblocks.ImperiumBlocks;

public class CreativeTabWall extends CreativeTabsIMP {
    public CreativeTabWall() {
        super("ImperiumWalls");
    }

    @Override
    public Item getTabIconItem() {
        return ImperiumBlocks.wallIcon;
    }
}
