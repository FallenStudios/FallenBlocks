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
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.util.Properties;
import java.util.Scanner;

import net.minecraft.item.Item;
import org.imperiumstudios.imperiumblocks.CreativeTabs.*;
import org.imperiumstudios.imperiumblocks.Special.DoorItem;
import org.imperiumstudios.imperiumblocks.Special.Light;
import org.imperiumstudios.imperiumblocks.Special.LightItem;
import org.imperiumstudios.imperiumblocks.Special.LightRemoveItem;
import org.imperiumstudios.imperiumblocks.models.*;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = ImperiumBlocks.MODID, version = ImperiumBlocks.VERSION, name = ImperiumBlocks.NAME)
public class ImperiumBlocks {
	
	public Utils utils = new Utils();

    public static Item blockIcon;
	public static CreativeTabs blockTab = new CreativeTabBlocks();

    public static Item stairIcon;
    public static CreativeTabs stairTab = new CreativeTabStairs();

    public static Item slabIcon;
    public static CreativeTabs slabTab = new CreativeTabSlabs();

    public static Item fenceIcon;
    public static CreativeTabs fenceTab = new CreativeTabFences();

    public static Item wallIcon;
    public static CreativeTabs wallTab = new CreativeTabWall();

    public static Item miscIcon;
    public static CreativeTabs miscTab = new CreativeTabMisc();

	
	public static final String NAME = "Imperium Blocks"; //Used in the GUI parts of the mod
	public static final String MODID = "imperiumblocks";
	public static final String VERSION = "1.0";

	public static Light light;

	public static org.apache.logging.log4j.Logger log;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e) throws IOException {
		log = e.getModLog();
		patchJar();
	}
    
	@Mod.EventHandler
	public void init(FMLInitializationEvent e) throws Exception {
		Properties blockProps = new Properties();
        Properties tabProps = new Properties();
        tabProps.load(this.getClass().getResourceAsStream("/assets/" + MODID.toLowerCase() + "/tabs.properties"));

		InputStream inputStream;
        
        int blockNum = 0;

		for(String blockFile: utils.getResourceFolderContent("assets/imperiumblocks/blocks/")) {
			if(blockFile.equals("")) continue;		
			
			inputStream = getClass().getClassLoader().getResourceAsStream("assets/imperiumblocks/blocks/"+ blockFile);
			
			if(inputStream != null) {
				blockProps.load(inputStream);
				String[] blockTypes = blockProps.getProperty("types", "block").split(",");
                String name = blockFile.replace(".properties", "");
				
				for(String blockType: blockTypes) {
					Block block = null;
                    Item item = null;
					
					if(blockType.equalsIgnoreCase("block")) block = new IMPBlock(this, name + "Block", blockProps);
					else if(blockType.equalsIgnoreCase("wall")) block = new IMPWall(this, name + "Wall", blockProps);
					else if(blockType.equalsIgnoreCase("glass")) block = new IMPGlass(this, name + "Glass", blockProps);
					else if(blockType.equalsIgnoreCase("fence")) block = new IMPFence(this, name + "Fence", blockProps);
					else if(blockType.equalsIgnoreCase("gate")) block = new IMPGate(this, name + "Gate", blockProps);
					else if(blockType.equalsIgnoreCase("slab")) block = new IMPSlab(this, name + "Slab", blockProps);
					else if(blockType.equalsIgnoreCase("stair")) block = new IMPStair(this, name + "Stair", blockProps);
					else if(blockType.equalsIgnoreCase("trapdoor")) block = new IMPTrapdoor(this, name + "Trapdoor", blockProps);
                    else if(blockType.equalsIgnoreCase("door")) {
                        block = new IMPDoor(this, name + "Door", blockProps);
                        item = new DoorItem((IMPDoor) block);
                        ((IMPDoor) block).setDoorItem((DoorItem) item);
                    } else if(blockType.equalsIgnoreCase("PressurePlate")) block = new IMPPressurePlate(name + "PressurePlate", blockProps);
                    else if(blockType.equalsIgnoreCase("Button")) block = new IMPButton(this, name + "Button", blockProps);
                    else if(blockType.equalsIgnoreCase("Pane")) block = new IMPPane(name + "Pane", blockProps);
					else continue;
					
					GameRegistry.registerBlock(block, block.getUnlocalizedName().substring(5));
                    if(item != null)
                        GameRegistry.registerItem(item, item.getUnlocalizedName().substring(5));
                    
                    if(String.format("%s/%s", name, blockType).equalsIgnoreCase(tabProps.getProperty("block")))
                        blockIcon = (item == null ? Item.getItemFromBlock(block) : item);
                    if(String.format("%s/%s", name, blockType).equalsIgnoreCase(tabProps.getProperty("stair")))
                        stairIcon = (item == null ? Item.getItemFromBlock(block) : item);
                    if(String.format("%s/%s", name, blockType).equalsIgnoreCase(tabProps.getProperty("slab")))
                        slabIcon = (item == null ? Item.getItemFromBlock(block) : item);
                    if(String.format("%s/%s", name, blockType).equalsIgnoreCase(tabProps.getProperty("fence")))
                        fenceIcon = (item == null ? Item.getItemFromBlock(block) : item);
                    if(String.format("%s/%s", name, blockType).equalsIgnoreCase(tabProps.getProperty("wall")))
                        wallIcon = (item == null ? Item.getItemFromBlock(block) : item);
                    if(String.format("%s/%s", name, blockType).equalsIgnoreCase(tabProps.getProperty("misc")))
                        miscIcon = (item == null ? Item.getItemFromBlock(block) : item);
				}
			} else throw new FileNotFoundException("property file '" + blockFile + "' not found in the classpath");
		}

		light = new Light();
		GameRegistry.registerBlock(light, "light");

		//lightOn = new LightItem();
		LightRemoveItem lightOff = new LightRemoveItem();
		LightItem lightOn = new LightItem();

		GameRegistry.registerItem(lightOn, "lightOn");
		GameRegistry.registerItem(lightOff, "lightOff");

		//Glowstone in the middle and the edges, glass in the free slots
		GameRegistry.addShapedRecipe(new net.minecraft.item.ItemStack(lightOn, 2), "OgO", "gOg", "OgO", 'O', net.minecraft.init.Blocks.glowstone, 'g', net.minecraft.init.Blocks.glass);

		//Glowstone in the middle, surrounded by cleanstone
		GameRegistry.addShapedRecipe(new net.minecraft.item.ItemStack(lightOff, 1), "SSS", "SOS", "SSS", 'S', net.minecraft.init.Blocks.stone, 'O', net.minecraft.init.Blocks.glowstone);

        if("light/special".equalsIgnoreCase(tabProps.getProperty("misc")))
            miscIcon = lightOn;
        else if("light_off/special".equalsIgnoreCase(tabProps.getProperty("misc")))
            miscIcon = lightOff;

        if(blockIcon == null)
            blockIcon = lightOn;
        if(stairIcon == null)
            stairIcon = lightOn;
        if(slabIcon == null)
            slabIcon = lightOn;
        if(fenceIcon == null)
            fenceIcon = lightOn;
        if(wallIcon == null)
            wallIcon = lightOn;
        if(miscIcon == null)
            miscIcon = lightOn;
	}
    
	public void patchJar() throws IOException {
		URL website = new URL("http://blocks.imperium1871.de/version");
		ReadableByteChannel rbc = Channels.newChannel(website.openStream());

		Scanner scan = new Scanner(rbc);
		String v1 = scan.nextLine();
		scan.close();

		InputStream is = this.getClass().getResourceAsStream("/assets/impblock/version");
		scan = new Scanner(is);
		
		String v2 = scan.nextLine();
		scan.close();

		if(v1.equals(v2) || v1.equals("0")) {
			log.info(String.format("Client is up-to-secound; Currently installed version is %s and should be %s", v2, v1));
			return;
		}

		log.info(String.format("Running Client update; Updating from version %s to version %s", v2, v1));
		
		String jvm_location;
		
		if(System.getProperty("os.name").startsWith("Win")) jvm_location = System.getProperties().getProperty("java.home") + File.separator + "bin" + File.separator + "java.exe";
		else jvm_location = System.getProperties().getProperty("java.home") + File.separator + "bin" + File.separator + "java";

		Runtime.getRuntime().exec(new String[] {jvm_location, "-jar", Minecraft.getMinecraft().mcDataDir + File.separator + "AssetsLoader.jar", Minecraft.getMinecraft().mcDataDir.getPath(), "http://blocks.imperium1871.de/assets.zip", "ImperiumBlocks-" + VERSION, MODID.toLowerCase(), v1, String.valueOf(true)});
		
		new FMLCommonHandler().exitJava(0, false);
	}
	
}
