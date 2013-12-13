package com.ericsson.model;

import java.text.ParseException;

/**
 * Define the domain model to handle generalized translations
 * @author estnpas
 *
 */
public class Translation 
	extends BaseModel {
	
	private static final String KEY = "key";
	private static final String VALUE = "value";
	
	public Translation() {
		register(KEY);
		register(VALUE);
	}
	
	public String getKey() {
		return getAsString(KEY);
	}
	
	public String getValue() {
		return getAsString(VALUE);
	}
	
	public static Translation newInstance(String line, String sepStr)
		throws ParseException {
		Translation translation = new Translation();
		translation.parse(line, sepStr);
		return translation;
	}
	
	public static Translation newInstance(String line)
		throws ParseException {
		Translation translation = new Translation();
		translation.parse(line);
		return translation;
	}

}
