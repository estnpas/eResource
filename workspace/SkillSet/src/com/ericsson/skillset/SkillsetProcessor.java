package com.ericsson.skillset;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;

import com.ericsson.model.Resource;
import com.ericsson.model.Skillset;

/**
 * Process the set of Telcocell skill records
 * @author estnpas
 *
 */
public class SkillsetProcessor 
	implements Constants {
	
	private List<Resource> resources = new ArrayList<Resource>();
	private List<Skillset> skillsets = new ArrayList<Skillset>();
	private Set<String> skills = new TreeSet<String>();
	
	/**
	 * Load the records from the Skills Translation table
	 * @param fileName
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public void loadTranslation(File inFile) 
		throws IOException, FileNotFoundException {
		
		BufferedReader br = new BufferedReader(new FileReader(inFile));
		String line = br.readLine();
		line = br.readLine();
		while (line!=null) {			
			try {
				skillsets.add(Skillset.newInstance(line));
			} catch (ParseException e1) {}
			
			line = br.readLine();
		}
		
		br.close();
		
		//  Ok, list out the translations to make sure we processed properly.
		for (Skillset skillset : skillsets) {
			System.out.println("Skill " + skillset.getName() + "=" + skillset.getSkillsetKey());
		}
	}
	
	private Skillset findSkill(String key) {
		for (Skillset skillset : skillsets) {
			if (StringUtils.equalsIgnoreCase(skillset.getName(), key)) {
				return skillset;
			}
		}
		return null;
	}
	
	private String translateSkill(String key) {
		Skillset skillset = findSkill(StringUtils.lowerCase(key));
		if (skillset!=null) {
			return skillset.getSkillsetKey();
		}
		return null;
	}	
	
	/**
	 * Process the loaded Skills Inventory records
	 * @param dir
	 * @throws FileNotFoundException
	 */
	public void process(File dir) 
		throws FileNotFoundException {
		File outFile = new File(dir, "SkillList.csv");
		if (outFile.exists()) {
			outFile.delete();
		}
		PrintWriter pw = new PrintWriter(outFile);
		
		//  Ok, we have the resources...now build a list of the skills...
		
		for (Resource resource : resources) {
			
			System.out.println("Process " + resource.toXMLString());
			
			//  If the firstname or lastname are empty, bypass
			if (StringUtils.isNotBlank(resource.getLastName()) &&
					StringUtils.isNotBlank(resource.getFirstName())) {
				if (resource.getSkills().length>0) {
					StringBuffer skillInventory = new StringBuffer();
					for (String skill : resource.getSkills()) {
						String skillKey = translateSkill(
													StringUtils.strip(skill));
						if (skillKey!=null) {
							if(skillInventory.length()>0) {
								skillInventory.append(",");
							}
							skillInventory.append(skillKey);
						}
						
						//  Translate
						Skillset skillset = findSkill(skillKey);
						if (skillset!=null) {
							skills.add(skillKey);
						}
					}
					
					//  Reset the skillList 
					if (skillInventory.length()>0) {
						System.out.println("Reset the skills list ["
												+ resource.getSkillList()
												+ "] to ["
												+ skillInventory.toString()
												+ "]");
						resource.setSkillList(skillInventory.toString());
					}
					
					//  Publish a skills extract record....
					String resourceName = resource.getFirstName()
							+ " "
							+ resource.getLastName();
					pw.println(resourceName
									+ ","
									+ "\""
									+ resource.getSkillList()
									+ "\"");
					
					System.out.println(" Skills [" + resource.getSkillList() + "]");
				}	
			}			
		}
		
		pw.flush();
		pw.close();
	}
	
	/**
	 * Load the Skills Inventory records
	 * @param inFile
	 * @throws IOException
	 */
	public void load(File inFile) 
		throws IOException {
		
		System.out.println("Loading '" + inFile.getName() + "'" );
		
		BufferedReader br = new BufferedReader(new FileReader(inFile));
		String line = br.readLine();
		line = br.readLine();  //  skip the first, header line.....
		while (line!=null) {
			try {
				Resource rsrc = Resource.newInstance(line);
				resources.add(rsrc);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			
			line = br.readLine();
		}
		
		br.close();
	}
	
	/**
	 * Load the Skills Inventory records
	 * @param fileName
	 * @throws IOException
	 */
	public void load(String fileName) 
		throws IOException {
		load(new File(fileName));
	}
	
	/**
	 * Load the Skills Inventory files from the specified directory
	 * @param dir
	 * @throws IOException
	 */
	public void loadDirectory(File dir) 
		throws IOException {
		
		if (dir.exists() && dir.isDirectory()) {
			File files[] = dir.listFiles(new CSVFileFilter());
			for (File inFile : files) {
				load(inFile);
			}
		}
	}
	
	public static void main(String args[]) {
		SkillsetProcessor processor = new SkillsetProcessor();
		
		File dir = new File("C:/Users/estnpas/Desktop/eResource/");
		
		if (!dir.exists() || !dir.isDirectory()) {
			System.err.println("The directory '" + dir.getName() + "' is not found.");
			System.exit(-1);
		}
		
		//  Translation table can be empty
		try {
			File tranFile = new File(dir, "translation/skills_grouping.csv");
			processor.loadTranslation(tranFile);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {			
			e1.printStackTrace();
		}
		
		//  Load and process all of the files from the specified folder.
		try {	
			File dataDir = new File(dir, "skillsData");
			if (!dataDir.exists() || !dataDir.isDirectory()) {
				System.err.println("The directory '" + dataDir.getName() + "' is not found.");
				System.exit(-1);
			}
			
			processor.loadDirectory(dataDir);
			processor.process(dir);
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

}
