/**
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

package org.imperiumstudios.imperiumblocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class Helper {
    public static Material getMaterial(String s) {
        String materialS[]  = {"air", "grass", "ground", "wood", "rock", "iron", "anvil", "water", "lava", "leaves", "plants", "vine", "sponge", "cloth",
                "fire", "sand", "circuits", "carpet", "glass", "redstoneLight", "tnt", "coral", "ice", "packedIce", "snow", "craftedSnow", "cactus",
                "clay", "gourd", "dragonEgg", "portal", "cake", "web", "piston"};

        Material material[] = {Material.air, Material.grass, Material.ground, Material.wood, Material.rock, Material.iron, Material.anvil, Material.water,
                Material.lava, Material.leaves, Material.plants, Material.vine, Material.sponge, Material.cloth, Material.fire, Material.sand,
                Material.circuits, Material.carpet, Material.glass, Material.redstoneLight, Material.tnt, Material.coral, Material.ice, Material.packedIce,
                Material.snow, Material.craftedSnow, Material.cactus, Material.clay, Material.gourd, Material.dragonEgg, Material.portal, Material.cake,
                Material.web, Material.piston};

    	for(int i = 0; i < Math.min(materialS.length, material.length); i++) {
            if (materialS[i].equalsIgnoreCase(s))
                return material[i];
        }

        return Material.rock;
    }

    public static Block.SoundType getSoundType(String s) {
        String soundS[]  = {"stone", "wood", "gravel", "grass", "piston", "metal", "glass", "cloth", "sand", "snow", "ladder"};
        Block.SoundType sound[] = {Block.soundTypeStone, Block.soundTypeWood, Block.soundTypeGravel, Block.soundTypeGrass, Block.soundTypePiston,
                Block.soundTypeMetal, Block.soundTypeGlass, Block.soundTypeCloth, Block.soundTypeSand, Block.soundTypeSnow, Block.soundTypeLadder};

        for(int i = 0; i < Math.min(soundS.length, sound.length); i++) {
            if (soundS[i].equalsIgnoreCase(s))
                return sound[i];
        }

        return Block.soundTypeStone;
    }
    
}