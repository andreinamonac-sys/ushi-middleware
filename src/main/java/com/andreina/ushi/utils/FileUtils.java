package com.andreina.ushi.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {

	/**
	 * Guarda un array de bytes
	 * 
	 * @param a
	 * @param fileName
	 * @param append
	 * @throws IOException
	 */

    public static void write(byte[] data, String filePath, boolean append) throws IOException {
        java.nio.file.Path target = java.nio.file.Paths.get(filePath).toAbsolutePath();
        java.nio.file.Files.createDirectories(target.getParent());
        if (append) {
            java.nio.file.Files.write(target, data, java.nio.file.StandardOpenOption.CREATE,
                    java.nio.file.StandardOpenOption.APPEND);
        } else {
            java.nio.file.Files.write(target, data, java.nio.file.StandardOpenOption.CREATE,
                    java.nio.file.StandardOpenOption.TRUNCATE_EXISTING);
        }
    }

	/**
	 * Recupera todo el contenido de un fichero en un array de bytes.
	 * @param filePath Ruta compelta fichero.
	 * @return Los bytes del fichero.
	 * @throws IOException Propagada si ocurren internamente.
	 */
	
	public static byte[] readFully (String filePath) throws IOException{
		File f = new File(filePath);
		if(!f.exists()) {
			return null;
		}
		FileInputStream fis =null;
		
		try {
			fis = new FileInputStream(filePath);
			return fis.readAllBytes();
		}finally {
			try {
				if(fis != null) {
					fis.close();
				}
				
			}catch(IOException ignore) {
				ignore.printStackTrace();
				
			}
		}
	}
}