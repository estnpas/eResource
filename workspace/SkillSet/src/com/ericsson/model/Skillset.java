package com.ericsson.model;

import java.text.ParseException;

/**
 * A Domain object record to track the Skills record
 * @author estnpas
 *
 */
public class Skillset 
	extends BaseModel {
	
	private static final String NAME = "SpreadsheetName";
	private static final String SKILLSETKEY = "SkillsetKey";
	private static final String SKILLSETGROUP = "SkillsetGroup";
	
	public Skillset() {
		register(NAME);
		register(SKILLSETKEY);
		register(SKILLSETGROUP);
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
	
	public static Skillset newInstance(String line)
		throws ParseException {
		Skillset skillset = new Skillset();
		//  The skillset file uses a comma (,) as a separator
		skillset.parse(line, ",");
		return skillset;
	}

}
