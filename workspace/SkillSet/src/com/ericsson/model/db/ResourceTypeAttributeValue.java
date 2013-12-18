package com.ericsson.model.db;

import java.util.Date;

/**
 * Define a model to encapsulate the Resource-Type Attribute Value DB model
 * @author estnpas
 *
 */
public class ResourceTypeAttributeValue 
	extends BaseDBModel {
	
private static final String TABLE_NAME = "ResourceTypeAttributeValue";
	
	private int attributeValueId;
	private int attributeId;
	private String attributeValue;
	
	public int getAttributeValueId() {
		return attributeValueId;
	}
	public void setAttributeValueId(int attributeValueId) {
		this.attributeValueId = attributeValueId;
	}
	public int getAttributeId() {
		return attributeId;
	}
	public void setAttributeId(int attributeId) {
		this.attributeId = attributeId;
	}
	public String getAttributeValue() {
		return attributeValue;
	}
	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}


	
	public String generateInsertSQL() {
		StringBuffer buffer = new StringBuffer();
		
		startInsertSQL(buffer);
		
		startColumnsClause(buffer);
		addColumn(buffer,COL_ATTRIBUTEVALUEID); buffer.append(",");
		addColumn(buffer,COL_ATTRIBUTEID); buffer.append(",");
		addColumn(buffer,COL_ATTRIBUTEVALUE); buffer.append(",");
		addDefaultColumns(buffer);
		endColumnsClause(buffer);
		
		startValuesClause(buffer);		
		addField(buffer, getAttributeValueId(), true);
		addField(buffer, getAttributeId(), true);
		addField(buffer, getAttributeValue(), true);
		addDefaultFields(buffer);
		endValuesClause(buffer);
		
		return buffer.toString();
	}
	
	public String getTableName() {
		return TABLE_NAME;
	}

}
