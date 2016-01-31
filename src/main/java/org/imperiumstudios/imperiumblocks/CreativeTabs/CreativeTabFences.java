package org.imperiumstudios.imperiumblocks.CreativeTabs;

import net.minecraft.item.Item;
import org.imperiumstudios.imperiumblocks.ImperiumBlocks;

public class CreativeTabFences extends CreativeTabsIMP {
    public CreativeTabFences() {
        super("ImperiumFences");
    }

    @Override
    public Item getTabIconItem() {
        return ImperiumBlocks.fenceIcon;
    }
}
