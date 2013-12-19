package com.ericsson.model.db;

import java.util.Date;

/**
 * Define the ResourceMaster DB table
 * @author estnpas
 *
 */
public class ResourceMaster 
	extends BaseDBModel {
	
	public static final String TABLE_NAME = "ResourceMaster";
	
	public static final String COL_RESOURCEID = "ResourceId";
	public static final String COL_TYPEID = "TypeId";
	public static final String COL_RESOURCENAME = "ResourceName";
	public static final String COL_MANAGERID = "ManagerId";
	public static final String COL_STATUS = "Status";
	public static final String COL_DESIGNATIONID = "DesignationId";
	public static final String COL_TEAMID = "TeamId";
	public static final String COL_EMAIL = "Email";
	public static final String COL_NOTES = "Notes";
	public static final String COL_STARTDATE = "StartDate";
	public static final String COL_TERMINATIONDATE = "TerminationDate";
	public static final String COL_PHONE = "Phone";
	public static final String COL_TIMESHEETAPPROVAL = "TimeSheetApproval";
	public static final String COL_UPDATEDBY = "UpdatedBy";
	public static final String COL_UPDATEDATE = "UpdateDate";
	
	private int resourceId;
	private int typeId;
	private String resourceName;
	private int managerId;
	private int status;
	private int designationId;
	private int teamId;
	private String email;
	private String notes;
	private Date startDate;
	private Date terminationDate;
	private String phone;
	private int timeSheetApproval;
	private String updatedBy;
	private Date updateDate;

	public int getResourceId() {
		return resourceId;
	}

	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public int getManagerId() {
		return managerId;
	}

	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getDesignationId() {
		return designationId;
	}

	public void setDesignationId(int designationId) {
		this.designationId = designationId;
	}

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getTerminationDate() {
		return terminationDate;
	}

	public void setTerminationDate(Date terminationDate) {
		this.terminationDate = terminationDate;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getTimeSheetApproval() {
		return timeSheetApproval;
	}

	public void setTimeSheetApproval(int timeSheetApproval) {
		this.timeSheetApproval = timeSheetApproval;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public String getTableName() {
		return TABLE_NAME;
	}

	@Override
	public String generateInsertSQL() {
		// TODO Auto-generated method stub
		return null;
	}

}
