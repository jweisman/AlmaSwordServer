package com.exlibrisgroup.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.*;

import org.apache.log4j.Logger;

public class Util {
	
    private static Logger log = Logger.getLogger(Util.class);

    
	public static int unzip(File file, Path destination) 
			throws FileNotFoundException, IOException {
		
    	ZipInputStream zip = new ZipInputStream(new FileInputStream(file));
    	int files = 0;

    	try {
       		ZipEntry entry;
       		byte[] buffer = new byte[2048];

       		while ((entry = zip.getNextEntry()) != null) {
                String s = String.format("Entry: %s len %d added %TD",
                        entry.getName(), entry.getSize(),
                        new Date(entry.getTime()));
                log.info(s);
       			if (!entry.isDirectory()) {
       				files++;
       				FileOutputStream output = null;
       				try {
	       				output = new FileOutputStream(destination.resolve(Paths.get(entry.getName())).toFile());
	       				int count = 0;
	       				while ((count = zip.read(buffer)) > 0) {
	       					output.write(buffer, 0, count);
	       				}
       				} 
       				finally {
       					output.close();
       				}
       			}
       		}    
    	} 
    	finally {
    		zip.close();
    	}
    	
    	return files;
	}
	
    public static void deleteDirectory(Path path) throws IOException {
 	   Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
 		   @Override
 		   public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
 			   Files.delete(file);
 			   return FileVisitResult.CONTINUE;
 		   }

 		   @Override
 		   public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
 			   Files.delete(dir);
 			   return FileVisitResult.CONTINUE;
 		   }

 	   });
    }
    
    public static String find(String input, String regex) {
    	Pattern pattern = Pattern.compile(regex);
    	Matcher matcher = pattern.matcher(input);
    	String match = "";
    	while (matcher.find()) {
    		match = matcher.group(1); // 0 is the whole string, not just the indicated section
    		break;
    	}
    	return match;
    }
}
