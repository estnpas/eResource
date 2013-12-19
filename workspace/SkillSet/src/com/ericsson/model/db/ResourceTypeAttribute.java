package com.ericsson.model.db;

/**
 * Define the ResourceTypeAttribute DB table
 * @author estnpas
 *
 */
public class ResourceTypeAttribute 
	extends BaseDBModel {
	
	public static final String TABLE_NAME = "ResourceTypeAttribute";
	
	public static final String COL_ID = "Id";
	public static final String COL_RESOURCETYPEID = "ResourceTypeId";
	public static final String COL_NAME = "Name";
	
	
	private int id;
	private int resourceTypeId;
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getResourceTypeId() {
		return resourceTypeId;
	}

	public void setResourceTypeId(int resourceTypeId) {
		this.resourceTypeId = resourceTypeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
