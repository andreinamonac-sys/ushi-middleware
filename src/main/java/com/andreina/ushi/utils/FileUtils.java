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

	public static void write(byte[] a, String filePath, boolean append) throws IOException {

		FileOutputStream fos = null;
		File f;
		try {
			f = new File(filePath);
			File parent = f.getAbsoluteFile().getParentFile();

			if (!parent.isDirectory() && !parent.mkdirs() && !parent.isDirectory()) {
				throw new IOException("No se pudo crear la carpeta: " + parent);
			}

			fos = new FileOutputStream(f, true);
			fos.write(a);
			fos.flush();

		} catch (IOException ioe) {

		} finally {
			try {
				if (fos != null) {
					fos.close();
				}

			} catch (IOException ignore) {
				ignore.printStackTrace();

			}
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