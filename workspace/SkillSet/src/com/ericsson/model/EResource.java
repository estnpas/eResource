package com.ericsson.model;

/**
 * A Domain object model to handle the published eResource resource record structure
 * @author estnpas
 *
 */
public class EResource 
	extends BaseModel {
	
	private static final String RESOURCENAME = "Resource Name";
	private static final String TYPE = "Type";
	private static final String MANAGER = "Manager";
	private static final String DESIGNATION = "Designation";
	private static final String REGION = "Region";
	private static final String OFFICE = "Office";
	private static final String DEPARTMENT = "Department";
	private static final String TEAM = "Team";
	private static final String EMAIL = "Email";
	
	public EResource() {
		register(RESOURCENAME);
		register(TYPE);
		register(MANAGER);
		register(DESIGNATION);
		register(REGION);
		register(OFFICE);
		register(DEPARTMENT);
		register(TEAM);
		register(EMAIL);
	}
	
	public String getResourceName() {
		return getAsString(RESOURCENAME);
	}
	
	public void setResourceName(String resourceName) {
		set(RESOURCENAME, resourceName);
	}
	
	public String getType() {
		return getAsString(TYPE);
	}
	
	public void setType(String type) {
		set(TYPE, type);
	}
	
	public String getManager() {
		return getAsString(MANAGER);
	}
	
	public void setManager(String manager) {
		set(MANAGER, manager);
	}
	
	public String getDesignation() {
		return getAsString(DESIGNATION);
	}
	
	public void setDesignation(String designation) {
		set(DESIGNATION, designation);
	}
	
	public String getRegion() {
		return getAsString(REGION);
	}
	
	public void setRegion(String region) {
		set(REGION, region);
	}
	
	public String getOffice() {
		return getAsString(OFFICE);
	}
	
	public void setOffice(String office) {
		set(OFFICE, office);
	}
	
	public String getDepartment() {
		return getAsString(DEPARTMENT);
	}
	
	public void setDepartment(String department) {
		set(DEPARTMENT, department);
	}
	
	public String getTeam() {
		return getAsString(TEAM);
	}
	
	public void setTeam(String team) {
		set(TEAM, team);
	}
	
	public String getEmail() {
		return getAsString(EMAIL);
	}
	
	public void setEmail(String email) {
		set(EMAIL, email);
	}
	
	/**
	 * Produce a list of the column (field) headers from the specified fields.
	 * @param sepStr
	 * @return
	 */
	public static String getFieldHeaders(String sepStr) {
		EResource rsrc = new EResource();
		StringBuffer buffer = new StringBuffer();
		for (String fieldName : rsrc.getFieldList()) {
			if (buffer.length()>0) {
				buffer.append(sepStr);
			}
			buffer.append(fieldName);
		}
		return buffer.toString();
	}
}
