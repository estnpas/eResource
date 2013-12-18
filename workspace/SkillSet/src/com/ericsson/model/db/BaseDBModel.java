package com.ericsson.model.db;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

public abstract class BaseDBModel 
	implements Serializable {
	
	public static final String COL_ATTRIBUTEVALUEID = "AttributeValueId";
	public static final String COL_ATTRIBUTEID = "AttributeId";
	public static final String COL_ATTRIBUTEVALUE = "AttributeValue";
	public static final String COL_LASTUPDATED = "Last_Updated";
	public static final String COL_ISDELETED = "Is_Deleted";
	public static final String COL_MODIFIEDBY = "ModifiedBy";
	
	protected String convertToDate(Date date) {
		return DateFormatUtils.format(date, "dd-MMM-yyyy HH:mm:ss");
	}
	
	protected int convertToBit(boolean value) {
		return (value) ? 1 : 0;
	}
}
