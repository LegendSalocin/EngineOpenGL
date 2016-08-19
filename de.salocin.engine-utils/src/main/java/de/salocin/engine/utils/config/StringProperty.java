package de.salocin.engine.utils.config;

/**
 * Represents a single property in a configuration file. The value type is a
 * {@link String}
 */
public class StringProperty extends AbstractProperty<String> {
	
	/**
	 * Creates a new instance of this property.
	 * 
	 * @param name
	 *            The property's name. This can not be changed.
	 * @param defaultValue
	 *            The property's default value. This can not be changed.
	 * 
	 * @return A new instance of this property
	 * 
	 * @see #getName()
	 * @see #getDefaultValue()
	 */
	public StringProperty(String name, String defaultValue) {
		super(name, defaultValue);
	}
	
	/**
	 * Alias for {@link #setValue(String)}
	 * 
	 * @param value
	 *            The new property's value
	 */
	public void setString(String value) {
		setValue(value);
	}
	
	/**
	 * Alias for {@link #getValue()}
	 * 
	 * @return The property's current value
	 */
	public String getString() {
		return getValue();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setObject(Object value) throws Exception {
		if (value == null) {
			return;
		}
		
		setString(value.toString());
	}
	
}
