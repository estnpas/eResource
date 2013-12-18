package com.ericsson.skillset;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.ericsson.model.Resource;
import com.ericsson.model.TelcocellRoster;
import com.ericsson.model.Translation;

/**
 * Base processing for any of the roster or skillset processing
 * @author estnpas
 *
 */
public abstract class BaseProcessor 
	implements Constants {
	
	protected List<TelcocellRoster> telcocellRoster = new ArrayList<TelcocellRoster>();
	protected List<Resource> telcocellResource = new ArrayList<Resource>();
	
	protected String translate(
						Map<String,String> translations, 
						String key,
						String def) {
		String returnValue = def;
		if (StringUtils.isNotBlank(key)) {
			String keyValue = StringUtils.lowerCase(key);
			if (translations.containsKey(keyValue)) {
				returnValue = translations.get(keyValue);
			}
		}
		
		return returnValue;
	}
	
	protected String translate(
							Map<String,String> translations, 
							String key) {
		return translate(
					translations, 
					key,
					"");
	}
	
	protected Map<String,String> loadTranslation(
										File dir, 
										String fileName)
			throws IOException {
		
		Map<String,String> translations = new HashMap<String,String>();
			
		File inFile = new File(dir, fileName);
		if (inFile.exists()) {
			BufferedReader br = new BufferedReader(new FileReader(inFile));
			String line = br.readLine();
			line = br.readLine();
			while (line!=null) {
				try {
					Translation translation = Translation.newInstance(line);
					//  Check if we already exist.
					//  For any translation, it always translates to itself
					String keyValue = StringUtils.lowerCase(translation.getKey());
					if (!translations.containsKey(keyValue)) {
						String value = StringUtils.trimToEmpty(translation.getValue());
						if (StringUtils.isNotEmpty(value)) {
							translations.put(keyValue, value);
							keyValue = StringUtils.lowerCase(value);
							if (!translations.containsKey(keyValue)) {
								translations.put(keyValue, value);
							}
						}
					}
				} catch (ParseException e1) {}
				line = br.readLine();
			}
			
			br.close();
		}
		
		return translations;
	}
	
	/**
	 * Load in the information from the Telcocell Roster file.<p>For the Telcocell Roster file we
	 * use the | separator as the columns may contain commas.</p>
	 * @param dir
	 * @param fileName
	 * @throws IOException
	 */
	protected void loadTelcocellRosterHC(File dir, String fileName)
		throws IOException {
		File inFile = new File(dir, fileName);
		if (!inFile.exists()) {
			throw new FileNotFoundException("File '" + fileName + "' was not found.");
		}
		
		BufferedReader br = new BufferedReader(new FileReader(inFile));
		String line = br.readLine();
		line = br.readLine();
		
		while (line!=null) {
			if (StringUtils.isNotEmpty(line)) {
				try {
					telcocellRoster.add(TelcocellRoster.newInstance(line));
				} catch (ParseException e1) {}
			}
			line = br.readLine();
		}
		
		br.close();
	}
	
	/**
	 * Find a record in the Telcocell Roster file using the personnel number
	 * @param personnelNumber
	 * @return
	 */
	protected TelcocellRoster findTelcocellRoster(String personnelNumber) {
		if (StringUtils.isNotBlank(personnelNumber)) {
			for (TelcocellRoster roster : telcocellRoster) {
				if (roster.getPersonnelNumber().equals(personnelNumber)) {
					return roster;
				}
			}
		}
		return null;
	}
	
	private void loadTelcocellResourceData(File inFile)
		throws IOException {
		
		System.out.println("Load '" + inFile.getName() + "'" );
		
		BufferedReader br = new BufferedReader(new FileReader(inFile));
		String line = br.readLine();
		line = br.readLine();
		
		while (line!=null) {
			if (StringUtils.isNotEmpty(line)) {
				try {
					telcocellResource.add(Resource.newInstance(line));
				} catch (ParseException e1) {}
			}
			line = br.readLine();
		}
		
		br.close();
	}
	
	/**
	 * Load in the Telcocell Skillset Data
	 * @param dir
	 * @param directoryName
	 * @throws IOException
	 */
	protected void loadTelcocellResourceDataFolder(File dir, String directoryName)
		throws IOException {
		
		File inDir = new File(dir, directoryName);
		if (!inDir.exists() || !inDir.isDirectory()) {
			throw new FileNotFoundException("Input directory '" + directoryName + "' does not exist.");
		}

		//  From this, build a list of files to load
		File[] files = inDir.listFiles(new CSVFileFilter());
		for (File file : files) {
			//  Process
			loadTelcocellResourceData(file);
		}
	}
	
	/**
	 * Attempt to retrieve the telcocell resource with the specified first and last name
	 * @param lastName
	 * @param firstName
	 * @return
	 */
	protected Resource findTelcocellResource(String lastName, String firstName) {
		for (Resource resource : telcocellResource) {
			if (StringUtils.equalsIgnoreCase(resource.getLastName(), lastName)) {
				if (StringUtils.equalsIgnoreCase(resource.getFirstName(), firstName)) {
					return resource;
				}
			}
		}
		return null;
	}

}
