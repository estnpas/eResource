package com.ericsson.model;

import java.text.ParseException;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrTokenizer;

import com.ericsson.skillset.Constants;

/**
 * A base Domain object model
 * @author estnpas
 *
 */
public abstract class BaseModel 
	implements Constants {
	
	private Field[] fields = new Field[0];
	
	private Field find(String fieldName) {
		for (Field field : fields) {
			if (StringUtils.equalsIgnoreCase(fieldName, field.getName())) {
				return field;
			}
		}
		return null;
	}
	
	/**
	 * Retrieve the information for the specified field (index)
	 * @param index
	 * @return
	 * @throws Exception
	 */
	protected Field getField(int index) 
		throws Exception {
		if (index>fields.length) {
			throw new Exception("Field with index " + index + " not found.");
		}
		return fields[index];
	}
	
	/**
	 * Register a field
	 * @param fieldName
	 */
	protected void register(String fieldName) {
		//  Leave the column header alone, ie do not force lowercase
		if (!ArrayUtils.contains(fields, fieldName)) {
			Field field = new Field(fieldName);
			fields = ArrayUtils.add(fields, field);
		}
	}
	
	/**
	 * Return a list of the fields for this object
	 * @return
	 */
	public String[] getFieldList() {
		String[] fieldList = new String[0];
		for (Field field : fields) {
			fieldList = ArrayUtils.add(fieldList, field.getName());
		}
		return fieldList;
	}
	
	/**
	 * Set the value of the specified field
	 * @param fieldName
	 * @param value
	 */
	protected void set(String fieldName, Object value) {
		Field field = find(fieldName);
		if (field!=null) {
			field.setValue(value);
		}
	}
	
	/**
	 * Return the value from the specified field
	 * @param fieldName
	 * @return
	 */
	public Object get(String fieldName) {
		Object value = null;
		Field field = find(fieldName);
		if (field!=null) {
			value = field.getValue();
		}
		return value;
	}
	
	/**
	 * Return the value from the specified field
	 * @param fieldName
	 * @return
	 */
	public String getAsString(String fieldName) {
		return (String)get(fieldName);
	}
	
	private void openTag(StringBuffer buffer, String name) {
		buffer.append("<");
		buffer.append(name);
		buffer.append(">");
	}
	
	private void closeTag(StringBuffer buffer, String name) {
		buffer.append("</");
		buffer.append(name);
		buffer.append(">");
	}
	
	/**
	 * Start a start XML tag
	 * @param buffer
	 */
	protected void startTag(StringBuffer buffer) {
		openTag(buffer, this.getClass().getName());
	}
	
	/**
	 * Create a end XML tag
	 * @param buffer
	 */
	protected void endTag(StringBuffer buffer) {
		closeTag(buffer, this.getClass().getName());
	}
	
	/**
	 * Create a field value tag
	 * @param buffer
	 * @param name
	 * @param value
	 */
	protected void fieldTag(StringBuffer buffer, String name, Object value) {
		openTag(buffer, name);
		if (value!=null) {
			buffer.append(value);
		}
		closeTag(buffer, name);
	}
	
	/**
	 * Parse the input line to create the object.<p>Use the default separator.</p>
	 * @param line
	 * @throws ParseException
	 */
	protected void parse(String line)
		throws ParseException {
		parse(line, "|");
	}
	
	/**
	 * Parse the input line to create the object
	 * @param line
	 * @param sepStr  the separator character to handle
	 * @throws ParseException
	 */
	protected void parse(String line, String sepStr)
		throws ParseException {

		//  Parse the line....
		StrTokenizer tokenizer = new StrTokenizer(line, sepStr);
		tokenizer.setIgnoreEmptyTokens(false);
		
		for (int index=0;
				tokenizer.hasNext() && index<fields.length;
					index++) {
			String token = tokenizer.nextToken();
			
			if (StringUtils.isNotBlank(token)) {
				try {
					Field field = getField(index);
					field.setValue(token);
				} catch (Exception e1) {
					e1.printStackTrace();
					throw new ParseException(line, index);
				}
			}
		}
	}
	
	/**
	 * produce an XML representation of the object
	 * @return
	 */
	public String toXMLString() {
		StringBuffer buffer = new StringBuffer();
		startTag(buffer);
		for (Field field : fields) {
			fieldTag(buffer, field.getName(), field.getValue());
		}
		endTag(buffer);
		return buffer.toString();
	}
	
	/**
	 * Produce a CSV representation of the object values
	 * @param sepStr
	 * @return
	 */
	public String toCSV(String sepStr) {
		StringBuffer buffer = new StringBuffer();
		for(Field field : fields) {
			if (buffer.length()>0) {
				buffer.append(sepStr);
			}
			Object value = field.getValue();
			if (value!=null) {
				//  Encapsulate the value to insure that any funny characters are handled appropriately
				String encapsulatedValue = "\""+value+"\"";
				buffer.append(encapsulatedValue);
			}
		}
		return buffer.toString();
	}
	
	private static final class Field {
		String name;
		Object value;
		
		public Field(String name) {
			this.name=name;
		}
		
		public String getName() {
			return name;
		}
		
		public void setValue(Object value) {
			this.value=value;
		}
		
		public Object getValue() {
			return value;
		}
	}

}
