package com.ericsson.model.db;

public class ResourceMaster 
	extends BaseDBModel {
	
	public static final String TABLE_NAME = "ResourceMaster";
	
	public static final String COL_RESOURCEID = "ResourceId";
	public static final String COL_TYPEID = "TypeId";
	public static final String COL_RESOURCENAME = "ResourceName";
	
	private int resourceId;
	private int typeId;
	private String resourceName;

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
