package com.ericsson.model.db;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * Base layout for any DB table definition
 * @author estnpas
 *
 */
public abstract class BaseDBModel 
	implements Serializable {
	
	public static final String COL_ATTRIBUTEID = "AttributeId";
	public static final String COL_ATTRIBUTEVALUE = "AttributeValue";
	public static final String COL_LASTUPDATED = "Last_Updated";
	public static final String COL_ISDELETED = "Is_Deleted";
	public static final String COL_MODIFIEDBY = "ModifiedBy";
	
	private Date lastUpdated;
	private boolean deleted=false;
	private int modifiedBy;
	
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
	
	protected String convertToDate(Date date) {
		return DateFormatUtils.format(date, "dd-MMM-yyyy HH:mm:ss");
	}
	
	protected int convertToBit(boolean value) {
		return (value) ? 1 : 0;
	}
	
	protected int convertToBit(Boolean value) {
		return (value.booleanValue()) ? 1 : 0;
	}
	
	protected String convertField(Object value) {
		String sqlValue = "";
		if (value==null) {
			sqlValue = "NULL";
		} else if (value instanceof Date) {
			sqlValue = convertToDate((Date)value);
		} else if (value instanceof Boolean) {
			sqlValue = String.valueOf(convertToBit((Boolean)value));
		}
		return sqlValue;
	}
	
	public abstract String getTableName();
	public abstract String generateInsertSQL();
	
	protected void startInsertSQL(StringBuffer buffer) {
		buffer.append("INSERT INTO ");
		buffer.append(getTableName());
	}
	
	protected void startColumnsClause(StringBuffer buffer) {
		buffer.append("(");
	}
	
	protected void endColumnsClause(StringBuffer buffer) {
		buffer.append(")");
	}
	
	protected void addColumn(
						StringBuffer buffer, 
						String columnName,
						boolean appendComma) {
		buffer.append(columnName);
		if (appendComma) {
			buffer.append(",");
		}
	}
	
	protected void addColumn(
						StringBuffer buffer, 
						String columnName) {
		addColumn(buffer, columnName, false);
	}
	
	protected void addDefaultColumns(StringBuffer buffer) {
		addColumn(buffer, COL_LASTUPDATED, true);
		addColumn(buffer, COL_ISDELETED, true);
		addColumn(buffer, COL_MODIFIEDBY);
	}
	
	protected void startValuesClause(StringBuffer buffer) {
		buffer.append(" VALUES (");
	}
	
	protected void endValuesClause(StringBuffer buffer) {
		buffer.append(")");
	}
	
	protected void addField(
						StringBuffer buffer, 
						Object value, 
						boolean appendComma) {
		buffer.append(convertField(value));
		if (appendComma) {
			buffer.append(",");
		}
	}
	
	protected void addField(
						StringBuffer buffer,
						Object value) {
		addField(buffer, value, false);
	}
	
	protected void addDefaultFields(StringBuffer buffer) {
		addField(buffer, getLastUpdated(), true);
		addField(buffer, isDeleted(), true);
		addField(buffer, getModifiedBy());
	}
}
