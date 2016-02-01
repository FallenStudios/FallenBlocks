package org.imperiumstudios.imperiumblocks.Special;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.imperiumstudios.imperiumblocks.ImperiumBlocks;

public class LightRemoveItem extends Item {
    public LightRemoveItem() {
        setCreativeTab(ImperiumBlocks.miscTab);
        setUnlocalizedName("light_off");
        setTextureName(String.format("%s:%s", ImperiumBlocks.MODID, "light_off"));
    }

    //Right-Click will remove all Light Blocks in a 7x7x7 square.
    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer ply, World world, int x, int y, int z, int side, float bx, float by, float bz) {
        onItemRightClick(stack, world, ply);
        return true;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer ply) {
        double px = ply.posX;
        double py = ply.posY;
        double pz = ply.posZ;

        boolean removed = false;

        for(int i = (int) px - 7; i < px + 8; i++)
            for(int j = (int) py - 7; j < py + 8; j++)
                for(int k = (int) pz - 7; k < pz + 8; k++)
                    if(world.getBlock(i, j, k) == ImperiumBlocks.light) {
                        world.setBlock(i, j, k, Blocks.air);
                        removed = true;
                    }

        //Remove item in Survival ONLY when at least one block was removed.
        if(!ply.capabilities.isCreativeMode && removed)
            stack.stackSize--;

        return stack;
    }
}
