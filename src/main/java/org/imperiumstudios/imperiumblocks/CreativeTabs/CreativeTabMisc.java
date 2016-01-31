package org.imperiumstudios.imperiumblocks.CreativeTabs;

import net.minecraft.item.Item;
import org.imperiumstudios.imperiumblocks.ImperiumBlocks;

public class CreativeTabMisc extends CreativeTabsIMP {
    public CreativeTabMisc() {
        super("ImperiumMisc");
    }

    @Override
    public Item getTabIconItem() {
        return ImperiumBlocks.miscIcon;
    }
}
