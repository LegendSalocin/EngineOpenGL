package de.salocin.engine.utils.property;

/**
 * Represents a single property. The value type is a {@link Boolean}
 */
public class BooleanProperty extends SimpleProperty<Boolean> {
	
	/**
	 * Creates a new instance of this property with no default value.
	 * 
	 * @return A new instance of this property
	 * 
	 * @see #getDefaultValue()
	 */
	public BooleanProperty() {
		this(false);
	}
	
	/**
	 * Creates a new instance of this property.
	 * 
	 * @param defaultValue
	 *            The property's default value. This can not be changed.
	 * 
	 * @return A new instance of this property
	 * 
	 * @see #getDefaultValue()
	 */
	public BooleanProperty(Boolean defaultValue) {
		super(defaultValue);
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
	public void load(String value) throws Exception {
		if (value == null) {
			return;
		}
		
		setValue(Boolean.parseBoolean(value));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String save() {
		return String.valueOf(getBoolean());
	}
	
}
