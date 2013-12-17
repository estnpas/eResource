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
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;

import com.ericsson.model.EResource;
import com.ericsson.model.Resource;
import com.ericsson.model.Roster;
import com.ericsson.model.TelcocellRoster;

/**
 * Process the Telcocell employee Roster information
 * @author estnpas
 *
 */
public class RosterProcessor 
	extends BaseProcessor {
	
	private Set<String> roles = new TreeSet<String>();
	
	private void addTemplate(File dir, PrintWriter pw) {
		try {
			File inFile = new File(dir, "eRS_Import_Template.txt");
			if (inFile.exists()) {
				BufferedReader br = new BufferedReader(new FileReader(inFile));
				String line = br.readLine();
				while (line!=null) {
					pw.println(line);
				
					line = br.readLine();
				}
				br.close();
			}
			
			pw.flush();
		} catch (IOException e1) {}
	}
	
	private void checkAndAddRole(String role) {
		if (StringUtils.isNotBlank(role)) {
			//  Ok, if not already present...add the ROLE
			if (!roles.contains(role)) {
				roles.add(role);
			}
		}		
	}
	
	private void checkAndAddTelcocellRole() {
		for (TelcocellRoster roster : this.telcocellRoster) {
			String positionName = roster.getPositionName();
			checkAndAddRole(roster.getPositionName());
		}
	}
	
	private void publishRoleInformation(File dir) {
		File outFile = new File(dir, "roles.csv");
		if (outFile.exists()) {
			outFile.delete();
		}
		
		//  Publish
		try {
			PrintWriter pw = new PrintWriter(outFile);
			for (String role : roles) {
				pw.println(role); 
			}
			pw.flush();
			pw.close();
		} catch (IOException e1) {}
	}
	
	/**
	 * Process the specified Telcocell roster file
	 * @param dir
	 * @param fileName
	 * @throws IOException
	 */
	public void process(
					File dir,
					String fileName)
		throws IOException {
		
		Map<String,String> officeTranslations = null;
		
		//  Load in the Translations:
		//      Office
		File translationDir = new File(dir, "translation");
		if (translationDir.exists() && translationDir.isDirectory()) {
			String officeTranslationFilename = "office_translations.csv";
			officeTranslations = loadTranslation(translationDir, officeTranslationFilename);
		}
		
		File outFile = new File(dir, "eRS_Resource_Import.csv");
		if (outFile.exists()) {
			outFile.delete();
		}
		
		PrintWriter pw = new PrintWriter(outFile);
		//  Add in any template records
		addTemplate(dir, pw);
		//  Add in the column/field headers
		pw.println(EResource.getFieldHeaders(","));
		
		File rosterDir = new File(dir, "rosterData");
		if (rosterDir.exists() && rosterDir.isDirectory()) {
			
			//  Load in the Telcocell Roster (from the HC data) data
			loadTelcocellRosterHC(rosterDir, "Nov Telcocell HC 2013 v5 Clean.csv");
			checkAndAddTelcocellRole();
			
			//  Load in the Telcocell Resource (skillset) data
			try {
				loadTelcocellResourceDataFolder(dir, "skillsData");
			} catch (IOException e1) {
				//  Do not error, we will just have no resource data
				e1.printStackTrace();
			}
			
			//  Load in, and process the core Telcocell Roster file
			File inFile = new File(rosterDir, fileName);
			BufferedReader br = new BufferedReader(new FileReader(inFile));
			
			String line = br.readLine();	
			line = br.readLine();
			while (line!=null) {
				
				try {
					Roster roster = Roster.newInstance(line);
					checkAndAddRole(roster.getPositionName());
					
					//  Lookup the Telcocell Roster record
					TelcocellRoster telcocellRoster = findTelcocellRoster(roster.getPersonnelNumber());
					
					//  Lookup the Telcocell Resource record
					Resource telcocellResource = findTelcocellResource(roster.getLastName(), roster.getFirstName());
					
					//  Build the eResource
					//  Resource-Name = first-name + last-name
					//  Manager       = operational-manager
					//  Email         = email (Ericsson)
					//  Region        = personnel-area
					//  Office        = personnel-subarea (translated)
					//
					//  Type          = ACTIVE=Employee, EXTERNAL/OTHER=Contractor (from Telcocell Roster)
					//  **Team          = Client/CU
					//  Department    = internal corporate (BSS SI)
					//  Designation   = job posting (from Telcocell Resource)
					
					EResource eResource = new EResource();
					String resourceName = roster.getFirstName()
											+ " "
											+ roster.getLastName();
					eResource.setResourceName(resourceName);
					eResource.setManager(roster.getOperationalManager());
					eResource.setEmail(roster.getEmail());
					eResource.setRegion(roster.getPersonnelArea());
					
					//  Set default values for now.
					eResource.setDepartment(DEPARTMENT_BASE);
					
					//  Translate the office
					String translatedOffice = translate(
													officeTranslations, 
													roster.getPersonnelSubArea());
					eResource.setOffice(translatedOffice);
					
					//  If the resource was found, use it to set the designation
					if (telcocellResource!=null) {
						eResource.setDesignation(telcocellResource.getPosition());
					}
					
					//  If the telcocell Roster record was found, use it to set the type
					//  For the moment, default to EMPLOYEE
					eResource.setType(TYPE_EMPLOYEE);
					if (telcocellRoster!=null) {
						if (telcocellRoster.isEmployee()) {
							eResource.setType(TYPE_EMPLOYEE);
						} else {
							eResource.setType(TYPE_CONTRACTOR);
						}
					}
						
					pw.println(eResource.toCSV(","));
				} catch (ParseException e1) {}
				line = br.readLine();
			}
			
			br.close();
		}
			
		pw.flush();
		pw.close();
		
		//  Publish the completed role information
		publishRoleInformation(dir);

	}
	
	public static void main(String args[]) {
		RosterProcessor processor = new RosterProcessor();
		
		File dir = new File("C:/Users/estnpas/Desktop/eResource");
		
		try {
			processor.process(dir, "Telcocell Roster v1 10 clean.csv");
		} catch (IOException e1) {}
		
	}

}
