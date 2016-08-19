package de.salocin.engine.utils.config;

/**
 * Represents a single property in a configuration file. The value type is a
 * {@link Long}
 */
public class LongProperty extends AbstractProperty<Long> {
	
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
	public LongProperty(String name, long defaultValue) {
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
	public LongProperty(String name, Long defaultValue) {
		super(name, defaultValue);
	}
	
	/**
	 * Alias for {@link #setValue(Long)} with a primitive long variable
	 * 
	 * @param value
	 *            The new property's value
	 */
	public void setLong(long value) {
		setValue(new Long(value));
	}
	
	/**
	 * Alias for {@link #getValue()} with a primitive long variable
	 * 
	 * @return The property's current value
	 */
	public long getLong() {
		return getValue().longValue();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setObject(Object value) throws Exception {
		if (value == null) {
			return;
		}
		
		setLong(Long.parseLong(value.toString()));
	}
	
}
