package com.ericsson.model.db;

/**
 * Define the ResourceMasterAttributes DB table
 * @author estnpas
 *
 */
public class ResourceMasterAttributes 
	extends BaseDBModel {
	
	public static final String TABLE_NAME = "ResourceMasterAttributes";
	
	public static final String COL_ID = "Id";
	public static final String COL_RESOURCEID = "ResourceId";
	public static final String COL_ATTRIBUTEID = "AttributeId";
	public static final String COL_ATTRIBUTEVALUEID = "AttributeValueId";
	
	private int id;
	private int resourceId;
	private int attributeId;
	private int attributeValueId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getResourceId() {
		return resourceId;
	}
	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}
	public int getAttributeId() {
		return attributeId;
	}
	public void setAttributeId(int attributeId) {
		this.attributeId = attributeId;
	}
	public int getAttributeValueId() {
		return attributeValueId;
	}
	public void setAttributeValueId(int attributeValueId) {
		this.attributeValueId = attributeValueId;
	}
	
	public String generateInsertSQL() {
		StringBuffer buffer = new StringBuffer();
		
		startInsertSQL(buffer);
		
		startColumnsClause(buffer);
		addColumn(buffer, COL_ID, true);
		addColumn(buffer, COL_RESOURCEID, true);
		addColumn(buffer, COL_ATTRIBUTEID, true);
		addColumn(buffer, COL_ATTRIBUTEVALUEID, true);
		addDefaultColumns(buffer);
		endColumnsClause(buffer);
		
		startValuesClause(buffer);
		addField(buffer, getId(), true);
		addField(buffer, getResourceId(), true);
		addField(buffer, getAttributeId(), true);
		addField(buffer, getAttributeValueId(), true);
		addDefaultFields(buffer);
		endValuesClause(buffer);
		
		return buffer.toString();
	}
	
	public String getTableName() {
		return TABLE_NAME;
	}

}
