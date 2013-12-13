package com.ericsson.skillset;

import java.io.File;
import java.io.FileFilter;

import org.apache.commons.lang3.StringUtils;

/**
 * A simple filter for any CSV files with no prefix values.
 * @author estnpas
 *
 */
public class CSVFileFilter 
	implements FileFilter {
	
	public boolean accept(File file) {
		boolean accept = false;
		
		//  Ok, check if the file has an extension CSV
		String fileName = file.getName();
		String extension = StringUtils.substringAfterLast(fileName, ".");
		accept = accept |
					StringUtils.equalsIgnoreCase(extension, "csv");
		return accept;
	}

}
