package com.ericsson.model;

import java.text.ParseException;

/**
 * A Domain object record to track the Skills record
 * @author estnpas
 *
 */
public class Skillset 
	extends BaseModel {
	
	private static final String NAME = "skillEntered";
	private static final String SKILLSETKEY = "skilltranslation";
	private static final String SKILLSETGROUP = "skillGroup"; // not used, just a place holder
	private static final String SKILLSETTYPE = "skillType"; // functional vs technical
	
	public Skillset() {
		register(NAME);
		register(SKILLSETKEY);
		register(SKILLSETGROUP);
		register(SKILLSETTYPE);
	}
	
	public String getName() {
		return getAsString(NAME);
	}
	
	public String getSkillsetKey() {
		return getAsString(SKILLSETKEY);
	}
	
	public String getSkillsetGroup() {
		return getAsString(SKILLSETGROUP);
	}
	
	public String getSkillsetType() {
		return getAsString(SKILLSETTYPE);
	}
	
	/**
	 * Create a SkillSet record.
	 * @param line
	 * @return
	 * @throws ParseException
	 */
	public static Skillset newInstance(String line)
		throws ParseException {
		Skillset skillset = new Skillset();
		//  The skillset file uses a comma (,) as a separator
		skillset.parse(line);
		return skillset;
	}

}
