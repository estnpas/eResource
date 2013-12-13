package com.ericsson.model;

import java.text.ParseException;

/**
 * A domain object to describe the roster information from Telcocell
 * @author estnpas
 *
 */
public class Roster 
	extends BaseModel {

	private static final String COMPANYCODE = "companyCode";
	private static final String PREVPERSONNELNUMBER = "previousPersonnelNumber";
	private static final String COUNTED = "counter";
	private static final String ORGANIZATION = "organization";
	private static final String PERSONNELNUMBER = "personnelNumber";
	private static final String LASTNAME = "lastName";
	private static final String FIRSTNAME = "firstName";
	private static final String POSITIONNAME = "positionName";
	private static final String ROLE = "role";
	private static final String PRACTICE = "practice";
	private static final String OPERATIONALMANAGER = "operationalManager";
	private static final String LEGALMANAGER = "legalManager";
	private static final String PERSONNELAREA = "personnelArea";
	private static final String ORGANIZATIONALUNIT = "organizationalUnit";
	private static final String OPERATIONALUNITNAME = "operationalUnitName";
	private static final String EMPLOYMENTSTATUS = "employmentStatus";
	private static final String EMPLOYEEGROUP = "employeeGroup";
	private static final String EMPLOYEESUBGROUP = "employeeSubGroup";
	private static final String SUPERIORNAME = "superiorName";
	private static final String ORGUNIT = "orgUnit";
	private static final String WORKCONTRACT = "workContract";
	private static final String COSTCENTRE = "costCentre";
	private static final String POSITION = "position";
	private static final String ORGABBR = "orgAbbr";
	private static final String JOBNUMBER = "jobNumber";
	private static final String JOB = "JOB";
	private static final String PERSONNELSUBAREA = "personnelSubArea";
	private static final String JOBAREAABBROM = "jobAreaAbbrOM";
	private static final String JOBAREAOM = "jobAreaOM";
	private static final String JOBFAMILYNAMEOM = "jobFamilyNameOM";
	private static final String JOBAREANAMEOM = "jobAreaNameOM";
	private static final String JOBFAMILYABBROM = "jobFamilyAbbrOM";
	private static final String JOBFAMILYOM = "jobFamilyOM";
	private static final String OPERATIONALUNIT = "operationalUnit";
	private static final String OPERATIONALUNITABBR = "operationalUnitAbbr";
	private static final String OPERATIONALMANAGEREMPNO = "operationalManagerEmpNo";
	private static final String CORPORATEID = "corporateId";
	private static final String TECHNICALDATEOFENTRY = "technicalDateofEntry";
	private static final String FIRSTWORKINGDATE = "firstWorkingDate";
	private static final String BUILDING = "building";
	private static final String EMAIL = "email";
	private static final String JOBSTAGE = "jobStage";
	private static final String COMPETENCEDOMAIN = "competenceDomain";
	private static final String COMPETENCEDOMAINNAME = "competenceDomainName";
	private static final String INDUSTRYVERTICAL = "industryVertical";
	private static final String INDUSTRYVERTICALNAME = "industryVerticalName";
	private static final String SERVICEAREA = "serviceArea";
	private static final String SERVICEAREANAME = "serviceAreaName";
	
	public Roster() {
		register(COMPANYCODE);
		register(PREVPERSONNELNUMBER);
		register(COUNTED);
		register(ORGANIZATION);
		register(PERSONNELNUMBER);
		register(LASTNAME);
		register(FIRSTNAME);
		register(POSITIONNAME);
		register(ROLE);
		register(PRACTICE);
		register(OPERATIONALMANAGER);
		register(LEGALMANAGER);
		register(PERSONNELAREA);
		register(ORGANIZATIONALUNIT);
		register(OPERATIONALUNITNAME);
		register(EMPLOYMENTSTATUS);
		register(EMPLOYEEGROUP);
		register(EMPLOYEESUBGROUP);
		register(SUPERIORNAME);
		register(ORGUNIT);
		register(WORKCONTRACT);
		register(COSTCENTRE);
		register(POSITION);
		register(ORGABBR);
		register(JOBNUMBER);
		register(JOB);
		register(PERSONNELSUBAREA);
		register(JOBAREAABBROM);
		register(JOBAREAOM);
		register(JOBFAMILYNAMEOM);
		register(JOBAREANAMEOM);
		register(JOBFAMILYABBROM);
		register(JOBFAMILYOM);
		register(OPERATIONALUNIT);
		register(OPERATIONALUNITABBR);
		register(OPERATIONALMANAGEREMPNO);
		register(CORPORATEID);
		register(TECHNICALDATEOFENTRY);
		register(FIRSTWORKINGDATE);
		register(BUILDING);
		register(EMAIL);
		register(JOBSTAGE);
		register(COMPETENCEDOMAIN);
		register(COMPETENCEDOMAINNAME);
		register(INDUSTRYVERTICAL);
		register(INDUSTRYVERTICALNAME);
		register(SERVICEAREA);
		register(SERVICEAREANAME);
	}
	
	public String getCompanyCode() {
		return getAsString(COMPANYCODE);
	}
	
	public String getPreviousPersonnelNumber() {
		return getAsString(PREVPERSONNELNUMBER);
	}
	
	public String getOrganization() {
		return getAsString(ORGANIZATION);
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
	
	public String getPositionName() {
		return getAsString(POSITIONNAME);
	}
	
	public String getRole() {
		return getAsString(ROLE);
	}
	
	public String getPractice() {
		return getAsString(PRACTICE);
	}
	
	public String getOperationalManager() {
		return getAsString(OPERATIONALMANAGER);
	}
	
	public String getLegalManager() {
		return getAsString(LEGALMANAGER);
	}
	
	public String getPersonnelArea() {
		return getAsString(PERSONNELAREA);
	}
	
	public String getOrganizationalUnit() {
		return getAsString(ORGANIZATIONALUNIT);
	}
	
	public String getOperationalUnitName() {
		return getAsString(OPERATIONALUNITNAME);
	}
	
	public String getEmployeeStatus() {
		return getAsString(EMPLOYMENTSTATUS);
	}
	
	public String getEmployeeGroup() {
		return getAsString(EMPLOYEEGROUP);
	}
	
	public String getEmployeeSubGroup() {
		return getAsString(EMPLOYEESUBGROUP);
	}
	
	public String getSuperiorName() {
		return getAsString(SUPERIORNAME);
	}
	
	public String getOrgUnit() {
		return getAsString(ORGUNIT);
	}
	
	public String getWorkContract() {
		return getAsString(WORKCONTRACT);
	}
	
	public String getCostCentre() {
		return getAsString(COSTCENTRE);
	}
	
	public String getPosition() {
		return getAsString(POSITION);
	}
	
	public String getOrganizationAbbr() {
		return getAsString(ORGABBR);
	}
	
	public String getJobNumber() {
		return getAsString(JOBNUMBER);
	}
	
	public String getJob() {
		return getAsString(JOB);
	}
	
	public String getPersonnelSubArea() {
		return getAsString(PERSONNELSUBAREA);
	}
	
	public String getJobAreaAbbrOM() {
		return getAsString(JOBAREAABBROM);
	}
	
	public String getJobAreaOM() {
		return getAsString(JOBAREAOM);
	}
	
	public String getJobFamilyNameOM() {
		return getAsString(JOBFAMILYNAMEOM);
	}
	
	public String getJobAreaNameOM() {
		return getAsString(JOBAREANAMEOM);
	}
	
	public String getJobFamilyAbbrOM() {
		return getAsString(JOBFAMILYABBROM);
	}
	
	public String getJobFamilyOM() {
		return getAsString(JOBFAMILYOM);
	}
	
	public String getOperationalUnit() {
		return getAsString(OPERATIONALUNIT);
	}
	
	public String getOperationalUnitAbbr() {
		return getAsString(OPERATIONALUNITABBR);
	}
	
	public String getOperationalManagerEmployeeNumber() {
		return getAsString(OPERATIONALMANAGEREMPNO);
	}
	
	public String getCorporateId() {
		return getAsString(CORPORATEID);
	}
	
	public String getTechnicalDateOfEntry() {
		return getAsString(TECHNICALDATEOFENTRY);
	}
	
	public String getFirstWorkingDate() {
		return getAsString(FIRSTWORKINGDATE);
	}
	
	public String getBuilding() {
		return getAsString(BUILDING);
	}
	
	public String getEmail() {
		return getAsString(EMAIL);
	}
	
	public String getJobStage() {
		return getAsString(JOBSTAGE);
	}
	
	public String getCompetenceDomain() {
		return getAsString(COMPETENCEDOMAIN);
	}
	
	public String getCompetenceDomainName() {
		return getAsString(COMPETENCEDOMAINNAME);
	}
	
	public String getIndustryVertical() {
		return getAsString(INDUSTRYVERTICAL);
	}
	
	public String getIndustryVerticalName() {
		return getAsString(INDUSTRYVERTICALNAME);
	}
	
	public String getServiceArea() {
		return getAsString(SERVICEAREA);
	}
	
	public String getServiceAreaName() {
		return getAsString(SERVICEAREANAME);
	}
	
	/**
	 * Create a new instance of the Roster record and parse out the specified line
	 * @param line
	 * @return
	 * @throws ParseException
	 */
	public static Roster newInstance(String line) 
		throws ParseException {
		Roster roster = new Roster();
		roster.parse(line);
		return roster;
	}
}
