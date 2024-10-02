package org.asmeta.asm2java.asm;

import java.io.File;

public class TestUtil {
	
	/**
	 * Check if the file exists and delete it.
	 *
	 * @param file the file to delete.
	 */
	public static void deleteExisting(File file){
		if (file.exists())
			file.delete();
		assert !file.exists();
	}

}
