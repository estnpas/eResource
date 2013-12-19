package com.ericsson.skillset;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.ericsson.model.Resource;
import com.ericsson.model.Skillset;
import com.ericsson.model.TelcocellRoster;
import com.ericsson.model.Translation;

public class ResourceAttributeMasterProcessor extends BaseProcessor {
	
	protected List<Skillset> skillSets = new ArrayList<Skillset>();
	

	protected void loadSkillsetList(File dir, String fileName)
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
							skillSets.add(Skillset.newInstance(line));
						} catch (ParseException e1) {}
					}
					line = br.readLine();
				}
				
				br.close();
			}
	
	protected void generateCSVFile(File dir, String typeFilter) 
	throws IOException {
		try {
			File skillListFile = new File(dir, "skillsList_" + typeFilter + ".csv");
			HashSet<String> writtenKeys = new HashSet(skillSets.size());
			if (skillListFile.exists()) {
				skillListFile.delete();
			}
			
			System.out.println("There are " + skillSets.size() + " entries.");
			if (!skillSets.isEmpty()) {
				PrintWriter pwSkillSet = new PrintWriter(skillListFile);
				pwSkillSet.println("Skill,Type");
				for (Skillset skillSet : skillSets) {
					if (typeFilter.equalsIgnoreCase(skillSet.getSkillsetType()))
					{
						if ((skillSet.getSkillsetKey() != null) && (!skillSet.getSkillsetKey().isEmpty()))
						{
							if (!writtenKeys.contains(skillSet.getSkillsetKey())) {
								pwSkillSet.println(skillSet.getSkillsetKey() + "," + skillSet.getSkillsetType());
								writtenKeys.add(skillSet.getSkillsetKey());
							}
						}
						else {
							if (!writtenKeys.contains(skillSet.getName())) {
								pwSkillSet.println(skillSet.getName() + "," + skillSet.getSkillsetType());
								writtenKeys.add(skillSet.getName());
							}
						}
					}
					
				}
				pwSkillSet.flush();
				pwSkillSet.close();
			}
		} catch (IOException e1) {}
	}

	public static void main(String args[]) {
		ResourceAttributeMasterProcessor processor = new ResourceAttributeMasterProcessor();

		File dir = new File("C:/Users/emattse/Desktop/eResource/");

		if (!dir.exists() || !dir.isDirectory()) {
			System.err.println("The directory '" + dir.getName()
					+ "' is not found.");
			System.exit(-1);
		}

		// Load and process all of the files from the specified folder.
		try {
			File dataDir = new File(dir, "skillsData");
			if (!dataDir.exists() || !dataDir.isDirectory()) {
				System.err.println("The directory '" + dataDir.getName()
						+ "' is not found.");
				System.exit(-1);
			}

			processor.loadSkillsetList(dataDir, "skills_grouping.csv");
			processor.generateCSVFile(dir, "technical skills");
			processor.generateCSVFile(dir, "functional skills");

		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

}
