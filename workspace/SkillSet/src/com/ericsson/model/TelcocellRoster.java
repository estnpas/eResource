package com.ericsson.model;

import java.text.ParseException;

import org.apache.commons.lang3.StringUtils;

/**
 * Define the domain record for the Telcocell Roster spreadsheet
 * @author estnpas
 *
 */
public class TelcocellRoster 
	extends BaseModel {
	
	private static String COMPANYCODE = "companyCode";
	private static final String PERSONNELAREA = "personnelArea";
	private static final String PERSONNELNUMBER = "personnelNumber";
	private static final String LASTNAME = "lastName";
	private static final String FIRSTNAME = "firstName";
	private static final String BILLABLE = "billable";
	private static final String BLANK1 = "blank1";
	private static final String EMPLOYEESTATUS = "employeeStatus";
	private static final String EMPLOYEEGROUP = "employeeGroup";
	private static final String EMPLOYEESUBGROUP = "employeeSubgroup";
	private static final String NAMEOFSUPERIOROM = "nameOfSuperiorOM";
	private static final String ORGANIZATIONALUNIT = "organizationalUnit";
	private static final String WORKCONTRACT = "workContract";
	private static final String COSTCENTRE = "costCentre";
	private static final String POSITIONNAME = "positionName";
	
	public TelcocellRoster() {
		register(COMPANYCODE);
		register(PERSONNELAREA);
		register(PERSONNELNUMBER);
		register(LASTNAME);
		register(FIRSTNAME);
		register(BILLABLE);
		register(BLANK1);
		register(EMPLOYEESTATUS);
		register(EMPLOYEEGROUP);
		register(EMPLOYEESUBGROUP);
		register(NAMEOFSUPERIOROM);
		register(ORGANIZATIONALUNIT);
		register(WORKCONTRACT);
		register(COSTCENTRE);
		register(POSITIONNAME);
	}
	
	public String getCompanyCode() {
		return getAsString(COMPANYCODE);
	}
	
	public String getPersonnelArea() {
		return getAsString(PERSONNELAREA);
	}
	
	public String getPersonnelNumber() {
		return getAsString(PERSONNELNUMBER);
	}
	
	public String getLastName() {
		return getAsString(LASTNAME);
	}
	
	public String getFirstName() {
		return getAsString(FIRSTNAME);
	}
	
	public String getBillable() {
		return getAsString(BILLABLE);
	}
	
	public String getEmployeeStatus() {
		return getAsString(EMPLOYEESTATUS);
	}
	
	public boolean isActive() {
		String employeeStatus = getAsString(EMPLOYEESTATUS);
		return StringUtils.equalsIgnoreCase(employeeStatus, STATUS_ACTIVE);
	}
	
	public String getEmployeeGroup() {
		return getAsString(EMPLOYEEGROUP);
	}
	
	public boolean isEmployee() {
		String employeeGroup = getAsString(EMPLOYEEGROUP);
		return StringUtils.equalsIgnoreCase(employeeGroup, GROUP_ACTIVE);
	}
	
	public String getPositionName() {
		return getAsString(POSITIONNAME);
	}
	
	public static TelcocellRoster newInstance(String line)
		throws ParseException {
		TelcocellRoster roster = new TelcocellRoster();
		roster.parse(line, ",");
		return roster;
	}
	
}
