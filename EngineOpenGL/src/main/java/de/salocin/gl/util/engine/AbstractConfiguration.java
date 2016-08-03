package de.salocin.gl.util.engine;

public abstract class AbstractConfiguration {
	
	private final String key;
	private Object objectValue;
	private String stringValue;
	private int intValue;
	private double doubleValue;
	private boolean booleanValue;
	
	public AbstractConfiguration(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}
	
	public boolean isPresent() {
		return objectValue != null;
	}
	
	public void setValue(Object value) {
		objectValue = value;
		stringValue = value.toString();
		
		try {
			intValue = Integer.parseInt(stringValue);
		} catch (NumberFormatException e) {
			intValue = -1;
		}
		
		try {
			doubleValue = Double.parseDouble(stringValue);
		} catch (NumberFormatException e) {
			doubleValue = -1.0;
		}
		
		booleanValue = Boolean.parseBoolean(stringValue);
	}
	
	public Object getObjectValue() {
		return objectValue;
	}
	
	public String getStringValue() {
		return stringValue;
	}
	
	public int getIntValue() {
		return intValue;
	}
	
	public double getDoubleValue() {
		return doubleValue;
	}
	
	public boolean getBooleanValue() {
		return booleanValue;
	}
	
}
