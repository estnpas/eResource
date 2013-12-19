package com.ericsson.skillset;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrTokenizer;
import org.apache.commons.lang3.time.DateFormatUtils;

import com.ericsson.model.Resource;
import com.ericsson.model.Skillset;
import com.ericsson.model.db.ResourceMaster;
import com.ericsson.model.db.ResourceMasterAttributes;
import com.ericsson.model.db.ResourceTypeAttributeValue;

/**
 * Process the set of Telcocell skill records
 * @author estnpas
 *
 */
public class SkillsetProcessor 
	extends BaseProcessor {
	
	private List<Resource> resources = new ArrayList<Resource>();
	private Set<String> skills = new TreeSet<String>();
	private Set<String> skillExceptions = new TreeSet<String>();
	
	private void generateSkillsAssignment(
									PrintWriter pwSQL,
									Resource resource) {
		
		pwSQL.println();
		pwSQL.println("--  Resource: " + resource.getFullName());
		pwSQL.println("delete from "
							+ ResourceMasterAttributes.TABLE_NAME
							+ " where "
							+ ResourceMasterAttributes.COL_RESOURCEID
							+ " = (select "
							+ ResourceMaster.COL_RESOURCEID
							+ " from "
							+ ResourceMaster.TABLE_NAME
							+ " where "
							+ ResourceMaster.COL_RESOURCENAME
							+ "='"
							+ resource.getFullName()
							+ "');");
		
		for (String skill : resource.getSkills()) {	
			pwSQL.println();
			pwSQL.println("--    Skill: " + skill);
							
			//  For each skill, generate an assignment record
			pwSQL.println("INSERT INTO "
								+ ResourceMasterAttributes.TABLE_NAME
								+ "("
								+ ResourceMasterAttributes.COL_RESOURCEID+","
								+ ResourceMasterAttributes.COL_ATTRIBUTEID+","
								+ ResourceMasterAttributes.COL_ATTRIBUTEVALUEID
								+ ")");
			
			pwSQL.println("select");
			
			pwSQL.println("(select "	
								+ ResourceMaster.COL_RESOURCEID
								+ " from "
								+ ResourceMaster.TABLE_NAME
								+ " where "
								+ ResourceMaster.COL_RESOURCENAME
								+ "= '"
								+ resource.getFullName()
								+ "'),");
			
			pwSQL.println("(select "
								+ ResourceMaster.COL_TYPEID
								+ " from "
								+ ResourceMaster.TABLE_NAME
								+ " where "
								+ ResourceMaster.COL_RESOURCENAME
								+ "= '"
								+ resource.getFullName()
								+ "'),");
			
			pwSQL.println("(select "
								+ ResourceTypeAttributeValue.COL_ATTRIBUTEID
								+ " from "
								+ ResourceTypeAttributeValue.TABLE_NAME
								+ " where "
								+ ResourceTypeAttributeValue.COL_ATTRIBUTEVALUE
								+ " ='"
								+ skill
								+ "');");
		}
	}
	
	private void addSkill(String skill) {
		String skillKey = StringUtils.lowerCase(StringUtils.trimToEmpty(skill));
		if (StringUtils.isNotEmpty(skillKey) &&
				!skills.contains(skillKey)) {
			skills.add(skillKey);
		}
	}
	
	private void publishSkills(File dir) {
		try {
			File skillFile = new File(dir, "Skills.csv");
			if (skillFile.exists()) {
				skillFile.delete();
			}
			System.out.println("There are " + skills.size() + " skills.");
			if (!skills.isEmpty()) {
				PrintWriter pwSkill = new PrintWriter(skillFile);
				pwSkill.println("Skill");
				for (String skill : skills) {
					pwSkill.println(skill);
				}
				pwSkill.flush();
				pwSkill.close();
			}
		} catch (IOException e1) {}
	}
	
	private void addSkillException(String skill) {
		String skillKey = StringUtils.lowerCase(StringUtils.trimToEmpty(skill));
		if (StringUtils.isNotEmpty(skillKey) &&
				!skillExceptions.contains(skillKey)) {
			skillExceptions.add(skillKey);
		}
	}
	
	private void publishSkillExceptions(File dir) {
		try {
			File skillExceptionFile = new File(dir, "SkillExceptions.csv");
			if (skillExceptionFile.exists()) {
				skillExceptionFile.delete();
			}
			System.out.println("There are " + skillExceptions.size() + " exceptions.");
			if (!skillExceptions.isEmpty()) {
				PrintWriter pwSkillException = new PrintWriter(skillExceptionFile);
				pwSkillException.println("Skill");
				for (String skillException : skillExceptions) {
					pwSkillException.println(skillException);
				}
				pwSkillException.flush();
				pwSkillException.close();
			}
		} catch (IOException e1) {}
	}
	
	private void addToInventory(StringBuffer skillInventory, String skillKey) {
		if (skillInventory.length()>0) {
			skillInventory.append(",");
		}
		skillInventory.append(skillKey);
	}
	
	private String createKey(String source) {
		return StringUtils.lowerCase(StringUtils.trimToEmpty(source));
	}
	
	/**
	 * Process the loaded Skills Inventory records
	 * @param dir
	 * @throws FileNotFoundException
	 */
	public void process(File dir) 
		throws FileNotFoundException {
		
		Map<String,String> skillTranslations = null;
		
		File outFile = new File(dir, "SkillResource.csv");
		if (outFile.exists()) {
			outFile.delete();
		}
		PrintWriter pw = new PrintWriter(outFile);
		pw.println("Resource-Name, Skill");
		
		File sqlFile = new File (dir, "ResourceAssignment.sql");
		if (sqlFile.exists()) {
			sqlFile.delete();
		}
		PrintWriter pwSQL = new PrintWriter(sqlFile);
		pwSQL.println();
		pwSQL.println("--  Resource Assignment");
		pwSQL.println("--  Date: " + DateFormatUtils.format(new Date(), "MMM-dd-yyyy HH:mm"));
		pwSQL.println();
		
		//  Load in the Skill Translations
		try {
			skillTranslations = loadTranslation(dir, "translation/skills_grouping.csv");
		} catch (IOException e11) {}
		
		//  Ok, we have the resources...now build a list of the skills...
		//  We do this in multiple passes.  If the full value of the skill is not found in the translation table which lists all of 
		//  the valid skills, then we attempt to break it down into component skills.
		//  For each of these we attempt to determine if its a valid skill as well.
		
		for (Resource resource : resources) {
			
			//  If the firstname or lastname are empty, bypass
			//  If the list of skills has values
			if (StringUtils.isNotBlank(resource.getLastName()) &&
					StringUtils.isNotBlank(resource.getFirstName()) &&
					resource.getSkills().length>0) {
				
				StringBuffer skillInventory = new StringBuffer();
				for (String skill : resource.getSkills()) {
					
					//  For each skill, create a key value which is trimmed and set to lower-case
					//  If found, then we attempt to translate this key.
					String skillLookupKey = createKey(skill);
					if (StringUtils.isNotEmpty(skillLookupKey)) {
						String skillKey = (String)translate(skillTranslations, skillLookupKey);
						if (StringUtils.isNotEmpty(skillKey)) {
							addToInventory(skillInventory, skillKey);
							addSkill(skillKey);
						} else {
							//  Sometimes there is a combination of fields....so check each word to see if it maps
							boolean foundValue = false;
							StrTokenizer skillTokenizer = new StrTokenizer(skillLookupKey, " ");
							for (String token : skillTokenizer.getTokenList()) {
								//  Ok, for each token
								String tokenKey = createKey(token);
								if (StringUtils.isNotEmpty(tokenKey)) {
									String tokenKeyValue = (String)translate(skillTranslations, token);
									
									if (StringUtils.isNotEmpty(tokenKeyValue)) {
										foundValue = true;
										addToInventory(skillInventory, tokenKeyValue);
										addSkill(tokenKeyValue);
									}
								}
							}
							
							//  if we have no resolution from attempting to parse the skill
							//  add to the list of exceptions. 
							//  NB:  The original key is added, not any of the parsed entities
							if (!foundValue) {
								addSkillException(skillLookupKey);
							}
						}
					}
				}	
				
				//  Reset the skillList on the resource
				resource.setSkillList(skillInventory.toString());
				
				//  Generate the SQL
				generateSkillsAssignment(pwSQL, resource);
				
				//  Publish a skills extract record....
				pw.println(resource.getFullName()
								+ ","
								+ "\""
								+ resource.getSkillList()
								+ "\"");
			}			
		}
		
		//  Publish the list of all of the skills used
		publishSkills(dir);
		
		//  Publish the missing Skills list
		publishSkillExceptions(dir);
		
		pwSQL.flush();
		pwSQL.close();
		
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
				
//				System.out.println("Resource " + rsrc.getLastName());
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