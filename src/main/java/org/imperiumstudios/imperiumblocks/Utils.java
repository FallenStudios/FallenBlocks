/**
 * The MIT License (MIT)
 * 
 * Copyright (c) 2016 Imperium Studios <https://imperiumstudios.org>
 * Copyright (c) 2016 Kevin Olinger <https://kevinolinger.net>
 * Copyright (c) 2016 garantiertnicht <>
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

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Utils {

	public String[] getResourceFolderContent(String path) throws Exception {
		URL dirURL = this.getClass().getClassLoader().getResource(path);
	      
		if(dirURL != null && dirURL.getProtocol().equals("file")) return new File(dirURL.toURI()).list();
		if(dirURL == null) {
			String me = this.getClass().getName().replace(".", "/") +".class";
	        dirURL = this.getClass().getClassLoader().getResource(me);
		}

		if(dirURL.getProtocol().equals("jar")) {
			String jarPath = dirURL.getPath().substring(5, dirURL.getPath().indexOf("!"));
	        JarFile jar = new JarFile(URLDecoder.decode(jarPath, "UTF-8"));
	        Enumeration<JarEntry> entries = jar.entries();
	        Set<String> result = new HashSet<String>();
	        
	        while(entries.hasMoreElements()) {
	        	String name = entries.nextElement().getName();

	          	if(name.startsWith(path)) {
	            	String entry = name.substring(path.length());

					if(entry.length() == 0)
						continue;
					ImperiumBlocks.log.info("1 " + entry);
					if(entry.charAt(0) == '/')
						entry = entry.substring(1);
					if(entry.length() == 0)
						continue;
					ImperiumBlocks.log.info("2 " + entry);

	            	int checkSubdir = entry.indexOf("/");
	            
	            	if(checkSubdir >= 0) entry = entry.substring(0, checkSubdir);

					if(entry.length() != 0)
	            		result.add(entry);
	          	}
	        }
	        
	        jar.close();
	        
	        return result.toArray(new String[result.size()]);
		} 
	        
		throw new UnsupportedOperationException("Cannot list files for URL "+ dirURL);
	}
	
}
