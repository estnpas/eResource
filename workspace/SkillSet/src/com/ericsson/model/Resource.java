package com.ericsson.model;

import java.text.ParseException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrTokenizer;

/**
 * Define the Domain Resource object from the inbound Telcocell skillset records
 * @author estnpas
 *
 */
public class Resource 
	extends BaseModel {
	
	private static final String LASTNAME = "lastName";
	private static final String FIRSTNAME = "firstName";
	private static final String POSITION = "position";
	private static final String OPERATIONALMANAGER = "operationManager";
	private static final String SKILLLIST = "skillList";
	private static final String PROJECTALLOCATION = "projectAllocation";
	private static final String PROJECTLOCATION = "projectLocation";
	private static final String PROJECTDURATION = "projectDuration";
	private static final String PROPOSEDTO = "proposedTo";
	
	public Resource() {
		register(LASTNAME);
		register(FIRSTNAME);
		register(POSITION);
		register(OPERATIONALMANAGER);
		register(SKILLLIST);
		register(PROJECTALLOCATION);
		register(PROJECTLOCATION);
		register(PROJECTDURATION);
		register(PROPOSEDTO);
	}
	
	private String[] skills = null;
	
	public String getLastName() {
		return getAsString(LASTNAME);
	}
	
	public String getFirstName() {
		return getAsString(FIRSTNAME);
	}
	
	public String getFullName() {
		String lastName = getAsString(LASTNAME);
		String firstName = getAsString(FIRSTNAME);
		
		//  Handle odd cases where things like the lastname are not properly defined.
		String fullName = firstName
							+ " "
							+ lastName;
		
		return fullName;
	}
	public String getPosition() {
		return getAsString(POSITION);
	}
	public String getOperationalManager() {
		return getAsString(OPERATIONALMANAGER);
	}
	public String getProjectAllocation() {
		return getAsString(PROJECTALLOCATION);
	}
	public String getProjectLocation() {
		return getAsString(PROJECTLOCATION);
	}
	public String getProjectDuration() {
		return getAsString(PROJECTDURATION);
	}
	
	/**
	 * Set the skillset field which is a comma-separated list of the resource skills
	 * @param skillList
	 */
	public void setSkillList(String skillList) {
		set(SKILLLIST, skillList);
		
		//  reset the list of skills.....
		skills = null;
	}
	
	/**
	 * Return a comma-separated representation of the skills list
	 * @return
	 */
	public String getSkillList() {
		//  Rebuild the list of skills
		StringBuffer buffer = new StringBuffer();
		for (String skill : getSkills()) {
			if (buffer.length()>0) {
				buffer.append(",");
			}
			buffer.append(skill);
		}
		return buffer.toString();
	}
	
	/**
	 * Return a string array which contains the resource skills
	 * @return
	 */
	public String[] getSkills() {
		
		//  If we have not yet processed, then do so right now
		if (skills==null) {
			skills = new String[0];
			// Strip off any odd characters
			String skillsList = (String)get(SKILLLIST);
			skillsList = StringUtils.replaceChars(skillsList, replaceString, "");	
			
			if (StringUtils.isNotBlank(skillsList)) {
				//  Break down the skills into individual items
				StrTokenizer tokenizer = new StrTokenizer(skillsList, ",");
				skills = tokenizer.getTokenArray();
			}
		}
		
		return skills;
	}
	
	/**
	 * Return a list of the field/column headers
	 * @param sepStr
	 * @return
	 */
	public static String getFieldHeaders(String sepStr) {
		Resource rsrc = new Resource();
		StringBuffer buffer = new StringBuffer();
		for (String fieldName : rsrc.getFieldList()) {
			if (buffer.length()>0) {
				buffer.append(sepStr);
			}
			buffer.append(fieldName);
		}
		return buffer.toString();
	}
	
	/**
	 * Create a new instance of the Resource domain object
	 * @param line
	 * @return
	 * @throws ParseException
	 */
	public static Resource newInstance(String line) 
		throws ParseException {
		Resource rsrc = new Resource();
		rsrc.parse(line, "|");
		
		return rsrc;
	}
}
