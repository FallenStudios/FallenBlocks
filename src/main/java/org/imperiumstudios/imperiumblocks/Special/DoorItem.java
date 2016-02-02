/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Imperium Studios <https://imperiumstudios.org>
 * Copyright (c) 2016 garantiertnicht <>
 * Copyright (c) 2016 Kevin Olinger <https://kevinolinger.net>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.imperiumstudios.imperiumblocks.Special;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import org.imperiumstudios.imperiumblocks.ImperiumBlocks;
import org.imperiumstudios.imperiumblocks.models.IMPDoor;


public class DoorItem extends ItemDoor {

    private IMPDoor door;

    @SideOnly(Side.CLIENT)
    private IIcon icon;

    public DoorItem(IMPDoor door) {
        super(door.getMaterial());
        
        this.door = door;
        
        this.setUnlocalizedName(door.getUnlocalizedName().substring(5) + "Item");
        this.setCreativeTab(ImperiumBlocks.miscTab);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg) {
        String textureName = door.getUnlocalizedName().substring(5).replace("Door", "");

        icon = iconReg.registerIcon(ImperiumBlocks.MODID +":"+ textureName +"/doorItem");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int damage) {
        return icon;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer ply, World world, int x, int y, int z, int side, float bx, float by, float bz) {
        if (side != 1) {
            return false;
        } else {
            ++y;

            if (ply.canPlayerEdit(x, y, z, side, stack) && ply.canPlayerEdit(x, y + 1, z, side, stack)) {
                if (!door.canPlaceBlockAt(world, x, y, z)) {
                    return false;
                } else {
                    int i1 = MathHelper.floor_double((double)((ply.rotationYaw + 180.0F) * 4.0F / 360.0F) - 0.5D) & 3;
                    placeDoorBlock(world, x, y, z, i1, door);
                    --stack.stackSize;
                    return true;
                }
            } else {
                return false;
            }
        }
    }
}
