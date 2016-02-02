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

package org.imperiumstudios.imperiumblocks;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import org.imperiumstudios.imperiumblocks.models.IMPGenericBlock;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BlockHelper {

    private Block block;
    private IMPGenericBlock impGenericBlock;

    private String name;
    private String type;

    private static ImperiumBlocks Core;

    /**
     * The block Type passed to the constructor wasn't found.
     */
    public class InvalidBlockType extends Exception {
        private InvalidBlockType(String msg) {
            super(String.format("%s/%s: %s", name, type, msg), null, false, true);
        }
    }

    /**
     * The Texture wish you liked to register does not exists.
     */
    public class NoSuchTexture extends Exception {
        private NoSuchTexture(String textName) {
            super(String.format("%s/%s: Texture %s is missing!", name, type, textName), null, false, true);
        }
    }

    /**
     * Creates a new Block
     * @param type The type of the Block. The Class will be org.imperiumstudios.imperiumblocks.models.IMP(TYPE)
     *             with type lowercase EXCEPT the first letter.
     * @param name The name of the new Block.
     * @param blockProps The block Properties to apply
     * @throws InvalidBlockType If the type was invalid
     */
    protected BlockHelper(String type, String name, Properties blockProps) throws InvalidBlockType {
        this.name = name;
        this.type = type;

        if(type == null || type.length() < 2)
            throw new InvalidBlockType("This Blocktype has an invalid name!");

        char first = type.charAt(0);
        type = type.toLowerCase().substring(1);

        type = Character.toUpperCase(first) + type;

        Object o;

        Class imp = null;
        try {
            imp = Class.forName("org.imperiumstudios.imperiumblocks.models.IMP" + type);
        } catch (ClassNotFoundException e) {
            throw new InvalidBlockType("There is no such Block Type!");
        }
        Class inter[] = imp.getInterfaces();
        boolean flag = false;

        for(Class a : inter) {
            if(a == IMPGenericBlock.class)
                flag = true;
                break;
            }

        flag = false;
        Class temp = imp;

        while(temp != Object.class) {
            temp = temp.getSuperclass();
            if(temp == Block.class) {
                flag = true; //result is unused
                break;
            }
        }

        if(!flag)
            throw new InvalidBlockType("Class exists and implements IMPGenericBlock, but is no instanceof Block!");

        blockProps.setProperty("name", name + type);

        try {
            o = imp.getConstructor(Properties.class, this.getClass()).newInstance(blockProps, this);
        } catch (InstantiationException e) {
            throw new InvalidBlockType("Class exists and implements IMPGenericBlock, but the construction failed!");
        } catch (IllegalAccessException e) {
            throw new InvalidBlockType("Class exists and implements IMPGenericBlock, but the construction can not called (change to public)!");
        } catch (InvocationTargetException e) {
            if(e.getCause() != null) //Should never be false, but be sure
                e.getCause().printStackTrace();
            throw new InvalidBlockType("Class exists and implements IMPGenericBlock, but the construction thrown an error! See above.");
        } catch (NoSuchMethodException e) {
            throw new InvalidBlockType("Class exists and implements IMPGenericBlock, but has no matchin Constructor (Properties, BlockHelper)!");
        }


        impGenericBlock = (IMPGenericBlock) o;
        block = (Block) o;

        block.setBlockName(name + type);
        block.setStepSound(BlockHelper.getSoundType(blockProps.getProperty("sound", "stone")));
        block.setHardness(Float.valueOf(blockProps.getProperty("hardness", "2")));
        block.setLightLevel(Float.valueOf(blockProps.getProperty("light", "0.0F")));

        if(block.renderAsNormalBlock())
            block.setLightLevel(Float.valueOf(blockProps.getProperty("light", "0.0F")));

        GameRegistry.registerBlock(block , name + type);
    }

    /**
     * Gets the full qualified Texture name. Will call {@link BlockHelper#getTextureName(String)} with the block name.
     * @deprecated You should not call this Variant if not nercassary. Use {@link BlockHelper#getTextureName()} instead.
     * @return A full qualified Texture name
     */
    @Deprecated
    public String getTextureNameNoExc() {
        return getTextureNameNoExc(name);
    }

    /**
     * Gets the full qualified Texture name for a subtexture.
     * @deprecated You should not call this Variant if not nercassary. Use {@link BlockHelper#getTextureName(String)} instead.
     * @param subName The name of the subtexture
     * @return A full qualified Texture name
     */
    @Deprecated
    public String getTextureNameNoExc(String subName) {
        return ImperiumBlocks.MODID +":"+ name +"/"+ subName;
    }

    /**
     * Gets the full qualified Texture name. Will call {@link BlockHelper#getTextureName(String)} with the block name.
     * @return A full qualified Texture name
     * @throws NoSuchTexture If the texture does not exist
     */
    public String getTextureName() throws NoSuchTexture {
        return getTextureName(name);
    }

    /**
     * Gets the full qualified Texture name for a subtexture.
     * @param subName The name of the subtexture
     * @return A full qualified Texture name
     * @throws NoSuchTexture If the texture does not exist
     */
    public String getTextureName(String subName) throws NoSuchTexture {
        List textures = new ArrayList();
        try {
            for(String item: Core.utils.getResourceFolderContent("assets/imperiumblocks/textures/blocks/" + name)) textures.add(item);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        if(textures.contains(subName +".png")) return ImperiumBlocks.MODID +":"+ name +"/"+ subName;
        else throw new NoSuchTexture(ImperiumBlocks.MODID +":"+ name +"/"+ subName);
    }

    /**
     * Register one Texture.
     * @param iconReg The IIcon register
     * @return The redy-to-use-and-registered IIcon
     * @throws NoSuchTexture If the texure is missing.
     */
    public IIcon registerIcons(IIconRegister iconReg) throws NoSuchTexture {
        return iconReg.registerIcon(getTextureName());
    }

    /**
     * Register multiple textures. If one of the subName is not available, it will try to fill all with BLOCKNAME.png
     * @param iconReg The IIcon register
     * @param subNames All subnames in order
     * @return The registered Icons
     * @throws NoSuchTexture If one ore more subName-textures fails loading AND there also is no BLOCKNAME.png
     */
    public IIcon[] registerIcons(IIconRegister iconReg, String... subNames) throws NoSuchTexture {
        IIcon icons[] = new IIcon[subNames.length];
        int i = 0;
        try {
            for(String subName : subNames) {
                icons[i++] = iconReg.registerIcon(getTextureName(subName));
            }
        } catch (NoSuchTexture noSuchTexture) {
            for(; i < icons.length; i++) {
                icons[i++] = iconReg.registerIcon(getTextureName());
            }
        }

        return icons;
    }

    /**
     * Gets the Item for a block.
     * @return An Item. Who is surprised?
     */
    public Item getItem() {
        return impGenericBlock.getItem() == null ? Item.getItemFromBlock(block) : impGenericBlock.getItem();
    }

    /**
     * Looks up an Material from a given String.
     * @param s The String-Representation of the Material.
     * @return Even more surprisingly, an Material!
     */
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

    /**
     * Looks up an SoundType from a given String.
     * @param s The String-Representation of the SoundType.
     * @return A small bananna ridden by a giant ape, falling because of damn grass.
     */
    public static Block.SoundType getSoundType(String s) {
        String soundS[] = {"stone", "wood", "gravel", "grass", "piston", "metal", "glass", "cloth", "sand", "snow", "ladder"};
        Block.SoundType sound[] = {Block.soundTypeStone, Block.soundTypeWood, Block.soundTypeGravel, Block.soundTypeGrass, Block.soundTypePiston,
                Block.soundTypeMetal, Block.soundTypeGlass, Block.soundTypeCloth, Block.soundTypeSand, Block.soundTypeSnow, Block.soundTypeLadder};

        for (int i = 0; i < Math.min(soundS.length, sound.length); i++) {
            if (soundS[i].equalsIgnoreCase(s))
                return sound[i];
        }

        return Block.soundTypeStone;
    }

    /**
     * Sets the Core. Seemnd to be better then having another var every call.
     * @param newCore ImperiumBlocks, wish is *HOPEFULLY* calling this.
     */
    public static void init(ImperiumBlocks newCore) {
        Core = newCore;
    }
}