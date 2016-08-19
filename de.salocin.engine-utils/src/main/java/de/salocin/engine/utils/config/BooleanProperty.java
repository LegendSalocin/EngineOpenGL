package de.salocin.engine.utils.config;

/**
 * Represents a single property in a configuration file. The value type is a
 * {@link Boolean}
 */
public class BooleanProperty extends AbstractProperty<Boolean> {
	
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
	public BooleanProperty(String name, Boolean defaultValue) {
		super(name, defaultValue);
	}
	
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
	public BooleanProperty(String name, boolean defaultValue) {
		super(name, defaultValue);
	}
	
	/**
	 * Alias for {@link #setValue(Boolean)} with a primitive boolean variable
	 * 
	 * @param value
	 *            The new property's value
	 */
	public void setBoolean(boolean value) {
		setValue(value ? Boolean.TRUE : Boolean.FALSE);
	}
	
	/**
	 * Alias for {@link #getValue()} with a primitive boolean variable
	 * 
	 * @return The property's current value
	 */
	public boolean getBoolean() {
		return getValue().booleanValue();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setObject(Object value) throws Exception {
		if (value == null) {
			return;
		}
		
		setBoolean(Boolean.parseBoolean(value.toString()));
	}
	
}
