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
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.swing.JOptionPane;

import org.imperiumstudios.imperiumblocks.Special.Light;
import org.imperiumstudios.imperiumblocks.Special.LightItem;
import org.imperiumstudios.imperiumblocks.Special.LightRemoveItem;
import org.imperiumstudios.imperiumblocks.models.IMPBlock;
import org.imperiumstudios.imperiumblocks.models.IMPFence;
import org.imperiumstudios.imperiumblocks.models.IMPGate;
import org.imperiumstudios.imperiumblocks.models.IMPGlass;
import org.imperiumstudios.imperiumblocks.models.IMPSlab;
import org.imperiumstudios.imperiumblocks.models.IMPStair;
import org.imperiumstudios.imperiumblocks.models.IMPTrapdoor;
import org.imperiumstudios.imperiumblocks.models.IMPWall;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = ImperiumBlocks.MODID, version = ImperiumBlocks.VERSION, name = ImperiumBlocks.NAME)
public class ImperiumBlocks {
	
	public Utils utils = new Utils();
	public static CreativeTabs impTab = new CreativeTabsIMP("ImperiumBlocks");
	
	public static final String NAME = "Imperium Blocks"; //Used in the GUI parts of the mod
	public static final String MODID = "imperiumblocks";
	public static final String VERSION = "1.0";
    
	private String modJar;
	public static Light light;
	public static org.apache.logging.log4j.Logger log;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) throws IOException {
        log = e.getModLog();
        modJar = Minecraft.getMinecraft().mcDataDir + File.separator +"mods"+ File.separator +"ImperiumBlocks-"+ VERSION +".jar";

        //patchJar();
    }
    
	@Mod.EventHandler
	public void init(FMLInitializationEvent e) throws Exception {
		Properties blockProps = new Properties();
		InputStream inputStream;

		for(String blockFile: utils.getResourceFolderContent("assets/imperiumblocks/blocks/")) {
			if(blockFile.equals("")) continue;		
			
			inputStream = getClass().getClassLoader().getResourceAsStream("assets/imperiumblocks/blocks/"+ blockFile);
			
			if(inputStream != null) {
				blockProps.load(inputStream);
				String[] blockTypes = blockProps.getProperty("types", "block").split(",");
				
				for(String blockType: blockTypes) {
					Block block = null;
					
					if(blockType.equalsIgnoreCase("block")) block = new IMPBlock(this, blockFile.replace(".properties", "") +"Block", blockProps);
					else if(blockType.equalsIgnoreCase("wall")) block = new IMPWall(this, blockFile.replace(".properties", "") +"Wall", blockProps);
					else if(blockType.equalsIgnoreCase("glass")) block = new IMPGlass(this, blockFile.replace(".properties", "") +"Glass", blockProps);
					else if(blockType.equalsIgnoreCase("fence")) block = new IMPFence(this, blockFile.replace(".properties", "") +"Fence", blockProps);
					else if(blockType.equalsIgnoreCase("gate")) block = new IMPGate(this, blockFile.replace(".properties", "") +"Gate", blockProps);
					else if(blockType.equalsIgnoreCase("slab")) block = new IMPSlab(this, blockFile.replace(".properties", "") +"Slab", blockProps);
					else if(blockType.equalsIgnoreCase("stair")) block = new IMPStair(this, blockFile.replace(".properties", "") +"Stair", blockProps);
					else if(blockType.equalsIgnoreCase("trapdoor")) block = new IMPTrapdoor(this, blockFile.replace(".properties", "") +"Trapdoor", blockProps);
					else continue;
					
					GameRegistry.registerBlock(block, block.getUnlocalizedName().substring(5));
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
	}
    
	public void patchJar() throws IOException {
		FileSystem fs = FileSystems.getDefault();

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

		Runtime.getRuntime().exec(new String[] {jvm_location, "-jar", Minecraft.getMinecraft().mcDataDir + File.separator + "AssetsLoader.jar", Minecraft.getMinecraft().mcDataDir.getPath(), "http://blocks.imperium1871.de/assets.zip", "ImperiumBlocks", MODID.toLowerCase(), v1, String.valueOf(true)});
		
		new FMLCommonHandler().exitJava(0, false);
	}
	
}
