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
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

import java.lang.reflect.Field;

public class Helper {
	
    public static Material getMaterial(String s) {
    	Class<Material> materialClass = Material.class;
    	for(Field field: materialClass.getDeclaredFields()) {
            if(field.getName().equals(s)) {
                try { return (Material) field.get(new Material(MapColor.stoneColor)); } 
            	catch(IllegalAccessException ex) { ex.printStackTrace(); }
            }
        }

        return Material.rock;
    }

    public static Block.SoundType getSoundType(String s) {
    	s = s.replace("\"","");
    	
        Class<Block> blockClass = Block.class;
        for(Field field : blockClass.getDeclaredFields()) {
            if(field.getName().equals(s)) {
                try { return (Block.SoundType) field.get(new Block.SoundType("none", 0.0F, 0.0F)); } 
                catch(IllegalAccessException ex) { ex.printStackTrace(); }
            }
        }

        return Block.soundTypeStone;
    }
    
}