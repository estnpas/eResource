package com.ericsson.model.db;

import java.util.Date;

public class ResourceTypeAttributeValue 
	extends BaseDBModel {
	
private static final String TABLE_NAME = "ResourceTypeAttributeValue";
	
	private int attributeValueId;
	private int attributeId;
	private String attributeValue;
	private Date lastUpdated;
	private boolean deleted=false;
	private int modifiedBy;
	
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
	public Date getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	public int getModifiedBy() {
		return modifiedBy;
	}
	
	public String generateInsertSQL() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("INSERT INTO ");
		buffer.append(TABLE_NAME);
		buffer.append("(");
		buffer.append(COL_ATTRIBUTEVALUEID); buffer.append(",");
		buffer.append(COL_ATTRIBUTEID); buffer.append(",");
		buffer.append(COL_ATTRIBUTEVALUE); buffer.append(",");
		buffer.append(COL_LASTUPDATED); buffer.append(",");
		buffer.append(COL_ISDELETED); buffer.append(",");
		buffer.append(COL_MODIFIEDBY);
		buffer.append(") VALUES (");
		
		//  Table values
		buffer.append(getAttributeValueId()); buffer.append(",");
		buffer.append(getAttributeId()); buffer.append(",");
		buffer.append(getAttributeValue()); buffer.append(",");
		buffer.append(convertToDate(getLastUpdated())); buffer.append(",");
		buffer.append(convertToBit(isDeleted())); buffer.append(",");
		buffer.append(getModifiedBy());
		
		buffer.append(")");
		
		return buffer.toString();
	}
	


}
