package org.imperiumstudios.imperiumblocks.Special;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.imperiumstudios.imperiumblocks.ImperiumBlocks;

public class LightItem extends Item {
    public LightItem() {
        setCreativeTab(CreativeTabs.tabDecorations);
        setUnlocalizedName("light");
        setTextureName(String.format("%s:%s", ImperiumBlocks.MODID, "light"));
    }

    //Right-Click should set a block
    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer ply, World world, int x, int y, int z, int side, float bx, float by, float bz) {
        double distance = ply.getDistance(x, y, z);
        if(distance > 5)
            return false;

        switch(side) {
            case 0:
                y--;
                break;
            case 1:
                y++;
                break;
            case 2:
                z--;
                break;
            case 3:
                z++;
                break;
            case 4:
                x--;
                break;
            case 5:
                x++;
                break;
        }

        world.setBlock(x, y, z, ImperiumBlocks.light);

        if(!ply.capabilities.isCreativeMode)
            stack.stackSize--;

        return true;
    }
}
